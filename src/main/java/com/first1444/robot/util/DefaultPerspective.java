package com.first1444.robot.util;

/**
 * Perspectives where forward is 90 degrees
 */
public enum DefaultPerspective implements Perspective {
	ROBOT_FORWARD(90, false),
	ROBOT_RIGHT(0, false),
	ROBOT_LEFT(180, false),
	ROBOT_BACK(-90, false),
	
	VIEWING_NORMAL(90, true),
	/** When the jumbotron is on the right side of our driver station*/
	VIEWING_FROM_LEFT(0, true),
	/** When the jumbotron is on the left side of our driver station*/
	VIEWING_FROM_RIGHT(180, true),
	VIEWING_FROM_OPPOSITE_SIDE(-90, true);
	
	private final double forwardDirection;
	private final boolean useGyro;
	
	DefaultPerspective(double forwardDirection, boolean useGyro) {
		this.forwardDirection = forwardDirection;
		this.useGyro = useGyro;
	}
	
	/**
	 * @return The the direction relative to the field that "up" on the joystick corresponds to
	 */
	public double getForwardDirection(){
		return forwardDirection;
	}
	public boolean isUseGyro(){
		return useGyro;
	}
}
