package com.first1444.robot;


import com.first1444.robot.util.DefaultPerspective;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class OrientationTest {
	@Test
	void testOrientation(){
		assertEquals(0, DefaultPerspective.VIEWING_NORMAL.getOffset(90.0));
		assertEquals(270, DefaultPerspective.VIEWING_NORMAL.getOffset(180.0));
		assertEquals(180, DefaultPerspective.VIEWING_NORMAL.getOffset(-90.0));
		assertEquals(90, DefaultPerspective.VIEWING_NORMAL.getOffset(0.0));

		for(Double value : new Double[]{null, 0.0, 90.0, 180.0, 270.0}){
			assertEquals(0, DefaultPerspective.ROBOT_FORWARD.getOffset(value));
			assertEquals(270, DefaultPerspective.ROBOT_RIGHT.getOffset(value));
			assertEquals(90, DefaultPerspective.ROBOT_LEFT.getOffset(value));
			assertEquals(180, DefaultPerspective.ROBOT_BACK.getOffset(value));
		}
	}
}
