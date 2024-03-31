package util;

public class Bullet extends GameObject {

	private Vector3f direction;
	public Bullet(String textureLocation, int width, int height, Point3f centre, Vector3f direction) {
        super(textureLocation, width, height, centre);
        this.direction = direction;
    }
    
	public Vector3f getDirection(){
		return direction;
	}
}
