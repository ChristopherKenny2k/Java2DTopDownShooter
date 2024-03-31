import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.Enemy;
import util.FireRatePU;
import util.HammerPU;
import util.GameObject;


public class Viewer extends JPanel {
	private long CurrentAnimationTime= 0; 
	
	//Instantiation of Model 
	Model gameworld =new Model(); 
	public Viewer(Model World) {
		this.gameworld=World;
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		this.repaint();
		// TODO Auto-generated method stub
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		CurrentAnimationTime++;
		
		//Draw player Game Object 
		int x = (int) gameworld.getPlayer().getCentre().getX();
		int y = (int) gameworld.getPlayer().getCentre().getY();
		int width = (int) gameworld.getPlayer().getWidth();
		int height = (int) gameworld.getPlayer().getHeight();
		String texture = gameworld.getPlayer().getTexture();
		
		//Draw Base Object
		int hx = (int) gameworld.getBase().getCentre().getX();
		int hy = (int) gameworld.getBase().getCentre().getY();
		int hwidth = (int) gameworld.getBase().getWidth();
		int hheight = (int) gameworld.getBase().getHeight();
		String htexture = gameworld.getBase().getTexture();
		
		//Order of Drawn Objects
		//1:Draw Background 
		drawBackground(g);
		
		//2:Draw Base
		drawBase(hx, hy, hwidth, hheight, htexture,g);
		
		//3:Draw Bullets 
		gameworld.bulletManager.getBullets().forEach((temp) ->
		{
			drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(),
					(int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(), g);
		});
		
		//4:Drawing PowerUps
		//4.1:Hammers
		if(gameworld.hammerPUManager.getHammerList() != null) {
			gameworld.getHammerPUs().forEach((HammerPU) ->
			{
				drawHammerPUs(HammerPU, g);
			});};	
			
		//4.2:FireRate PowerUps
		if(gameworld.fireRatePUManager.getFireRateList() != null) {
			gameworld.getFireRatePUs().forEach((FireRatePU) ->
			{
				drawFireRatePUs(FireRatePU, g);
			});};
		if(gameworld.fireRatePUManager.getFireRateList() == null) {
			System.out.println("ITS EMPTYBOZO");
		}	
		
		//5:Draw Enemies   
				gameworld.getEnemies().forEach((enemy) ->
				{ 
					drawEnemies(enemy, g);
				}); 
				
		//6: Draw player
		drawPlayer(x, y, width, height, texture,g);
		
		
		//UI Elements
		//7: Draw Difficulty Indicator
		drawDiffIndicator(15, 25, 105, 105, "res/DifficultyIndicator.png",g);
		
		//8: Draw Healthbar
		drawHealthBar(700, -25, 225, 225, "res/HomeHealthBar.png",g);
		
		//9:Draw Score
		drawScore(gameworld.getScore(), g);
	}
	
	//Draw Methods
	private void drawHammerPUs(HammerPU hammerPU, Graphics g) {
	    int x = (int) hammerPU.getCentre().getX();
	    int y = (int) hammerPU.getCentre().getY();
	    int width = (int) hammerPU.getWidth();
	    int height = (int) hammerPU.getHeight();
	    String texture;

	    //Checks to determine if hammer should be flashing to indicate imminent  deletion
	    //Hammer PowerUp
	    if (hammerPU.reallyFlashingCheck() == true) {
	    	texture = "res/ReallyFlashing.png";
	    } else if (hammerPU.flashingCheck() == true) {
	    	texture = "res/FlashingHammers.png";
	    } else {
	    	texture = "res/HammerPowerUp.png";
	    }
	    File textureToLoad = new File(texture);
	    try {
	        Image myImage = ImageIO.read(textureToLoad);

	        int frameWidth = 64;
	        int currentPositionInAnimation = ((int) CurrentAnimationTime % 12) * frameWidth;
	        g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
	                currentPositionInAnimation + frameWidth, 64, null);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	 }
	
