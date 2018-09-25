package testRobot;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.Bullet;

public class DiamondDogs01 extends Robot {
	private int poder = 1;
	@Override
	public void run() {
		while(true) {
			turnLeft(10);
			
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		stop();
		fire(poder);
		stop();
	}	
	public void getVictim(Bullet event) {
		if(event == null) {
			this.poder = 1;
		
		}
		else {
			this.poder ++;
		}
	}
}
