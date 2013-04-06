package com.bbr.level;

import com.bbr.core.Zone;
import com.bbr.enemy.GhostPirate;
import com.bbr.entity.Entity;

public class EntityEvent {
	public enum EntityType { GHOSTPIRATE }
	protected EntityType entityType;
	protected int sx, sy;
	protected int px, py;

	protected EntityEvent(String entityName, int sx, int sy, int px, int py) {
		this.entityType = EntityType.valueOf(entityName);
		this.sx = sx;
		this.sy = sy;
		this.px = px;
		this.py = py;
	}

	public void trigger(Zone zone) {
		Entity e = null;
		switch (entityType) {
		case GHOSTPIRATE:
			e = new GhostPirate(zone, px, py);
			zone.addEntity(e);
			break;
		}
		if (e != null) {
			if (sx > 0) e.setXsize(sx);
			if (sy > 0) e.setYsize(sy);
		}
	}
}
