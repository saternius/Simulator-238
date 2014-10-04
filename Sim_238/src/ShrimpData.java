import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;


public class ShrimpData {
	Shrimp shrimp;
	DecimalFormat d = new DecimalFormat("#.###");
	DecimalFormat dd = new DecimalFormat("#.#####");
	int shiftX=-23;
	int shiftY=0;
	public ShrimpData(Shrimp shrimp) {
		this.shrimp = shrimp;
	}
	

	public void draw(Graphics2D stage) {
		stage.setColor(new Color(102,153,255,200));
		stage.fillRect(545+shiftX, 3+shiftY, 275, 560);
		stage.setColor(new Color(192,199,218));
		stage.fillRect(550+shiftX, 7+shiftY, 68, 68);
		AffineTransform old = stage.getTransform();
        stage.translate(584+shiftX, 40+shiftY);
        stage.setColor(new Color(shrimp.skin_red,shrimp.skin_green,shrimp.skin_blue));
        stage.rotate(Math.toRadians(shrimp.rotation));
		stage.fillPolygon(shrimp.bodyX, shrimp.bodyY, shrimp.bodyX.length);
        stage.setTransform(old);
        
		stage.setColor(new Color(25,37,63));
		stage.setFont(new Font("Arial", Font.PLAIN, 18));
		stage.drawString("Organism #"+shrimp.shrimpID,625+shiftX,25+shiftY);
		stage.setFont(new Font("Arial", Font.PLAIN, 12));
		
		//This is where the facts come in
		stage.drawString("Food Eaten: "+shrimp.foodEaten,625+shiftX,42+shiftY);
		int seconds = (shrimp.age*Game.CLICKS_DELAY)/1000;
		stage.drawString("Alive for: "+seconds+" seconds",625+shiftX,60+shiftY);
		
		//Appearances and Aging
		stage.drawString("Apperance",550+shiftX,90+shiftY);
		stage.drawString("Initial Size: "+d.format(shrimp.initial_size), 565+shiftX, 105+shiftY);
		stage.drawString("Growth: "+d.format(shrimp.growth), 565+shiftX, 120+shiftY);
		stage.drawString("Color(RGB): "+"("+shrimp.skin_red+","+shrimp.skin_green+","+shrimp.skin_blue+")", 565+shiftX, 135+shiftY);
		stage.drawString("Initial Health: "+d.format(shrimp.init_healthBar),565+shiftX,150+shiftY);
		stage.drawString("Max Health: "+d.format(shrimp.maxHealth), 565+shiftX, 165+shiftY);
		
		//Food 
		stage.drawString("Food Habits",550+shiftX,180+shiftY);
		stage.drawString("Metabolism: "+d.format(shrimp.metabolism), 565+shiftX, 195+shiftY);
		stage.drawString("Percent of food properly digested: "+d.format(shrimp.digestiveSystem), 565+shiftX, 210+shiftY);
		stage.drawString("Health at which to search for food: "+d.format(shrimp.care), 565+shiftX,225+shiftY);
		stage.drawString("Searching for food Speed: "+d.format(shrimp.hunt_speed), 565+shiftX, 240+shiftY);
		stage.drawString("Running at food speed: "+d.format(shrimp.food_speed), 565+shiftX, 255+shiftY);
		stage.drawString("Acceleration: "+d.format(shrimp.acceleration), 565+shiftX, 270+shiftY);
		stage.drawString("Energy burned from moving: "+d.format(shrimp.running_burn), 565+shiftX, 285+shiftY);
		stage.drawString("Dexterity (rotation speed): "+d.format(shrimp.dexterity), 565+shiftX, 300+shiftY);
		stage.drawString("Sight range: "+d.format(shrimp.sight), 565+shiftX, 315+shiftY);
		stage.drawString("Sight Angle: "+d.format(shrimp.rightSightAngle-shrimp.leftSightAngle), 565+shiftX, 330+shiftY);
		stage.drawString("Energy Burned from size: "+dd.format(shrimp.sizeBurn), 565+shiftX, 345+shiftY);
		stage.drawString("Energy Burned from good vision: "+dd.format(shrimp.sightBurn), 565+shiftX, 360+shiftY);
		
		//Reproduction
		stage.drawString("Reproduction ", 550+shiftX, 375+shiftY);
		stage.drawString("fertility(number of children): "+d.format(shrimp.fertility), 565+shiftX, 390+shiftY);
		stage.drawString("Min/Max Age to reproduce: "+"("+Math.round(shrimp.rep_MinTimer)+" to " +Math.round(shrimp.rep_MaxTimer)+")", 565+shiftX, 405+shiftY);
		stage.drawString("Min/Max health to reproduce: "+"("+d.format(shrimp.rep_HungerMin)+" to "+d.format(shrimp.rep_HungerMax)+ ")", 565+shiftX, 420+shiftY);
		stage.drawString("Min Food Eaten to reproduce: "+d.format(shrimp.minFoodEatenForRep), 565+shiftX, 435+shiftY);
		stage.drawString("Reproduction Recovery Time: "+d.format(shrimp.rep_Recovery), 565+shiftX, 450+shiftY);
		stage.drawString("Health lost per offspring: "+d.format(shrimp.preg_cost), 565+shiftX, 465+shiftY);
		stage.drawString("Bith Ejection Speed: "+d.format(shrimp.birth_spread), 565+shiftX, 480+shiftY);
		//Death
		stage.drawString("Death ", 560+shiftX, 495+shiftY);
		stage.drawString("Time before body explodes: "+d.format(shrimp.deathTimer), 565+shiftX, 510+shiftY);
		stage.drawString("Spread of corpse: "+d.format(shrimp.deathSpread), 565+shiftX, 525+shiftY);
		stage.drawString("Number of food spread: "+d.format(shrimp.deathAmount), 565+shiftX, 540+shiftY);
		stage.drawString("Nutrition of food(0-255): "+"("+shrimp.deathMinNutrition+"-"+shrimp.deathMaxNutrition+")", 565+shiftX, 555+shiftY);
	}

}
