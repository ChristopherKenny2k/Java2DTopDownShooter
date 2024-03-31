import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.Iterator;

import util.BulletManager;
import util.Bullet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import util.Enemy;
import util.EnemyManager;
import util.FireRatePUManager;
import util.Base;

import util.GameObject;
import util.HammerPU;
import util.FireRatePU;
import util.HammerPUManager;
import util.Point3f;
import util.Vector3f; 
 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Model {
	
	//GameObjects
	 public  GameObject Player;
	 public GameObject Enemy;
	 public GameObject HammerPU;
	 public GameObject FireRatePU;
	 public Base Base;
	 //Controller
	 private Controller controller = Controller.getInstance();
	 //Stats / Trackers
	 private int Score=0; 
	 private int finalScore=0;
	 private int shotsFired;
	 private float accuracy;
	 //Managers
	 public BulletManager bulletManager = new BulletManager(Player);
	 public EnemyManager enemyManager = new EnemyManager();
	 public HammerPUManager hammerPUManager = new HammerPUManager();
	 public FireRatePUManager fireRatePUManager = new FireRatePUManager();
	 
	 public Point3f baseHitbox = new Point3f(518, 418, 0);
	 
	 //Time-related variables
	 private long lastBulletTime = System.currentTimeMillis();
	 private long lastDamageTime = System.currentTimeMillis();
	 public int bulletCooldown = 500;
	 private final long ACTIVE_EFFECT_TIMER = 10000;
	 public long pickupTime;
	 //Drop Probability (increases as player gets kills, resets when Power Up is dropped)
	 public long dropProb=0;


	 public Model() {
		 //setup game world 
		//Player 
		Player= new GameObject("res/PlayerHat.png",50,50,new Point3f(800,500,0));
		//Base
		Base = new Base("res/RanchHouseSmall.png", 215, 215, new Point3f(436, 375,0), 100);
		
		//Managers
		bulletManager = new BulletManager(Player);
		//Spawn enemy to start game + spawning loops
		enemyManager.spawnEnemy();
		//Play Game Music
		SoundManager.playBackgroundMusic();		
	}
	
	public void gamelogic() 
	{
		// Player Logic first 
		playerLogic(); 
		// Enemy Logic next
		enemyManager.enemyLogic();
		// Bullets move next 
		bulletManager.bulletLogic();
		//Game logic 
		gameLogic(); 
		//Base Logic
		baseLogic();
		//Power Up Logic
		PowerUpLogic();   
	}

	private void gameLogic() { 
		
		//Shot Hits Enemy checker + resulting impacts e.g; Spin for Power Up drop, increase score
		for (GameObject temp : getEnemies()) 
		{
		for (GameObject Bullet : bulletManager.getBullets()) 
		{
			if ( Math.abs(temp.getCentre().getX()- Bullet.getCentre().getX())< temp.getWidth() 
				&& Math.abs(temp.getCentre().getY()- Bullet.getCentre().getY()) < temp.getHeight())
			{
				enemyManager.EnemyList.remove(temp);
				enemyManager.AttackingEnemy.remove(temp);
				bulletManager.getBullets().remove(Bullet);
				Score++;
				//DropCheck for power up, if fails, increments probability on next kill by 1%
				if(dropcheck(dropProb) == true) {
					dropProb=0;
					double rand = Math.random();
					//Spawns PowerUp on enemy death location
					if (rand > 0.65){
						fireRatePUManager.SpawnFireRatePU(temp.getCentre());
					} else if(rand <= 0.65) {
						hammerPUManager.SpawnHammerPU(temp.getCentre());
					}
				} else {
					dropProb = dropProb +1;

				}
			}  
			
		} 
		}
		//Deleting Power Ups when time expires from drop time
		for (HammerPU hammerPU : hammerPUManager.getHammerList()) {
			if ((System.currentTimeMillis() - hammerPU.getSpawnTime()) > hammerPU.getDeleteTimer()) {
				hammerPUManager.getHammerList().remove(hammerPU);
			}
		}
		for (FireRatePU fireRatePU : fireRatePUManager.getFireRateList()) {
			if ((System.currentTimeMillis() - fireRatePU.getSpawnTime()) > fireRatePU.getDeleteTimer()) {
				fireRatePUManager.getFireRateList().remove(fireRatePU);
			}
		}
	}
	
	
	private void baseLogic() {
		
		//Enemies in contact with base moved to AttackingEnemy list (no vector applied)
	    for(Enemy enemy: enemyManager.EnemyList) {
	        if (Math.abs(enemy.getCentre().getX() - baseHitbox.getX()) < (enemy.getWidth()+190) / 2
	                && Math.abs(enemy.getCentre().getY() - baseHitbox.getY()) < (enemy.getHeight() + 100) / 2) {
	           enemyManager.AttackingEnemy.add(enemy);
	        }
	    }
	   
	    enemyManager.EnemyList.removeAll(enemyManager.AttackingEnemy);
	    for(Bullet bullet: bulletManager.bulletList) {
	        // Bullets delete on collision with base
	        if (Math.abs(bullet.getCentre().getX() - baseHitbox.getX()) < (bullet.getWidth() +75) / 2
	                && Math.abs(bullet.getCentre().getY() - 20- baseHitbox.getY()) < (bullet.getHeight() - 25) / 2) {
	        	bulletManager.bulletList.remove(bullet);
	        }
	    }
	    long currentTime = System.currentTimeMillis();
	    // Damage is dealt to house every 1 seconds (SUBJECT TO CHANGE for balancing)
	    if (currentTime - lastDamageTime >=  1000) {
	    	//For every enemy in attacking enemy list, base health -1 per second;
        for (Enemy enemy : enemyManager.AttackingEnemy) {
	        Base base = getBase();
	        base.setHealth(base.getHealth() - 2);

	        // GameOver Conditions
	        if (base.getHealth() <= 0) {
	        	setFinalScore(getScore());
	        	if(getFinalScore() > 0) {
	        	setAccuracy((getFinalScore() * 100.0f) / getShotsFired());
	        	} else {
	        		setAccuracy(0);
	        	}
	        	
	            SoundManager.playGameOver();
	            SoundManager.GameOverSounds();
	        }
        }
        lastDamageTime = currentTime;
	    }
	    
	        
	}

	//Accuracy for game over screen, limited to 2 decimal places
	public void setAccuracy(float accuracy) {
	    this.accuracy = Float.parseFloat(String.format("%.2f", accuracy));
	}
	public float getAccuracy() {
	    return accuracy;
	}
	
	//Player Logic
	private void playerLogic() {

		// player boundaries (used for collision calculation)
	    float playerLeft = Player.getCentre().getX() - Player.getWidth() / 2;
	    float playerRight = playerLeft + Player.getWidth();
	    float playerTop = Player.getCentre().getY() - Player.getHeight() / 2;
	    float playerBottom = playerTop + Player.getHeight();

	    //Base hitbox (with some fine tuning, due to empty space in sprite creation)
	    float baseLeft = Base.getCentre().getX()  +  100 - Base.getWidth() / 2;
	    float baseRight = baseLeft - 35 + Base.getWidth();
	    float baseTop = Base.getCentre().getY() + 100 - Base.getHeight() / 2;
	    float baseBottom = baseTop - 120 + Base.getHeight();

	    //Active effect check for Fire Rate Power Ups, decreased bullet shot cooldown
	    if(activeEffectCheck() == true) {
		           bulletCooldown = 175;
		        } else {
		        	bulletCooldown=500;
		        }
	  
	    // Collision with base, ceases movement in appropriate direction (prevents keypress)
	    boolean collidesWithBase = playerRight > baseLeft && playerLeft < baseRight &&
	                               playerBottom > baseTop && playerTop < baseBottom;

	    if (Controller.getInstance().isKeyAPressed() || Controller.getInstance().isLeftArrowPressed()) {
	        if (playerLeft + 65 >= 0 && (!collidesWithBase || Player.getCentre().getX() < Base.getCentre().getX())) {
	            Player.getCentre().ApplyVector(new Vector3f(-2, 0, 0));
	        }
	    }

	    if (Controller.getInstance().isKeyDPressed() || Controller.getInstance().isRightArrowPressed()) {
	        if (playerRight + 2 <= 970 && (!collidesWithBase || Player.getCentre().getX() > Base.getCentre().getX())) {
	            Player.getCentre().ApplyVector(new Vector3f(2, 0, 0));
	        }
	    }

	    if (Controller.getInstance().isKeyWPressed() || Controller.getInstance().isUpArrowPressed()) {
	        if (playerTop + 50 >= 0 && (!collidesWithBase || Player.getCentre().getY() < Base.getCentre().getY())) {
	            Player.getCentre().ApplyVector(new Vector3f(0, 2, 0));
	        }
	    }

	    if (Controller.getInstance().isKeySPressed() || Controller.getInstance().isDownArrowPressed()) {
	        if (playerBottom + 2 <= 815 && (!collidesWithBase || Player.getCentre().getY() > Base.getCentre().getY())) {
	            Player.getCentre().ApplyVector(new Vector3f(0, -2, 0));
	        }
	    }

	    //Space or Click for shooting, increments shots fired (for accuracy calculation), cooldown + play gunshot audio
	    if ((Controller.getInstance().isKeySpacePressed() || Controller.getInstance().isLeftMouseButtonPressed())
	            && System.currentTimeMillis() - lastBulletTime > bulletCooldown) {
	        bulletManager.createBullet(getAngle(), getVelocityX(), getVelocityY());
	        shotsFired= shotsFired + 1;
	        SoundManager.playGunshot();
	        lastBulletTime = System.currentTimeMillis();

	        if (Controller.getInstance().isKeySpacePressed()) {
	            Controller.getInstance().setKeySpacePressed(false);
	        }

	        if (Controller.getInstance().isLeftMouseButtonPressed()) {
	            Controller.getInstance().setLeftMouseButtonPressed(false);
	        }
	    }
	}
	
	//PowerUp Logic
	public void PowerUpLogic() {
		//Time used for active effect duration
	     long currentTime = System.currentTimeMillis();
	     
	     //Hammer PowerUps
	     for(HammerPU hammerPU : hammerPUManager.HammerList) {
	    	 //Collision check for player to hammer power up, removes sprite and plays sound + applies effect
	    	 if (Math.abs(hammerPU.getCentre().getX() - 5 -Player.getCentre().getX()) < (hammerPU.getWidth() + 20) / 2
		                && Math.abs(hammerPU.getCentre().getY()  - Player.getCentre().getY()) < (hammerPU.getHeight() + 12) / 2) {
		           hammerPUManager.HammerList.remove(hammerPU);
		           SoundManager.playHammerPU();
		           Base.setHealth(Base.getHealth() + 25);
		           if(Base.getHealth()>100) {
		        	   Base.setHealth(100);
		           }
		        }
	     }
	     
	     //FireRate PowerUps
	     for(FireRatePU fireRatePU : fireRatePUManager.FireRateList) {
	    	//Collision check for player to FireRate power up, removes sprite and plays sound + applies effect for duration
	    	 if (Math.abs(fireRatePU.getCentre().getX() -Player.getCentre().getX()) < (fireRatePU.getWidth()) / 2
		                && Math.abs(fireRatePU.getCentre().getY()  - Player.getCentre().getY()) < (fireRatePU.getHeight() + 12) / 2) {
	    		 fireRatePUManager.FireRateList.remove(fireRatePU);
	    		 SoundManager.playFireRatePU();
	    		 setPickupTime(System.currentTimeMillis());    	
	    	 }
	     }
	     
	 }
	
	//Roll to decide if check happens (if not, dropprob is incremented in gamelogic)
	public boolean dropcheck(float f) {
		float roll = (float) (Math.random() * 100);
		if(roll <= f) {
			return true;
		} else {
			return false;
		}	
	}


	//Getters and Setters
	public Base getBase( ) {
		return (util.Base) Base;
	}
	public Enemy getEnemy() {
		return (util.Enemy) Enemy;
	}
	public GameObject getPlayer() {
		return Player;
	}
	public long getActiveEffectTimer() {
		return ACTIVE_EFFECT_TIMER;
	}
	public boolean activeEffectCheck() {
		return (System.currentTimeMillis() -  pickupTime) < ACTIVE_EFFECT_TIMER;
	}
	public long getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(long pickupTime) {
		this.pickupTime = pickupTime;
	}
	public Point3f playerPos() {
		if (Player != null) {
		return Player.getCentre();
		}
		return null;
	}
	public int getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	public int getShotsFired() {
		return shotsFired;
	}
	public void setShotsFired(int shotsFired) {
		this.shotsFired = shotsFired;
	}
	
	//Get all Enemies from both enemy lists
	public CopyOnWriteArrayList<Enemy> getEnemies() {
	    CopyOnWriteArrayList<Enemy> allEnemies = new CopyOnWriteArrayList<>();
	    
	    // Add enemies from the original list
	    allEnemies.addAll(enemyManager.EnemyList);
	    
	    // Add enemies from the attacking list
	    allEnemies.addAll(enemyManager.AttackingEnemy);
	    
	    return allEnemies;
	}
	
	//Getters for Power Ups
	public CopyOnWriteArrayList<HammerPU> getHammerPUs(){
		CopyOnWriteArrayList<HammerPU> allHammerPUS = new CopyOnWriteArrayList<>();
		allHammerPUS.addAll(hammerPUManager.HammerList);
		return allHammerPUS;
	}
	public CopyOnWriteArrayList<FireRatePU> getFireRatePUs(){
		CopyOnWriteArrayList<FireRatePU> allFireRatePUs = new CopyOnWriteArrayList<>();
		allFireRatePUs.addAll(fireRatePUManager.FireRateList);
		return allFireRatePUs;
	}

	
	public int getScore() { 
		return Score;
	}
	
	//Restart Game Method
	public void restartGame() {
		
		Player.setCentre(new Point3f(800, 500, 0));
		enemyManager.setDiff(0);
		//Despawning all Enemies and Power-Ups
		for(Enemy enemy: enemyManager.AttackingEnemy) {
			enemyManager.AttackingEnemy.remove(enemy);
		}
		for(Enemy enemy: enemyManager.EnemyList) {
			enemyManager.EnemyList.remove(enemy);
		}
		for(HammerPU hammerPU: hammerPUManager.HammerList) {
			hammerPUManager.HammerList.remove(hammerPU);
		}
		for(FireRatePU fireRatePU: fireRatePUManager.FireRateList) {
			fireRatePUManager.FireRateList.remove(fireRatePU);
		}
		//Resetting Stats
		Score = 0;
		accuracy = 0;
		shotsFired=0;
		Base.setHealth(100);
		

		 // 2 second delay before spawning enemies on game restart
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	            enemyManager.spawnEnemy();
	            enemyManager.spawnEnemy();
	        }
	    }, 2000);

	    // Manually Resetting Controls
	    // WASD + S
	    controller.setKeyAPressed(false);
	    controller.setKeySPressed(false);
	    controller.setKeyDPressed(false);
	    controller.setKeyWPressed(false);
	    controller.setKeySpacePressed(false);
	    //Arrow Keys
	    controller.setLeftArrowPressed(false);
	    controller.setRightArrowPressed(false);
	    controller.setDownArrowPressed(false);
	    controller.setUpArrowPressed(false);
	    //Mouse Controls
	    controller.setLeftMouseButtonPressed(false);
	    
	}
	
	// Mouse and bullet trajectory calculations
	public Point getMousePosition() {
	PointerInfo mouse = MouseInfo.getPointerInfo();
	Point mouselocation = mouse.getLocation();
	return mouselocation;
	}
	public double calculateMouseX() {
		double x = (int) getMousePosition().getX();
		return x;
	}
	public double calculateMouseY() {
		double y = (int)getMousePosition().getY();
		return y;
	}
	
	//Getters for mouse location
	public double getMouseX() {
		return calculateMouseX();
	}
	public double getMouseY() {
		return calculateMouseY();
	}
	
	//Calculation of angle from player to cursor 
	public double calculateAngle() {
	double angle = Math.atan(((getMouseY() - Player.getCentre().getY())-30) / ((getMouseX() -10  - Player.getCentre().getX())));
	
	//Fine tuning in the case that the angle is = PI/2 in which case the trajectory is reversed
	//Here i account for those specific angles 
	if((getMouseY() - Player.getCentre().getY()) < 0) {
		if(angle > 0) {
			if(1.5514282888952 < angle && angle < 1.57079632679) {
				angle= -1*angle;
				}
		if(angle < 0) {
			if(-1.5667802841291 > angle && angle > -1.56865957790) {
				angle = -1*angle;
		}
		}
	}
	} else if((getMouseY() - Player.getCentre().getY()) > 0) {
		if(angle > 0) {
			if(1.56522017642 < angle && angle < 1.5707963267948966) {
				angle= -1*angle;
				}
		if(angle < 0) {
			if(-1.5281681379228103 > angle && angle > -1.5692215249472282) {
				angle = -1*angle;
		}
		}
	}
	}
	return angle;
	}
	
	
	
	public double calculateVelocityX() {
	float velocityX = (float) ((float)2 * Math.cos(calculateAngle()));
	if ((Player.getCentre().getX() + 10 - getMouseX()) < 0) {
		velocityX = velocityX * -2;
	} else {
	}
	return velocityX *2;
	}
	public double calculateVelocityY() {
	float velocityY = (float) ((float)2 * Math.sin(calculateAngle()));
	if ((Player.getCentre().getX() - getMouseX()) < 0) {
		velocityY = velocityY * -2;
	} else {
	}
	return velocityY * 2;
	}
	
	//Getter for this, used in bullet manager
	public double getAngle() {
		return calculateAngle();
	}
	public float getVelocityX() {
		return (float) calculateVelocityX();
	}
	public float getVelocityY() {
		return (float) calculateVelocityY();
	}
}

