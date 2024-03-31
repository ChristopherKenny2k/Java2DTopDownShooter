package util;

import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyManager extends GameObject {


	//Different lists of enemies, (in movement, at base (attacking), and a combination of all)
    public CopyOnWriteArrayList<Enemy> EnemyList = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Enemy> AttackingEnemy = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Enemy> AllEnemies = new CopyOnWriteArrayList<>();
    //Difficulty, increases and determines max enemies at one time
    public int diff =0;
    
	public void spawnEnemy() {
		
		//Enemies spawn randomly and move towards the base
		Vector3f direction = new Vector3f();
		Point3f spawnPoint = new Point3f();
		float randomX = (float) (Math.random() * 1000);
	    float randomY = (float) (Math.random() * 1000);

	    // Randomly select a side of the screen (top, right, bottom, left)
	    int side = (int) (Math.random() * 4);

	    // Adjust the spawn point based on the selected side, X or Y must be Min or Max (edge of screen)
	    switch (side) {
	    // Top
	    case 0:
	    	spawnPoint = new Point3f(randomX, 0, 0);
	        break;
	    // Right
	    case 1: 
	        spawnPoint = new Point3f(1000, randomY, 0);
	        break;
	    // Bottom
	    case 2: 
	         spawnPoint = new Point3f(randomX, 1000, 0);
	         break;
	    // Left
	    case 3: 
	         spawnPoint = new Point3f(0, randomY, 0);
	         break;
	     default:
	    	 break;
	    }
	         //Direction Calculation
	         float centerX = 543;
	         float centerY = 482;
	         float dx = centerX - spawnPoint.getX();
	         float dy = centerY - spawnPoint.getY();

	         // Normalize the direction vector
	         float length = (float) Math.sqrt(dx * dx + dy * dy);
	         if (length != 0.0f) {
	             float invLength = 1.0f / length;
	             dx *= invLength;
	             dy *= invLength;
	         }

	         direction.setX(dx);
	         direction.setY(-dy);
	         direction.setZ(0);

        Enemy enemy = new Enemy("res/Enemies.png", 38, 38, spawnPoint, direction);
        EnemyList.add(enemy);
        
	        }
	
	//Enemy Logic
	public void enemyLogic() {
		// TODO Auto-generated method stub
		for (Enemy temp : EnemyList) 
		{
			//Applying movement to enemies not at base
            temp.getCentre().ApplyVector(temp.getDirection());


            // Difficulty, spawn limits
            //LVL: 1
         if (diff <= 25) {
            if (EnemyList.size() < 2) {
            	diff++;
				spawnEnemy();
		}
         }
         //LVL: 2
         if (diff > 25 && diff <= 50){
        	 if (EnemyList.size() < 3) {
        		 diff++;
        		 spawnEnemy();
        	 }
         }
         //LVL: 3
         if (diff > 50 && diff <= 100){
        	 if (EnemyList.size() < 4) {
        		 diff++;
        		 spawnEnemy();
        	 }
         }
         //LVL: 4
         if (diff > 100 && diff <= 150){
        	 if (EnemyList.size() < 5) {
        		 diff++;
        		 spawnEnemy();
        	 }
         }
         //LVL: 5
         if (diff > 150){
        	 if (EnemyList.size() < 6) {;
        		 diff++;
        		 spawnEnemy();
        	 }
         }         
	}
	}
	
	public void setDiff(int i) {
		this.diff = i;
	}
	
	public CopyOnWriteArrayList<Enemy> getAllEnemies() {
		for(Enemy enemy : EnemyList) {
			AllEnemies.add(enemy);
		}
		for(Enemy enemy: AttackingEnemy) {
			AllEnemies.add(enemy);
		}
		return AllEnemies;	
	}
}


