package util;

import java.util.concurrent.CopyOnWriteArrayList;

public class BulletManager {
	private GameObject Player;
	
	public BulletManager(GameObject Player) {
		this.Player = Player;
	}

	//Bullet list, used for drawing appropriate bullets in viewer.java
    public CopyOnWriteArrayList<Bullet> bulletList = new CopyOnWriteArrayList<>();

    public void createBullet(double angle, float velocityX, float velocityY) {
    	//angle, trajectory and speed handling
    	Vector3f tempdirection = new Vector3f(-velocityX, velocityY, 0).normalize();
    	// Bullets are sped up by x3
    	float finalx = tempdirection.getX() *3;
    	float finaly = tempdirection.getY() * 3;
    	Vector3f direction = new Vector3f(finalx, finaly, 0);

    	//Determining bullet position
        Point3f bulletPosition = new Point3f(Player.getCentre().getX()+4, Player.getCentre().getY(), 0.0f);

        Bullet bullet = new Bullet("res/bullet.png", 128, 128, bulletPosition, direction);
        bulletList.add(bullet);
    }
    

    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bulletList;
    }

    public void bulletLogic() {
		// TODO Auto-generated method stub
	  
    	//Moving Bullets
		for (Bullet temp : bulletList) 
		{
			temp.getCentre().ApplyVector(temp.getDirection());

			// "Out-of-Bounds" bullet deletion (offscreen) statements
			//Left of Screen
			if (temp.getCentre().getX()< 1) {
				bulletList.remove(temp);
			}
			//Right of Screen
			if (temp.getCentre().getX() > 999) {
				bulletList.remove(temp);
			}
			//Top of Screen
			if (temp.getCentre().getY()< 1) {
				bulletList.remove(temp);
			}
			//Bottom of Screen
			if (temp.getCentre().getY() > 999) {
				bulletList.remove(temp);
			}
		} 	
	}
}
