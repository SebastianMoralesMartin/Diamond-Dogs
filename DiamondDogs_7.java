package testRobot;

import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
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
	private double giroCanon;
	private double giroRadar;
	private double giroRobot;
	private boolean izqODer = true;
	private boolean unovuno = false;
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
				System.out.println(estado);
				switch(estado) {
				case GIRANDO:  //algoritmo de movimiento, mueve el cuerpo ligeramente mas rapido que el cañon y el radar
					setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
					setTurnGunRightRadians(Double.POSITIVE_INFINITY);
					setTurnRightRadians(Double.POSITIVE_INFINITY);
					setAhead(60);
					setTurnLeft(10);
					execute();
					if (unovuno) {estado = Estado.PVP;}
					/*estado = Estado.GIROIZQ;
				
					if (System.currentTimeMillis()-tiempo>=3000) 
					{
						estado = Estado.GIRODER;
						tiempo = System.currentTimeMillis();
					}*/
					
					break;
				/*case DISPARANDO:  //caso cuando detecta a un robot enemigo.
				    //direccionCanon = 0;
				    //setTurnGunRight(360*direccionCanon);
				    //setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
				    setTurnRadarRightRadians(Utils.normalRelativeAngle(giroRadar)); // centra el radar en el enemigo
				    setTurnGunRightRadians(Utils.normalRelativeAngle(giroCanon));  // centra el cañon en el enemigo
					setFire(poder);
					setAhead(rnd.nextInt(60));
					setTurnRight(rnd.nextInt(30));
					execute();
					break;*/
				case HUYENDO: //detecta que un proyectil lo impactó y toma acciones evasivas
					//setTurnGunLeft(10);
					//setTurnRadarLeft(10);
					setAhead(rnd.nextInt(200));
					setTurnLeft(75);
					setBack(rnd.nextInt(200));
					execute();
					break;
				case PARED: //detecta que colsionó con una pared y da vuelta
					setTurnLeft(180);
					setBack(100);
					estado = Estado.GIRANDO;
					break;
				case DISDER:
				    setTurnRadarRightRadians(Utils.normalRelativeAngle(giroRadar)); // centra el radar en el enemigo
				    setTurnGunRightRadians(Utils.normalRelativeAngle(giroCanon));  // centra el cañon en el enemigo
					setFire(poder);
					setAhead(rnd.nextInt(60));
					setTurnRight(rnd.nextInt(30));
					execute();
					break;
				case DISIZQ:
				    setTurnRadarRightRadians(Utils.normalRelativeAngle(giroRadar)); // centra el radar en el enemigo
				    setTurnGunRightRadians(Utils.normalRelativeAngle(giroCanon));  // centra el cañon en el enemigo
					setFire(poder);
					setAhead(rnd.nextInt(60)); //decide que velocidad tiene el tanque
					setTurnLeft(rnd.nextInt(30)); //decide que 
					execute();
					break;
				case PVP:
					//setTurnRightRadians(POSITIVE_INFINITY);
					setTurnRadarRightRadians(Utils.normalRelativeAngle(giroRadar)); // centra el radar en el enemigo
				    setTurnGunRightRadians(Utils.normalRelativeAngle(giroCanon));  // centra el cañon en el enemigo
				    setTurnRightRadians(Utils.normalRelativeAngle(giroRobot)); // centra el cuerpo del robot en el enemigo para perseguirlo
				    setAhead(50); // inicia la persecusion
				    setFire(poder); // 
				    execute();
				    break;
				}
				
			}
			
		}
		@Override
		public void onScannedRobot(ScannedRobotEvent event) {
			this.giroRadar = event.getBearingRadians() + getHeadingRadians() - getRadarHeadingRadians();
			this.giroCanon = event.getBearingRadians() + getHeadingRadians() - getGunHeadingRadians();
			this.giroRobot = event.getBearingRadians() + getHeadingRadians() - getHeadingRadians();
			//el if decide si el robot irá a la izquiera o a la derecha para disparar
			if(unovuno && izqODer) {
				estado = Estado.PVP;
			}else if(unovuno) {
				estado = Estado.PVP;
			}else if (izqODer){
				estado = Estado.DISDER;
			}else {
				estado = Estado.DISIZQ;
			}
		}
		@Override
		public void onBulletMissed(BulletMissedEvent event) {
			this.izqODer = rnd.nextBoolean();
			poder = 1;
			if(unovuno) {
				estado = Estado.PVP;
			}else {estado = Estado.GIRANDO;}
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
		}
		@Override
		public void onHitRobot(HitRobotEvent event) {
			estado = Estado.PARED;
		}
		@Override
		public void onRobotDeath(RobotDeathEvent event) {System.out.println("muerto");
			if(getOthers() == 1)
			{
				this.unovuno = true;
				estado = Estado.PVP;
			}
		}
		enum Estado{
			GIRANDO,
			HUYENDO, // impactado por proyectil, huye
			PARED, // colision con pared
			DISIZQ, //disparo con giros a la izquierda
			DISDER,//disparo con giros a la derecha
			PVP //uno contra uno
		}

}
