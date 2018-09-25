package testRobot;

import robocode.Robot;
import robocode.ScannedRobotEvent;
public class Giro extends Robot {
	@Override
	public void run() {
		while(true) {
			ahead(100);
			turnLeft(180);
		}
		
	}
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		if(event.getDistance()<200) {
		fire(3);
		}else {
			fire(1);
		}
	}
}
