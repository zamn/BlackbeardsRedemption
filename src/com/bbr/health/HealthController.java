//The controller for Health.java
package com.bbr.health;
import org.newdawn.slick.Image;

import com.bbr.entity.Unit;
import com.bbr.resource.Art;
public class HealthController{
	private int healthPerHeart = 100;
	private Image positiveUnitImage;
	private Image negativeUnitImage;
	private Unit unit;
	private int numTotalUnits = 0;

	public HealthController(String positiveImage, String negativeImage, Unit unit){
		positiveUnitImage = Art.getImage(positiveImage);
		negativeUnitImage = Art.getImage(negativeImage);
		this.unit = unit;
		numTotalUnits = unit.getBaseHealth() / healthPerHeart;

	}
	public void changeUnit(Unit unit){
		this.unit = unit;
	}
	public void draw(){
		int numUnits = 0;
		if(unit.getHealth() > 0)
			numUnits = unit.getHealth() / healthPerHeart;
		for(int i=0; i<numTotalUnits; i++){
			if(i < numUnits){
				positiveUnitImage.draw(i * positiveUnitImage.getWidth(), 0);
			} else{
				negativeUnitImage.draw(i* negativeUnitImage.getWidth(), 0);
			}
				
		}

	}

}
