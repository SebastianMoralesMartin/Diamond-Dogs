package testRobot;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.BulletHitEvent;
import robocode.AdvancedRobot;
import java.awt.Color;
import robocode.HitWallEvent;
import java.util.Random;



public class DD_03 extends AdvancedRobot {
	private long tiempo = System.currentTimeMillis();
	private int poder = 1;
	private Estado estado;
	private int direccionCanon = 1;
	private Random rnd = new Random();
	
	
		enum Estado{
			GIRANDO,
			DISPARANDO,
			HUYENDO,
			PVP,
			DISTRACCION,
			EVASION,
			PARED
		}
		
		@Override
		public void run() {
			setAdjustRadarForGunTurn(true);
			setAdjustGunForRobotTurn(true);
			setBodyColor(Color.RED);
			setGunColor(Color.PINK);
			setRadarColor(Color.CYAN);
			setScanColor(Color.WHITE);
			estado = Estado.GIRANDO;
			while(true) {
				switch(estado) {
				case GIRANDO:
					setAhead(60);
					setTurnLeft(10);
					setTurnRadarLeft(10);
					setTurnGunLeft(10);
					execute();
					
					break;
				case DISPARANDO:
				    direccionCanon = 0;
				    setTurnGunRight(360*direccionCanon);
					fire(poder);
					break;
				case HUYENDO:
					setTurnGunLeft(10);
					setTurnRadarLeft(10);
					setAhead(rnd.nextInt(200));
					setTurnLeft(75);
					setBack(rnd.nextInt(200));
					execute();
					break;
				case PARED:
					turnLeft(180);
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
			if (System.currentTimeMillis() - tiempo >=1000) {
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
		@Override
		public void onHitWall(HitWallEvent event) {
			estado = Estado.PARED;
			estado = Estado.GIRANDO;
		}
		
		
		

}
