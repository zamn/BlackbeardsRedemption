package com.bbr.level;

import java.util.ArrayList;
import java.util.List;

import com.bbr.core.Zone;
import com.bbr.player.Pirate;

// TODO some way to finalize a level to prevent changes. Don't call it finalize()
// TODO have level bounds?
public class Level {
	protected int spawnX, spawnY;
	protected List<EntityEvent> entityEvents = new ArrayList<EntityEvent>();

	protected Level() { }

	public void setSpawnPoint(int spawnX, int spawnY) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}

	public void addEntityEvent(String entityName, int px, int py) {
		addEntityEvent(entityName, -1,-1, px,py);
	}
	public void addEntityEvent(String entityName, int sx, int sy, int px, int py) {
		entityEvents.add(new EntityEvent(entityName,sx,sy,px,py));
	}

	public void loadLevel(Zone zone) {
		Pirate p = new Pirate(zone, spawnX, spawnY);
		zone.addEntity(p);
		zone.follow(p);
		// Java ee
		for (EntityEvent ee : entityEvents) {
			ee.trigger(zone);
		}
	}
}
