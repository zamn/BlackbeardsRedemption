package com.bbr.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

import com.bbr.gui.Drawable;

public class Zone implements Drawable {
	// Lists of Fliers
	protected List<Flyer> fliers = new ArrayList<Flyer>();
	protected List<Flyer> fliersToAdd = new ArrayList<Flyer>();
	protected List<Flyer> fliersToRemove = new ArrayList<Flyer>();
	// Scrolling
	protected int xscroll = 0, yscroll = 0;

	public Zone() {
	}
	///////////////////////////////////////////////////
	//               Add/Remove Fliers               //
	///////////////////////////////////////////////////
	public void addFlyer(Flyer flyer) {
		//flyer.container = this;
		fliersToAdd.add(flyer);
	}
	public void removeFlyer(Flyer flyer) {
		fliersToRemove.add(flyer);
	}
	public boolean hasFlyer(Flyer flyer) {
		return fliers.contains(flyer);
	}

	/////////////////////////////////////////////////////
	//               Collision Detection               //
	/////////////////////////////////////////////////////
	public Flyer getCollided(Flyer collider) {
		Flyer flyer;
		for (int i = 0; i < fliers.size(); i++) {
			flyer = fliers.get(i);
			if (flyer != collider) {
				if (flyer.collidesWith(collider)) {
					return flyer;
				}
			}
		}
		return null;
	}
	public void draw(Graphics g) {
		for (int i = 0; i < fliers.size(); i++) {
			fliers.get(i).draw(g);
		}
	}
	public void dt() {
		updateFliers();

		Flyer flyer;
		for (int i = 0; i < fliers.size(); i++) {
			flyer = fliers.get(i);
			flyer.dt();
		}

		// TODO tick
		updateFliers();
	}

	private void updateFliers() {
		fliers.addAll(fliersToAdd);
		fliersToAdd.clear();
		fliers.removeAll(fliersToRemove);
		fliersToRemove.clear();
	}
	public float getXscroll() {
		// TODO Auto-generated method stub
		return xscroll;
	}
	public float getYscroll() {
		// TODO Auto-generated method stub
		return yscroll;
	}
}
