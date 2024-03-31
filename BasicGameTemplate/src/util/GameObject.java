package util;

public class GameObject {
	
	private Point3f centre= new Point3f(0,0,0);

	private int width=10;
	private int height=10;
	private boolean hasTextured=false;
	private String textureLocation; 
	private String blanktexture="res/blankSprite.png";
	
	public GameObject() {  
		
	}
	
    public GameObject(String textureLocation,int width,int height,Point3f centre) { 
    	 hasTextured=true;
    	 this.textureLocation=textureLocation;
    	 this.width=width;
		 this.height=height;
		 this.centre =centre;
	}

	public Point3f getCentre() {
		return centre;
	}

	public void setCentre(Point3f centre) {
		this.centre = centre; 
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTexture() {
		if(hasTextured) 
			{
			return textureLocation;
			}
		 
		return blanktexture; 
	}
  
}
