package com.first1444.robot.drivetrains.swerve;

public interface FourSwerveCollection extends SwerveCollection{

	SwerveModule getFrontLeft();
	SwerveModule getFrontRight();
	SwerveModule getRearLeft();
	SwerveModule getRearRight();
}
