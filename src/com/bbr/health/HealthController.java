//The controller for Health.java
package com.bbr.health;
import org.newdawn.slick.Image;

import com.bbr.player.Player;
import com.bbr.resource.Art;
public class HealthController{
	private int healthPerHeart = 100;
	private Image positiveUnitImage;
	private Image negativeUnitImage;
	private Player p;
	private int numTotalunits = 0;

	public HealthController(String positiveImage, String negativeImage, Player p){
		positiveUnitImage = Art.getImage(positiveImage);
		negativeUnitImage = Art.getImage(negativeImage);
		this.p = p;
		numTotalunits = p.getBaseHealth() / healthPerHeart;

	}

	public void draw(){
		int numUnits = 0;
		if(p.getHealth() > 0)
			numUnits = p.getHealth() / healthPerHeart;
		for(int i=0; i<numTotalunits; i++){
			if(i < numUnits){
				positiveUnitImage.draw(i * positiveUnitImage.getWidth(), 0);
			} else{
				negativeUnitImage.draw(i* negativeUnitImage.getWidth(), 0);
			}
				
		}

	}

}
