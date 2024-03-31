package util;

import java.util.concurrent.CopyOnWriteArrayList;

public class HammerPUManager {
public CopyOnWriteArrayList<HammerPU> HammerList = new CopyOnWriteArrayList<>();
	
	public CopyOnWriteArrayList<HammerPU> getHammerList() {
		return HammerList;
	}

public void SpawnHammerPU(Point3f spawnlocation) {
		
        HammerPU hammerPU = new HammerPU("res/HammerPowerUp.png", 54, 54, spawnlocation);
        HammerList.add(hammerPU);
         }


	
}
