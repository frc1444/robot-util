package com.first1444.robot.drivetrains.swerve;

import me.retrodaredevil.action.Action;

import java.util.List;

public interface SwerveCollection extends Action {
	List<? extends SwerveModule> getModules();
}
