package testRobot;

import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.BulletHitEvent;
import robocode.AdvancedRobot;
import java.awt.Color;
import java.util.Random;



public class DD_03 extends AdvancedRobot {
	private long tiempo = System.currentTimeMillis();
	private int poder = 1;
	private Estado estado;
	//private int direccionCanon = 1;
	private Random rnd = new Random();
	private double gunTurn;
	private double radarTurn;
		
		@Override
		public void run() {
			
			
			setAdjustRadarForGunTurn(true); //desbloquea el movimiento independiente del cañon
			setAdjustGunForRobotTurn(true); //desbloquea el movimiento independiente del radar
			setBodyColor(Color.GRAY);
			setGunColor(Color.BLACK);
			setRadarColor(Color.CYAN);
			setScanColor(Color.WHITE);
			setBulletColor(Color.PINK);
			estado = Estado.GIRANDO;
			while(true) {
				switch(estado) {
				case GIRANDO:  //algoritmo de movimiento, mueve el cuerpo ligeramente mas rapido que el cañon y el radar
					setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
					setTurnGunRightRadians(Double.POSITIVE_INFINITY);
					setAhead(60);
					setTurnLeft(10);
					setTurnRadarLeft(10);
					setTurnGunLeft(10);
					execute();
					/*estado = Estado.GIROIZQ;
				
					if (System.currentTimeMillis()-tiempo>=3000) 
					{
						estado = Estado.GIRODER;
						tiempo = System.currentTimeMillis();
					}*/
					
					break;
				case DISPARANDO:  //caso cuando detecta a un robot enemigo.
				    //direccionCanon = 0;
				    //setTurnGunRight(360*direccionCanon);
				    //setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
				    setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn)); // centra el radar en el enemigo
				    setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));  // centra el cañon en el enemigo
					setFire(poder);
					setAhead(60);
					setTurnRight(10);
					execute();
					break;
				case HUYENDO: //detecta que un proyectil lo impactó y toma acciones evasivas
					//setTurnGunLeft(10);
					//setTurnRadarLeft(10);
					setAhead(rnd.nextInt(200));
					setTurnLeft(75);
					setBack(rnd.nextInt(200));
					execute();
					break;
				case PARED: //detecta que colsionó con una pared y da vuelta
					turnLeft(180);
					break;
				}
				
			}
			
		}
		@Override
		public void onScannedRobot(ScannedRobotEvent event) {
			this.radarTurn = event.getBearingRadians() + getHeadingRadians() - getRadarHeadingRadians();
			this.gunTurn = event.getBearingRadians() + getHeadingRadians() - getGunHeadingRadians();
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
		@Override
		public void onHitRobot(HitRobotEvent event) {
			estado = Estado.PARED;
			estado = Estado.GIRANDO;
		}
		enum Estado{
			GIRANDO,
			DISPARANDO,
			HUYENDO,
			PVP,
			EVASION,
			PARED,
		}

}
