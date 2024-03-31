import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import util.UnitTests;

public class MainWindow {
	//Frame Title
    private static JFrame frame = new JFrame("A Fistful O' Brains");
    private static Model gameworld = new Model();
    private static Viewer canvas = new Viewer(gameworld);
    private Controller controller = new Controller();
    private static int TargetFPS = 100;
    private static boolean startGame = false;
    private JLabel BackgroundImageForStartMenu;
    private JLabel BackgroundImageForEndMenu;
    private JLabel tutorialMessage;
    private JButton startGameButton;
    
    public MainWindow() {
    	frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.add(canvas);
        canvas.setBounds(0, 0, 1000, 1000);
        canvas.setBackground(new Color(255, 255, 255));
        canvas.setVisible(false);
        
        //Display Tutorial Message
        tutorialMessage = new JLabel(new ImageIcon("res/cowboyTutorial.png"));
        tutorialMessage.setBounds(0, -50, 1000, 900);
        tutorialMessage.setVisible(false);

        
        JButton startMenuButton = new JButton("Start Game");
        startMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenuButton.setVisible(false);
                BackgroundImageForStartMenu.setVisible(false);
                frame.add(tutorialMessage);
                tutorialMessage.setVisible(true);
                startGameButton.setVisible(true);
            }
        });
        startMenuButton.setBounds(400, 500, 200, 40);
        

        // Start Game button (invisible and covers entire screen [click to continue])
        startGameButton = new JButton("");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tutorialMessage.setVisible(false);
                startGameButton.setVisible(false);
                canvas.setVisible(true);
                canvas.addKeyListener(controller);
                canvas.requestFocusInWindow();
                startGame = true;
            }
        });
        startGameButton.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        startGameButton.setVisible(false);
        
        startGameButton.setOpaque(false);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setBorderPainted(false);
        frame.add(startGameButton);
        
        //Main Menu Art
        File backgroundToLoad = new File("res/MenuScreen.png");
        try {
            BufferedImage myPicture = ImageIO.read(backgroundToLoad);
            BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
            BackgroundImageForStartMenu.setBounds(0, 0, 1025, 1025);
            frame.add(BackgroundImageForStartMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Controller.getInstance().mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Controller.getInstance().setLeftMouseButtonPressed(false);
                }
            }
        });
        frame.add(startMenuButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        long inGameTimer = System.currentTimeMillis();
        MainWindow hello = new MainWindow();

        while (true) {
            int timeBetweenFrames = 1000 / TargetFPS;
            long frameCheck = System.currentTimeMillis() + (long) timeBetweenFrames;

            while (frameCheck > System.currentTimeMillis()) {
                // Waiting for the next frame
            }

            //While gameloop is active
            if (startGame) {
                gameloop();

        
                //Conditions for gameover
                if (gameworld.getBase().getHealth() <= 0) {
                    long currentTime = System.currentTimeMillis();

                    // Show Game Over screen when base health hits 0, with delay of 1 second
                    if (currentTime - inGameTimer >= 1000) {
                        startGame = false;
                        hello.GameOver(); 
                    }
                } else {
                    inGameTimer = System.currentTimeMillis();
                }
            }
            UnitTests.CheckFrameRate(System.currentTimeMillis(), frameCheck, TargetFPS);
        }
    }
    
    //Game over Screen
    private void GameOver() {
    	//Game Over Art
		File gameOverScreen = new File("res/GameOverScreen.png");
	   
		//Try Again Button, will display score and accuracy
		JButton TryAgainButton = new JButton("<html>Darn Tootin'! <br>Score: " + gameworld.getScore()
		+ "<br>Accuracy: " + gameworld.getAccuracy()+"% <br>(Click to Try Again)");

		//Parameters for try again button
		TryAgainButton.setBackground(Color.red);
		TryAgainButton.setOpaque(true);
		TryAgainButton.setBorderPainted(false);
		TryAgainButton.setFocusPainted(false);
		Font buttonFont = TryAgainButton.getFont();
		TryAgainButton.setFont(new Font(buttonFont.getName(), Font.BOLD, buttonFont.getSize()));
		TryAgainButton.setForeground(Color.white);

		TryAgainButton.addActionListener(new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e) {
			TryAgainButton.setVisible(false);
			BackgroundImageForEndMenu.setVisible(false);
			canvas.setVisible(true);
			canvas.removeKeyListener(controller);
			gameworld.restartGame();
            startGame = true;
            // Add the key listener again to override controls (this was a bug fix for player movement not working on game restart)
            canvas.addKeyListener(controller);
            canvas.requestFocusInWindow();
        }
    });

		TryAgainButton.setBounds(425,350,200,65);
		try {
			BufferedImage goScreen = ImageIO.read(gameOverScreen);
			BackgroundImageForEndMenu = new JLabel(new ImageIcon(goScreen));
			int imageHeight = goScreen.getHeight(); 
			int imageWidth = goScreen.getWidth(); 
            BackgroundImageForEndMenu.setBounds(0, -50, imageWidth, imageHeight - 140);
            frame.add(TryAgainButton);
            frame.add(BackgroundImageForEndMenu);
          
            canvas.setVisible(false);
            BackgroundImageForEndMenu.setVisible(true);
            TryAgainButton.setVisible(true);
            TryAgainButton.setFocusable(false);
            
            } catch(IOException e) {
            	e.printStackTrace();
            }

		frame.setVisible(true);
	}

  
	private static void gameloop() {
        gameworld.gamelogic();
        canvas.updateview();
        frame.setTitle("A Fistful O' Brains");
    }
}