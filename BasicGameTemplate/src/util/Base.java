package util;	


	public class Base extends GameObject {
		
		int health;
		public Base(String textureLocation, int width, int height, Point3f centre, int health) {
	        super(textureLocation, width, height, centre);
	        this.health = health;
	    }
	
		//Getter and Setter for health
		public int getHealth(){
			return health;
		}
		public void setHealth(int i) {
			this.health = i;
		}
		
	}
