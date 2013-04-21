package com.bbr.level;

import com.bbr.core.Zone;
import com.bbr.enemy.Arboc;
import com.bbr.enemy.GhostPirate;
import com.bbr.enemy.SenorRat;
import com.bbr.enemy.Snake;
import com.bbr.entity.Entity;
import com.bbr.entity.terrain.Exit;
import com.bbr.entity.terrain.Platform;
import com.bbr.entity.terrain.Spike;
import com.bbr.entity.terrain.FallingPlatform;

public class EntityEvent {
	public enum EntityType { FALLINGPLATFORM, PLATFORM, SPIKE, EXIT,
		GHOSTPIRATE, SNAKE,
		ARBOC, SENORRAT, BACKGROUND }
	protected EntityType entityType;
	protected int sx, sy;
	protected int px, py;

	protected EntityEvent(String entityName, int sx, int sy, int px, int py) {
		this.entityType = EntityType.valueOf(entityName.toUpperCase());
		this.sx = sx;
		this.sy = sy;
		this.px = px;
		this.py = py;
	}

	public void trigger(Zone zone) {
		Entity e = null;
		switch (entityType) {
		case FALLINGPLATFORM:
			e = new FallingPlatform(zone, px, py);
			zone.addEntity(e);
			break;
		case PLATFORM:
			e = new Platform(zone, px, py);
			zone.addEntity(e);
			break;
		case SPIKE:
			e = new Spike (zone, px, py);
			zone.addEntity(e);
			break;
		case EXIT:
			e = new Exit(zone, px, py);
			zone.addEntity(e);
			break;

		case GHOSTPIRATE:
			e = new GhostPirate(zone, px, py);
			zone.addEntity(e);
			break;
		case SNAKE:
			e = new Snake(zone, px, py);
			zone.addEntity(e);
			break;

		case ARBOC:
			e = new Arboc(zone, px, py);
			zone.addEntity(e);
			break;
		case SENORRAT:
			e = new SenorRat(zone, px, py);
			zone.addEntity(e);
			break;
		}
		if (e != null) {
			if (sx > 0) e.setXsize(sx);
			if (sy > 0) e.setYsize(sy);
		}
	}
}
