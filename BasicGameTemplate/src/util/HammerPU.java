package util;


public class HammerPU extends GameObject{
	
	//Time variables for flashing sprite and item deletion
	private long SpawnTime;
	private final long flashingTrigger = 5000;
	private final long reallyFlashingTrigger = 8000;
	private final long deleteTimer= 10000;
	
	public HammerPU(String textureLocation, int width, int height, Point3f spawnLocation) {
        super(textureLocation, width, height, spawnLocation);
        
        this.SpawnTime = System.currentTimeMillis(); 
    }
	
	public boolean flashingCheck() {
		return (System.currentTimeMillis() - SpawnTime) > flashingTrigger && 
				(System.currentTimeMillis() - SpawnTime) < reallyFlashingTrigger;

	}

	public boolean reallyFlashingCheck() {
		return (System.currentTimeMillis() - SpawnTime) > reallyFlashingTrigger;

	}
	public long getSpawnTime() {
		return SpawnTime;
	}

	public void setSpawnTime(long spawnTime) {
		SpawnTime = spawnTime;
	}

	public long getFlashingTrigger() {
		return flashingTrigger;
	}

	public long getDeleteTimer() {
		return deleteTimer;
	}
	
	
	
}
