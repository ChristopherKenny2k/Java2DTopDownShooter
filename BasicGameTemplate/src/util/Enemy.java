package util;


public class Enemy extends GameObject {
	
	private Vector3f direction;
	private Point3f spawnLocation;
	//Sprite facing direction
	private int EnemyFacing; 
	 
	public Enemy(String textureLocation, int width, int height, Point3f spawnLocation, Vector3f direction) {
        super(textureLocation, width, height, spawnLocation);
        this.direction = direction;
        this.spawnLocation = spawnLocation;
        this.EnemyFacing = determineEnemyFacing(spawnLocation);
    }
    
	public Vector3f getDirection(){
		return direction;
	}
	public Vector3f setDirection(float x, float y, float z, Vector3f direction) {
		return this.direction = direction;
	}
	public Point3f getSpawnLocation() {
		return spawnLocation;
	}

	
	public int getEnemyFacing() {
	        return EnemyFacing;
	        }

	private int determineEnemyFacing(Point3f spawnLocation) {
        float spawnX = getSpawnLocation().getX();
        float spawnY = getSpawnLocation().getY();

        // Determining which sprite to select in viewer (facing house from spawn point)
        double angle = Math.atan2(spawnY - 500, spawnX - 500);
        double degrees = Math.toDegrees(angle);

        // Determine the quadrant based on the angle, quadrant then dictates facing number
        int quadrant;
        if (degrees >= -45 && degrees < 45) {
            quadrant = 1; 
        } else if (degrees >= 45 && degrees < 135) {
            quadrant = 2;
        } else if (degrees >= -135 && degrees < -45) {
            quadrant = 3;
        } else {
            quadrant = 4;
        }

        // Determine the fixed frame based on the quadrant
        int fixedFrame;
        switch (quadrant) {
        //North
            case 1:
                fixedFrame = 1;
                break;
            case 2:
         //South
                fixedFrame = 0;
                break;
            case 3:
         //East
                fixedFrame = 2;
                break;
         //West
            case 4:
                fixedFrame = 3;
                break;
         //Default (S)
            default:
                fixedFrame = 0;
                break;
        }
        return fixedFrame;
	    }
      
  
}
