import javax.sound.sampled.*;
import java.io.IOException;

public class SoundManager {

    public static void playBackgroundMusic() {
        playSound("westernguitar.wav", true);
    }

    public static void GameOverSounds() {
    	playSound("churchbell.wav", false);
    	playSound("rattlesnake.wav", false);
    }

    //Gunshot
    public static void playGunshot() {
        playSound("gunshot.wav", false);
    }
    //HammerPU
    public static void playHammerPU() {
        playSound("hammer.wav", false);
    }
    //FireRatePU
    public static void playFireRatePU() {
        playSound("yeehaw.wav", false);
    }
    //GameOverSound
    public static void playGameOver() {
        playSound("yahoy.wav", false);
    }
    
    //PlaySound Method
    private static void playSound(String soundFilePath, boolean loop) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    SoundManager.class.getClassLoader().getResource(soundFilePath));

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
                clip.drain();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
