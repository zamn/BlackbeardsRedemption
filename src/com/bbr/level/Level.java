package com.bbr.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;

import com.bbr.core.Zone;
import com.bbr.entity.player.Pirate;
import com.bbr.resource.Art;

// TODO some way to finalize a level to prevent changes. Don't call it finalize()
// TODO have level bounds?
public class Level {
	protected static List<Level> levels = new ArrayList<Level>();
	protected String levelName;
	
	static {
		LevelListReader llr = new LevelListReader(new File("data/levellist.txt"));
		try {
			llr.readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected int spawnX, spawnY;
	protected List<EntityEvent> entityEvents = new ArrayList<EntityEvent>();
	protected Image background;
	protected Level() { }

	public static Level loadLevel(String levelPath) throws FileNotFoundException {
		File f = new File(levelPath);
		LevelFileReader lfr = new LevelFileReader(f);
		lfr.readFile();
		levels.add(lfr.getLevel());
		lfr.getLevel().setName(f.getName());
		return lfr.getLevel();
	}
	
	public void setName(String name) {
		levelName = name;
	}
	
	public String getName() {
		return levelName;
	}
	
	public static Level getFirstLevel() {
		return levels.get(0);
	}
	public static Level getNextLevel(Level curLevel) {
		int index = levels.indexOf(curLevel);
		if (index >= 0 && index < levels.size() - 1) {
			return levels.get(index + 1);
		}
		return null;
	}
	
	public static Level gameOver() {
		for (int i = 0; i < levels.size(); i++) {
			if (levels.get(i).getName().equals("gameover.txt"))
				return levels.get(i);
		}
		return null; // we have no game over screen :x
	}

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

	public void loadInto(Zone zone) {
		Pirate p = new Pirate(zone, spawnX, spawnY);
		zone.addEntity(p);
		zone.follow(p);
		zone.setBackground(background);
		for (EntityEvent ee : entityEvents) {
			ee.trigger(zone);
		}
	}
	public void setBackground(String bg){
		this.background = Art.getImage(bg);
	}
}
