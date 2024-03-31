import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;


public class Controller implements KeyListener {
       
	//Keyboard Controls
	   private static boolean KeyAPressed= false;
	   private static boolean KeySPressed= false;
	   private static boolean KeyDPressed= false;
	   private static boolean KeyWPressed= false;
	   private static boolean KeySpacePressed= false;
	   private static boolean RightArrowPressed = false;
	   private static boolean LeftArrowPressed = false;
	   private static boolean UpArrowPressed = false;
	   private static boolean DownArrowPressed = false;

	   
	   private static final Controller instance = new Controller();
	   
	 public Controller() { 
	}
	 
	 public static Controller getInstance(){
	        return instance;
	    }
	   
	@Override
	public void keyTyped(KeyEvent e) { 
		 
	}

	//WASD + Space (pressed)
	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		//To Lowercase to handle capital letters (capslock)
		char keyChar = Character.toLowerCase(e.getKeyChar());
		//WASD + space (pressed)
		switch (keyChar) 
		{
			case 'a':setKeyAPressed(true);break;  
			case 's':setKeySPressed(true);break;
			case 'w':setKeyWPressed(true);break;
			case 'd':setKeyDPressed(true);break;
			case ' ':setKeySpacePressed(true);break;   
		    default:
		        break;
		}  
		
		//Arrow Keys (pressed)
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:setLeftArrowPressed(true);break;
		    case KeyEvent.VK_RIGHT:setRightArrowPressed(true);break;
		    case KeyEvent.VK_UP:setUpArrowPressed(true);break;
		    case KeyEvent.VK_DOWN:setDownArrowPressed(true);break;
		    default: //System.out.println("Unknown arrow key pressed");
		        break;
		        }
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{ 
		//To Lowercase to handle capital letters (capslock)
		char keyChar = Character.toLowerCase(e.getKeyChar()); 
		
		//WASD + space (released)
		switch (keyChar) 
		{
			case 'a':setKeyAPressed(false);break;  
			case 's':setKeySPressed(false);break;
			case 'w':setKeyWPressed(false);break;
			case 'd':setKeyDPressed(false);break;
			case ' ':setKeySpacePressed(false);break;   
		    default:
		        break;
		}  
	
		//Arrow Keys (released)
        switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:setLeftArrowPressed(false);break;
        case KeyEvent.VK_RIGHT:setRightArrowPressed(false);break;
        case KeyEvent.VK_UP:setUpArrowPressed(false);break;
        case KeyEvent.VK_DOWN:setDownArrowPressed(false);break;
        }
	}


	//WASD + space
	public boolean isKeyAPressed() {
		return KeyAPressed;
	}
	public void setKeyAPressed(boolean keyAPressed) {
		KeyAPressed = keyAPressed;
	}
	public boolean isKeySPressed() {
		return KeySPressed;
	}
	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}
	public boolean isKeyDPressed() {
		return KeyDPressed;
	}
	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}
	public boolean isKeyWPressed() {
		return KeyWPressed;
	}
	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}
	public boolean isKeySpacePressed() {
		return KeySpacePressed;
	}

	public void setKeySpacePressed(boolean keySpacePressed) {
		KeySpacePressed = keySpacePressed;
	} 
	
	// Arrow keys
    public boolean isRightArrowPressed() {
        return RightArrowPressed;
    }
    public void setRightArrowPressed(boolean rightArrowPressed) {
        RightArrowPressed = rightArrowPressed;
    }
    public boolean isLeftArrowPressed() {
        return LeftArrowPressed;
    }
    public void setLeftArrowPressed(boolean leftArrowPressed) {
        LeftArrowPressed = leftArrowPressed;
    }

    public boolean isUpArrowPressed() {
        return UpArrowPressed;	
    }

    public void setUpArrowPressed(boolean upArrowPressed) {
        UpArrowPressed = upArrowPressed;
    }
    public boolean isDownArrowPressed() {
        return DownArrowPressed;
    }
    public void setDownArrowPressed(boolean downArrowPressed) {
        DownArrowPressed = downArrowPressed;
    }
	
    
	//Mouse Controls
	private boolean leftMouseButtonPressed = false;
    public boolean isLeftMouseButtonPressed() {
        return leftMouseButtonPressed;
    }
    public void setLeftMouseButtonPressed(boolean value) {
        leftMouseButtonPressed = value;
    }
    public boolean mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            setLeftMouseButtonPressed(true);
            return true;
        } else {
            setLeftMouseButtonPressed(false);
            return false;
        }
    }
	
	 
}

