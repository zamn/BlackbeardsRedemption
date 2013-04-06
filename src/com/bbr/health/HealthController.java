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
	public HealthController(String imageName, int defaultHealth){
		unitImage = Art.getImage(imageName);
		changeHealth(defaultHealth);
	}
	public void changeHealth(int newHealth){
		if(newHealth < 0)
			return;
		numUnites = (newHealth / healthPerHeart);
		System.out.println("NumUnits: "+numUnites);
		for(int i=0; i<numUnites; i++){
			System.out.println("x: "+(i * unitImage.getWidth())+", y: 0");
		}

	}	
	public void draw(){
		for(int i=0; i<numUnites; i++){
			unitImage.draw(i * unitImage.getWidth(), 0);
		}

	}

}
