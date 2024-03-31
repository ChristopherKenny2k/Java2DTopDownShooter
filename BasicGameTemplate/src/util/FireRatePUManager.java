package util;

import java.util.concurrent.CopyOnWriteArrayList;

public class FireRatePUManager {
public CopyOnWriteArrayList<FireRatePU> FireRateList = new CopyOnWriteArrayList<>();
	
	public CopyOnWriteArrayList<FireRatePU> getFireRateList() {
		return FireRateList;
	}

public void SpawnFireRatePU(Point3f spawnlocation) {
		
        FireRatePU fireRatePU = new FireRatePU("res/FireRatePowerUp.png", 54, 54, spawnlocation);
        FireRateList.add(fireRatePU);
         }

}
