//The GUI for the Health meter
package com.bbr.health;
import org.newdawn.slick.Image;
import com.bbr.resource.Art;
public class HealthBar{
	private Image unitImage;
	private int numUnits=0;
	public HealthBar(String imageFileName){
		unitImage = Art.getImage(imageFileName);
	}
	public void changeNumUnits(int num){
		numUnits = num;
	}
	public void draw(){
		for(int i=0; i<numUnits; i++){
			unitImage.draw(numUnits*unitImage.getWidth(), 0);
		}
	}
}
