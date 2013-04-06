//The controller for Health.java
package com.bbr.health;
import org.newdawn.slick.Image;
import com.bbr.resource.Art;
public class HealthController{
	private int healthPerHeart = 100;
	private int numUnites;
	private Image unitImage;
	public HealthController(String imageName){
		unitImage = Art.getImage(imageName);
	}
	public void changeHealth(int newHealth){
		if(newHealth < 0)
			return;
		numUnites = (newHealth / healthPerHeart);
	}	
	public void draw(){
		for(int i=0; i<numUnites; i++){
			unitImage.draw(numUnites * unitImage.getWidth(), 0);
		}
	}

}
