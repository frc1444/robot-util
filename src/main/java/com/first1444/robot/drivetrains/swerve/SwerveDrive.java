package com.first1444.robot.drivetrains.swerve;


import com.first1444.robot.drivetrains.StrafeDrive;

import java.util.List;

public interface SwerveDrive extends StrafeDrive {
	List<? extends SwerveModule> getModules();
}
