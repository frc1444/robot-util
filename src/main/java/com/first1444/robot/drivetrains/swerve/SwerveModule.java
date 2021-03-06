package com.first1444.robot.drivetrains.swerve;


public interface SwerveModule {
	
	
	default void recalibrate(){
	}
	default void setQuickReverseAllowed(boolean quickReverseAllowed){
	
	}
	
	/**
	 * @param speed A number in range [-1..1] representing the speed as a percentage
	 */
	void setTargetSpeed(double speed);
	/**
	 * @return Gets the speed as a percentage
	 */
	double getTargetSpeed();
	
	double getTotalDistanceTraveledInches();

	
	void setTargetAngle(double positionDegrees);
	double getTargetAngle();
	
	double getCurrentAngle();

	default void set(double targetSpeed, double positionDegrees){
		setTargetSpeed(targetSpeed);
		setTargetAngle(positionDegrees);
	}


	/**
	 *
	 * @return A string to represent
	 */
	String getName();
}
