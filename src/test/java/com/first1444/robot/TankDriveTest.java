package com.first1444.robot;

import com.first1444.robot.drivetrains.TankDrive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class TankDriveTest {
	@Test
	void tankTest(){
		new TestTank(0, 0).setArcadeControl(0, 0); // still
		
		new TestTank(1, 1).setArcadeControl(1, 0); // full throttle
		
		new TestTank(1, 0).setArcadeControl(1, 1); // full forward and turn right
		new TestTank(0, -1).setArcadeControl(-1, 1);
		
		new TestTank(0, 1).setArcadeControl(1, -1); // full forward and turn left
		new TestTank(-1, 0).setArcadeControl(-1, -1);
		
		new TestTank(1, -1).setArcadeControl(0, 1);
		new TestTank(-1, 1).setArcadeControl(0, -1);
		
	}
	private static final class TestTank implements TankDrive {
		private final double expectedLeft, expectedRight;
		
		private TestTank(double expectedLeft, double expectedRight) {
			this.expectedLeft = expectedLeft;
			this.expectedRight = expectedRight;
		}
		
		@Override
		public void setTankControl(double left, double right) {
//			assertTrue(expectedLeft == left);
//			assertTrue(expectedRight == right);
			assertEquals(expectedLeft, left, Double.MIN_VALUE);
			assertEquals(expectedRight, right, Double.MIN_VALUE);
		}
	}
}
