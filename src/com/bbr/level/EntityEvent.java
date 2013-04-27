package com.bbr.level;

import com.bbr.core.Zone;
import com.bbr.enemy.Arboc;
import com.bbr.enemy.GhostPirate;
import com.bbr.enemy.SenorRat;
import com.bbr.enemy.Snake;
import com.bbr.enemy.SnakeSpawner;
import com.bbr.entity.Entity;
import com.bbr.entity.terrain.BreakablePlatform;
import com.bbr.entity.terrain.Exit;
import com.bbr.entity.terrain.FakeSpike;
import com.bbr.entity.terrain.Ground;
import com.bbr.entity.terrain.Platform;
import com.bbr.entity.terrain.Spike;
import com.bbr.entity.terrain.FallingPlatform;
/*
 * This class spawns entities that you add. In order to add an entity you must:
 * 	- Add the entity as an enum value in EntityType
 * 	- Add the path to the entity in data/spritelist.txt
 * 	- Create the class for the Entity (with the same name as enum) in com.bbr.entity.*
 * 	- Add a block in the case statement in trigger spawning it into zone.
 */
public class EntityEvent {
	public enum EntityType { BREAKABLEPLATFORM, FALLINGPLATFORM, PLATFORM, SPIKE, FAKESPIKE, EXIT,
		GHOSTPIRATE, SNAKE, SNAKESPAWNER,
		ARBOC, SENORRAT, BACKGROUND, GROUND }
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
		case BREAKABLEPLATFORM:
			e = new BreakablePlatform(zone, px, py);
			zone.addEntity(e);
			break;
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
		case FAKESPIKE:
			e = new FakeSpike(zone, px, py);
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
		case SNAKESPAWNER:
			e = new SnakeSpawner(zone, px, py);
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
		case GROUND:
			e = new Ground(zone, px, py);
			zone.addEntity(e);
			break;
		}
		if (e != null) {
			if (sx > 0) e.setXsize(sx);
			if (sy > 0) e.setYsize(sy);
		}
	}
}
