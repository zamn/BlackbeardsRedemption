package com.bbr.level;

import java.util.ArrayList;
import java.util.List;

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
}
