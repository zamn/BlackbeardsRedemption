//The controller for Health.java
package com.bbr.health;
import org.newdawn.slick.Image;

import com.bbr.player.Player;
import com.bbr.resource.Art;
public class HealthController{
	private int healthPerHeart = 100;
	private Image unitImage;
	private Player p;
	public HealthController(String imageName){
		unitImage = Art.getImage(imageName);
	}
	public HealthController(String imageName, Player p){
		unitImage = Art.getImage(imageName);
		this.p = p;
	}

	public void draw(){
		int numUnits = 0;
		if(p.getHealth() > 0)
			numUnits = p.getHealth() / healthPerHeart;
		for(int i=0; i<numUnits; i++){
			unitImage.draw(i * unitImage.getWidth(), 0);
		}

	}

}
