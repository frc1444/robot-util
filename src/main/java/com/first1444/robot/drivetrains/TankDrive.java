package com.first1444.robot.drivetrains;

import static java.lang.Math.abs;

public interface TankDrive extends Drivetrain {
	
	/**
	 *
	 * @param left The speed of the left side. In range [-1..1]
	 * @param right The speed othe right side. In range [-1..1]
	 */
	void setTankControl(double left, double right);
	
	/**
	 *
	 * @param forwardSpeed The speed of the robot. In range [-1..1]
	 * @param turnAmount The turnAmount of the robot. In range[-1..1] A positive value turns clockwise.
	 */
	default void setArcadeControl(double forwardSpeed, double turnAmount){
		double left = forwardSpeed + turnAmount;
		double right = forwardSpeed - turnAmount;
		double absLeft = abs(left);
		double absRight = abs(right);
		if(absLeft > 1 || absRight > 1){
			if(absLeft > absRight){
				left /= absLeft;
				right /= absLeft;
			} else {
				left /= absRight;
				right /= absRight;
			}
		}
		setTankControl(left, right);
	}
	
}
