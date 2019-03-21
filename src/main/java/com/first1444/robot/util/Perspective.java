package com.first1444.robot.util;

import static com.first1444.robot.util.MathUtil.mod;

public interface Perspective {
	double getForwardDirection();
	boolean isUseGyro();
	/**
	 * If this certain orientation does not rely on a gyro, you may pass null.
	 * @return The amount to add to the desired direction to account for the given perspective
	 */
	default double getOffset(Double orientation){
		if(!isUseGyro()){
			return mod(getForwardDirection() - 90, 360);
		}
		if(orientation == null){
			throw new IllegalArgumentException();
		}
		return mod(getForwardDirection() - orientation, 360);
	}
	default double getOrientationOffset(Orientation orientation){
		return getOffset(orientation.getOrientation());
	}
}
