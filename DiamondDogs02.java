package testRobot;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

public class DiamondDogs02 extends Robot {
	private int power = 1;
	private boolean rastreando;
	private boolean disparando;
	@Override
	public void run() {
		rastreando = true;
		disparando = false;
		while(true) {
			if (rastreando) {
				turnLeft(5);
			}else if(disparando) {
				fire(power);
			}
		}
	}
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		rastreando = false;
		disparando = true;
		
	}
	@Override
	public void onBulletHit(BulletHitEvent event) {
		if (power<3) {
			power ++;
			
		}
	}
	@Override
	public void onBulletMissed(BulletMissedEvent event) {
		power = 1;
		disparando = false;
		rastreando = true;
	}

}
