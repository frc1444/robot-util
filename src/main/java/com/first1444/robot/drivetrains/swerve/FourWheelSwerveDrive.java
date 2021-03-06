package com.first1444.robot.drivetrains.swerve;

import com.first1444.robot.util.DefaultPerspective;
import com.first1444.robot.util.Orientation;
import com.first1444.robot.util.Perspective;
import me.retrodaredevil.action.SimpleAction;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.Math.*;

public class FourWheelSwerveDrive extends SimpleAction implements SwerveDrive{
	private final Supplier<Orientation> orientationSupplier;
	private final FourSwerveCollection swerveCollection;
	private final double cosA, sinA;

	private double x;
	private double y;
	private double speed = 0;
	private double turnAmount = 0;
	private Perspective controlPerspective = DefaultPerspective.ROBOT_FORWARD;

	/**
	 *
	 * @param orientationSupplier The getter for the orientation
	 * @param swerveCollection A collection of 4 swerve modules
	 * @param wheelBase The distance from the front wheels to the back wheels
	 * @param trackWidth The distance from the left wheels to the right wheels
	 */
	public FourWheelSwerveDrive(Supplier<Orientation> orientationSupplier, FourSwerveCollection swerveCollection, double wheelBase, double trackWidth) {
		super(true);
		this.orientationSupplier = orientationSupplier;
		this.swerveCollection = Objects.requireNonNull(swerveCollection);

		final double diagonal = Math.hypot(wheelBase, trackWidth);
		cosA = wheelBase / diagonal;
		sinA = trackWidth / diagonal;
	}
	@Override
	public List<? extends SwerveModule> getModules() { return swerveCollection.getModules(); }

	@Override
	public void setControl(double x, double y, double turnAmount, double speed, Perspective controlPerspective) {
		if(abs(x) > 1)
			throw new IllegalArgumentException("abs(x) > 1. x=" + x);
		if(abs(y) > 1)
			throw new IllegalArgumentException("abs(y) > 1. y=" + y);
		if(abs(turnAmount) > 1)
			throw new IllegalArgumentException("abs(turnAmount) > 1. turnAmount=" + turnAmount);
		if(abs(speed) > 1)
			throw new IllegalArgumentException("abs(speed) > 1. speed=" + speed);
		
		this.x = x;
		this.y = y;
		this.turnAmount = turnAmount;
		this.speed = speed;
		this.controlPerspective = controlPerspective;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		if(x == 0 && y == 0 && turnAmount == 0){
			speed = 0;
			for(SwerveModule module : getModules()){
				module.setTargetSpeed(0);
			}
			swerveCollection.update();
			return;
		}
		
		final double offset = controlPerspective.getOrientationOffset(orientationSupplier.get()); // the amount to add to the desired angle (rotate x and y by)
		final double offsetRadians = toRadians(offset);
		final double offsetCosine = cos(offsetRadians);
		final double offsetSine = sin(offsetRadians);
		final double x = this.x * offsetCosine - this.y * offsetSine;
		final double y = this.y * offsetCosine + this.x * offsetSine;

		/*
		Note when looking at this code, most things are defined in this order: fl, fr, rl, rr. Other swerve drive
		code may define these in a different order. That may make this code look different or even incorrect but I
		assure you, it is not.

		Also, in this code, 90 degrees is forward while 0 degrees is to the right. This makes it similar to many
		coordinate systems in math but may make the code somewhat different.
		 */
		
		final double turnAmount = this.turnAmount;
		final double speed = this.speed;
		final double sinA = this.sinA;
		final double cosA = this.cosA;
		
		final double A = y - turnAmount * sinA;
		final double B = y + turnAmount * sinA;
		final double C = x - turnAmount * cosA;
		final double D = x + turnAmount * cosA;

		double flSpeed = hypot(B, D);
		double frSpeed = hypot(A, D);
		double rlSpeed = hypot(B, C);
		double rrSpeed = hypot(A, C);

		final double max = max(max(flSpeed, frSpeed), max(rlSpeed, rrSpeed));
		if(max > 1){
			flSpeed /= max;
			frSpeed /= max;
			rlSpeed /= max;
			rrSpeed /= max;
		}

		flSpeed *= speed;
		frSpeed *= speed;
		rlSpeed *= speed;
		rrSpeed *= speed;

		final double flAngle = toDegrees(atan2(B, D));
		final double frAngle = toDegrees(atan2(A, D));
		final double rlAngle = toDegrees(atan2(B, C));
		final double rrAngle = toDegrees(atan2(A, C));
		
		swerveCollection.getFrontLeft().set(flSpeed, flAngle);
		swerveCollection.getFrontRight().set(frSpeed, frAngle);
		swerveCollection.getRearLeft().set(rlSpeed, rlAngle);
		swerveCollection.getRearRight().set(rrSpeed, rrAngle);

		this.speed = 0; // reset the speed so it has to be continuously desired that the wheels move
		
		swerveCollection.update(); // culprit
	}

	@Override
	protected void onEnd(boolean peacefullyEnded) {
		super.onEnd(peacefullyEnded);
		swerveCollection.end();
	}
}
