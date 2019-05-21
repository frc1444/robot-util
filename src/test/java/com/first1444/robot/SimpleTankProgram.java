package com.first1444.robot;

import com.first1444.robot.drivetrains.TankDrive;

import java.util.Scanner;

public class SimpleTankProgram {
	private static final String FORMAT = "%3.2f";
	public static void main(String[] args){
		TankDrive drive = new TankDrive() {
			@Override
			public void setTankControl(double left, double right) {
				System.out.println("left | right");
				System.out.println(String.format(FORMAT, left) + "   " + String.format(FORMAT, right));
			}
		};
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()){
			String input = scanner.nextLine();
			StringBuilder throttle = null;
			StringBuilder turn = null;
			for(char c : input.toCharArray()){
				if(!Character.isDigit(c) && c != '.' && c != '-'){
					if(throttle != null && turn == null){
						turn = new StringBuilder();
					}
					continue;
				}
				if(turn == null){ // do left
					if(throttle == null){
						throttle = new StringBuilder();
					}
					throttle.append(c);
				} else {
					turn.append(c);
				}
			}
			if(throttle == null || turn == null){
				continue;
			}
			final double throttleValue, turnValue;
			try {
				throttleValue = Double.parseDouble(throttle.toString());
				turnValue = Double.parseDouble(turn.toString());
			} catch(NumberFormatException ex){
				continue;
			}
			drive.setArcadeControl(throttleValue, turnValue);
		}
	}
}
