package testRobot;

import robocode.Robot;
public class RobotCero extends Robot 
{
		@Override
		public void run() {
			
			long tiempo = System.currentTimeMillis();
			while(true) {
				doNothing();
				if (System.currentTimeMillis()- tiempo >= 1000) {
					fire(1);
					tiempo = System.currentTimeMillis();
					
				}
			}
		}
}
