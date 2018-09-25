package estados;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.BulletHitEvent;
import robocode.AdvancedRobot;

import java.awt.Color;



public class RobotEstado extends AdvancedRobot {
	private long tiempo = System.currentTimeMillis();
	private int poder = 1;
	private Estado estado;
		enum Estado{
			GIRANDO,
			DISPARANDO,
			HUYENDO
		}
		
		@Override
		public void run() {
			setBodyColor(Color.RED);
			setGunColor(Color.PINK);
			setRadarColor(Color.CYAN);
			setScanColor(Color.WHITE);
			estado = Estado.GIRANDO;
			while(true) {
				switch(estado) {
				case GIRANDO:
					turnLeft(10);
					setMaxVelocity(5);
					ahead(10);
					break;
				case DISPARANDO:
					fire(poder);
					break;
				case HUYENDO:
					ahead(100);
					turnRight(75);
					back(150);
					break;
				}
				
			}
			
		}
		@Override
		public void onScannedRobot(ScannedRobotEvent event) {
			estado = Estado.DISPARANDO;
		}
		@Override
		public void onBulletMissed(BulletMissedEvent event) {
			poder = 1;
			estado = Estado.GIRANDO;
		}
		@Override
		public void onHitByBullet(HitByBulletEvent event) {
			estado = Estado.HUYENDO;
			if (System.currentTimeMillis() - tiempo >=2000) {
				estado =Estado.GIRANDO;
				tiempo = System.currentTimeMillis();
			}
			
		}
		@Override
		public void onBulletHit(BulletHitEvent event) {
			if(poder<3) {
				poder ++;
			}
		}
		
		

}