	//FireRate Power Up
	private void drawFireRatePUs(FireRatePU FireRatePU, Graphics g) {
	    int x = (int) FireRatePU.getCentre().getX();
	    int y = (int) FireRatePU.getCentre().getY();
	    int width = (int) FireRatePU.getWidth();
	    int height = (int) FireRatePU.getHeight();
	    String texture;

	    //Checks to determine if FireRate Power Up should be flashing to indicate imminent deletion
	    if (FireRatePU.reallyFlashingCheck() == true) {
	    	texture = "res/ReallyFlashingFireRatePU.png";
	    } else if (FireRatePU.flashingCheck() == true) {
	    	texture = "res/FlashingFireRatePU.png";
	    } else {
	    	texture = "res/FireRatePowerUp.png";
	    }
	    File textureToLoad = new File(texture);
	    try {
	        Image myImage = ImageIO.read(textureToLoad);

	        int frameWidth = 64;
	        int currentPositionInAnimation = ((int) CurrentAnimationTime % 12) * frameWidth;
	        g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
	                currentPositionInAnimation + frameWidth, 64, null);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	 }
	
	
	//Drawing Enemies
	private void drawEnemies(Enemy enemy, Graphics g) {
	    int x = (int) enemy.getCentre().getX();
	    int y = (int) enemy.getCentre().getY();
	    int width = (int) enemy.getWidth();
	    int height = (int) enemy.getHeight();
	    String texture = enemy.getTexture();

	    File textureToLoad = new File(texture);

	    try {
	        Image myImage = ImageIO.read(textureToLoad);

	        //Get the direction (frame) that enemy should be facing
	        int fixedFrame = enemy.getEnemyFacing();
	        int currentPositionInAnimation = fixedFrame * 32; 
	        g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0,
	                currentPositionInAnimation + 31, 32, null);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}




	//Drawing Sand Texture Background
	private void drawBackground(Graphics g)
	{
		File TextureToLoad = new File("res/sandtexture.png");
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			 g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 1000, 1000, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//DrawBullets
	private void drawBullet(int x, int y, int width, int height, String texture,Graphics g)
	{
		File TextureToLoad = new File(texture);
		try {
			Image myImage = ImageIO.read(TextureToLoad);  
			 g.drawImage(myImage, x,y, x+width, y+height, 0 , 0, 324, 324, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	//Draw Base
	private void drawBase(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); 
		try {
		Image myImage = ImageIO.read(TextureToLoad);
		g.drawImage(myImage, x, y,  x+width,  y+height, 0, 0, 127, 127, null);
		} catch(IOException e) {
			e.printStackTrace(); 
		}
	}

	// Drawing Health Bar
	private void drawHealthBar(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture);
		 try {
			 Image myImage = ImageIO.read(TextureToLoad);

		        // frameIndex used to iterate though different frames (relating to health of base remaining)
		        int frameIndex = (100 - gameworld.getBase().getHealth()) / 5;
		        frameIndex = Math.max(0, Math.min(frameIndex, 19));

		        // If health is <= 0, set the frame index to the last frame (Game over, house destroyed)
		        if (gameworld.getBase().getHealth() <= 0) {
		            frameIndex = 20;
		        }

		        int currentPositionInAnimation = frameIndex * 64;
		        g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0, currentPositionInAnimation + 63, 63, null);
		    } catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	//Draw Player
	private void drawPlayer(int x, int y, int width, int height, String texture,Graphics g) { 
		File TextureToLoad = new File(texture);
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, x,y, x+width, y+height, 0, 0, 32, 32, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	
	//Drawing difficulty Indicator
	private void drawDiffIndicator(int x, int y, int width, int height, String texture,Graphics g) { 
		File TextureToLoad = new File(texture);
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			
			int score = gameworld.getScore();
			int frameindex;
			
			//Score determines difficulty, so we will use score to select the difficulty indicator in top right of screen
			if (score <= 20) {
				frameindex = 0;
			} else if (score > 25 && score <= 50) {
				frameindex = 1;
			}else if (score > 50 && score <= 100) {
				frameindex = 2;
			}else if (score > 100 && score <= 150) {
				frameindex = 3;
			}else if (score > 150) {
				frameindex = 4;
			} else {
				frameindex = 0;
			}
			int currentPositionInAnimation = frameindex * 64; 

	        g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0, currentPositionInAnimation + 63, 63, null);
	    } catch(IOException e) {
		e.printStackTrace();
	}
	}
	
	//Displaying Score in top left corner of game
	private void drawScore(int score, Graphics g) {
		//Bold and Black font
	    g.setColor(Color.BLACK);
	    g.setFont(new Font("Arial", Font.BOLD, 24)); 

	  //Positioning (just beside difficulty indicator)
	    g.drawString(""+score, 115, 125);
	}
	
	
	

		 
	 

}

