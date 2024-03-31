package util;


public class Point3f {

	private float x;
	private float y;
	private float z;
	
	private int boundary=1000;
	
	
	// default constructor
	public Point3f() { 
		setX(0.0f);
		setY(0.0f);
		setZ(0.0f);
		
	}
	
	//initializing constructor
	public Point3f(float x, float y, float z) { 
		this.setX(x);
		this.setY(y);
		this.setZ(z); 
	}
	
	private void setBoundary(int boundary) {
		this.boundary = boundary;
		
	}

	// sometimes for different algorithms we will need to address the point using positions 0 1 2 
	public float getPostion(int postion)
	{
		switch(postion)
		{
		case 0: return getX();
		case 1: return getY();
		case 2: return getZ(); 
		default: return Float.NaN;  
		} 
	}
	
	public String toString()
	{
		return ("(" + getX() +"," + getY() +"," + getZ() +")");
    }



	public Point3f PlusVector(Vector3f Additonal) { 
		return new Point3f(this.getX()+Additonal.getX(), this.getY()+Additonal.getY(), this.getZ()+Additonal.getZ());
	} 

	
	public Point3f MinusVector(Vector3f Minus) { 
		return new Point3f(this.getX()-Minus.getX(), this.getY()-Minus.getY(), this.getZ()-Minus.getZ());
	}
	
 
	public Vector3f MinusPoint(Point3f Minus) { 
		return new Vector3f(this.getX()-Minus.getX(), this.getY()-Minus.getY(), this.getZ()-Minus.getZ());
	}
	 
	
	
	 //Use for direct application of a Vector 
	public void ApplyVector(Vector3f vector) { 
		 setX(CheckBoundary(this.getX()+vector.getX()));
		 setY(CheckBoundary(this.getY()-vector.getY()));
		 setZ(CheckBoundary(this.getZ()-vector.getZ())); 
	}

	private float CheckBoundary(float f) {
		if (f<0) f=0.0f;
		if (f>boundary)f=(float) boundary;
		return f;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	
	  
	  
	 // Remember point + point  is not defined so we not write a method for it.  
}


