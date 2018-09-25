package testRobot;

import robocode.Robot;

public class Cuadro extends Robot {
	@Override
	public void run() {
		while(true) {
			ahead(100);
			turnLeft(90);
		}
	}

}
