import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import edu.wlu.cs.levy.CG.KeyMissingException;
import edu.wlu.cs.levy.CG.KeySizeException;



public class Shrimp {
	//Developer variables 
	public static boolean drawSight = false;
	public static boolean drawAxis = false;
	public static boolean drawPOVAxis = false;
	public static boolean debug = false;
	public static double mutationRate =.025;
	
	//TODO: Make genetics visible on sight.
	//TODO: Implement genetic effect on metabolic rate.
	//TODO: Implement an array of integers to effect growth.
	public static int shrimpIDer = 0;
	public static int seek = 1;
	public static int seekMinima = 3;
	public static int seekMaxima = 6;
	public String debugString = "test";
	
	
	//GENETIC inheritance
		//STANDARD 
		public double growth=0;
		public double metabolism=.015;
		public double food_speed=.5;
		public double acceleration=.1;
		public double dexterity=80;
		public double sight=100;
		public double health=12;
		public double care=10;
		public double hunt_speed=.25;
		public int skin_red = (int) (Math.random()*255);
		public int skin_blue = (int) (Math.random()*255);
		public int skin_green= (int) (Math.random()*255);
		public double initial_size=.14;
		public double running_burn=.01;
		public double digestiveSystem=.04;
		public int deathTimer=30;
		public double deathSpread=3;
		public double deathAmount=.5;
		public int deathMaxNutrition=200;
		public int deathMinNutrition=70;
		public double maxHealth =10;
		public double rep_MinTimer=200;
		public double rep_MaxTimer=6000;
		public double rep_HungerMin=.33;
		public double rep_HungerMax=.85;
		public double rep_Recovery=250;
		public double fertility=3;
		public double preg_cost=1;
		public double birth_spread=1;
		public double minFoodEatenForRep=2;
		
		public double leftSightAngle=250;
		public double rightSightAngle=290;
		public double foodSeeks = 1.0;
		
		public double sightBurn=.00001;
		public double sizeBurn=.00001;
		
		public ArrayList<Integer>liniage;
		public int init_healthBar=12;
		
		int[] bodyX = {-44,-36,-36,-19,-19,19,19,36,36,44,44,36,36,19,19,-19,-19,-36,-36,-44};
		int[] bodyY = {-17,-17,-46,-46,-56,-56,-46,-46,-17,-17,21,21,37,37,57,57,37,37,21,21};
		
		//WEAPONS/COMBAT
		//fear
		//poison
		//sprint
		//etc
		
		//CURRRENT STATE
		int shrimpID = 0;
		double x;
		double y;
		double xVel=0;
		double yVel=0;
		double rotation;
		double speed = 0;
		double width = initial_size*110;
		int age = 0;
		int recovery =100000;
		int foodEaten = 0;
		boolean glow = false;
		private boolean alive= true;
		
		public Shrimp(){
			x =  (Math.random()*Game.width);
			y = (Math.random()*Game.height);
			rotation = (Math.random()*360);
			growth = mutate(growth);
			metabolism = mutate(metabolism);
			food_speed = mutate(food_speed);
			acceleration = mutate(acceleration);
			dexterity = mutate(dexterity);
			fertility = mutate(fertility);
			sight = mutate(sight);
			initial_size = mutate(initial_size);
			hunt_speed = mutate(hunt_speed);
			running_burn = mutate(running_burn);
			care = mutate(care);
			digestiveSystem = mutate(digestiveSystem);
			
			deathTimer = (int) mutate(deathTimer);
			deathSpread = mutate(deathSpread);
			deathAmount = mutate(deathAmount);
			deathMaxNutrition = mutate(deathMaxNutrition,1,255);
			deathMinNutrition = mutate(deathMinNutrition,0,254);
			
			rep_MinTimer = mutate(rep_MinTimer);
			rep_MaxTimer = mutate(rep_MaxTimer);
			rep_HungerMin = mutate(rep_HungerMin);
			rep_HungerMax = mutate(rep_HungerMax);
			rep_Recovery = mutate(rep_Recovery);
			preg_cost = mutate(preg_cost);
			birth_spread = mutate(birth_spread);
			
			maxHealth = mutate(maxHealth);
			init_healthBar = (int)mutate(init_healthBar);
			minFoodEatenForRep = mutate(minFoodEatenForRep);
			leftSightAngle = mutate(leftSightAngle);
			rightSightAngle = mutate(rightSightAngle);
			foodSeeks = mutate(foodSeeks);
			
			sightBurn = mutate(sightBurn);
			sizeBurn = mutate(sizeBurn);
			health = init_healthBar;
			bodyX = mutate(bodyX);
			bodyY = mutate(bodyY);
			changeSizenPos(initial_size);
			width = initial_size*110;
			shrimpID=shrimpIDer;
			shrimpIDer++;
		}
	
		public Shrimp(double x2, double y2, double d,
				double growth2, double metabolism2, double food_speed2,
				double acceleration2, double dexterity2, double fertility2,
				double sight2, double initial_size2, double hunt_speed2,
				double running_burn2, double care2,
				double digestiveSystem2, double deathSpread2,
				double deathAmount2, int deathMaxNutrition, int deathMinNutrition,
				double rep_MinTimer2, double rep_MaxTimer2,
				double rep_HungerMin2, double rep_HungerMax2,
				double rep_Recovery2, double preg_cost2, double birth_spread2,
				double maxHealth2, int init_healthBar2, int skin_red2,
				int skin_green2, int skin_blue2, double birth_spread3, double minFoodEatenForRep2,
				double leftSightAngle, double rightSightAngle,
				double sightBurn2, double sizeBurn2, int[] bodyX2, int[] bodyY2,double foodSeeks2) {
			this.x = x2;
			this.y = y2;
			this.rotation = d;
			this.growth = mutate(growth2);
			this.metabolism = mutate(metabolism2);
			this.food_speed = mutate(food_speed2);
			this.acceleration = mutate(acceleration2);
			this.dexterity = mutate(dexterity2);
			this.fertility = mutate(fertility2);
			this.sight = mutate(sight2);
			this.initial_size = mutate(initial_size2);
			this.hunt_speed = mutate(hunt_speed2);
			this.running_burn = mutate(running_burn2);
			this.care = mutate(care2);
			this.digestiveSystem = mutate(digestiveSystem2);
			this.deathSpread = mutate(deathSpread2);
			this.deathAmount = mutate(deathAmount2);
			this.deathMaxNutrition = deathMaxNutrition;
			this.deathMinNutrition = deathMinNutrition;
			this.rep_MinTimer = mutate(rep_MinTimer2);
			this.rep_MaxTimer = mutate(rep_MaxTimer2);
			this.rep_HungerMin = mutate(rep_HungerMin2);
			this.rep_HungerMax = mutate(rep_HungerMax2);
			this.rep_Recovery = mutate(rep_Recovery2);
			this.preg_cost = mutate(preg_cost2);
			this.birth_spread = mutate(birth_spread2);
			this.maxHealth = mutate(maxHealth2);
			this.init_healthBar = (int) mutate(init_healthBar2);
			//health = init_healthBar;
			health = 8;
			this.skin_red = (int) multiMutate(skin_red2,0,255,1.5);
			this.skin_green = (int) multiMutate(skin_green2,0,255,1.5);
			this.skin_blue = (int) multiMutate(skin_blue2,0,255,1.5);
			this.speed = mutate(birth_spread3);
			this.minFoodEatenForRep = mutate(minFoodEatenForRep2);
			this.leftSightAngle = mutate(leftSightAngle);
			this.rightSightAngle = mutate(rightSightAngle);
			this.foodSeeks = mutate(foodSeeks2);
			this.sightBurn = mutate(sightBurn2);
			this.sizeBurn = mutate(sizeBurn2);
			
			this.bodyX = mutate(bodyX);
			this.bodyY = mutate(bodyY);
			changeSizenPos(initial_size);
			width = initial_size*110;
			shrimpID=shrimpIDer;
			shrimpIDer++;
		}

		private int multiMutate(int val, int i, int j, double d) {
			double plusOrMinus = (Math.random()*(val*(mutationRate*d)*2))-((val*(mutationRate*d)));
			int ret = (int) (val+plusOrMinus);
			if(ret<i){
				return i;
			}
			if(ret>j){
				return j;
			}
			return ret;
		}

		private int[] mutate(int[] body) {
			int[] returnBody = new int[body.length];
			for(int i=0; i<body.length;i++){
				returnBody[i] = (int) mutate(body[i]);
			}
			return returnBody;
		}

		private int mutate(int val, int i, int j) {
			double plusOrMinus = (Math.random()*(val*mutationRate*2))-((val*mutationRate));
			int ret = (int) (val+plusOrMinus);
			if(ret<i){
				return i;
			}
			if(ret>j){
				return j;
			}
			return ret;
		}

		private double mutate(double val) {
			double plusOrMinus = (Math.random()*(val*mutationRate*2))-((val*mutationRate));
			return val+plusOrMinus;
		}

		private void changeSizenPos(double d) {
			for(int i=0; i<bodyX.length;i++){
				bodyX[i]*=d;
				bodyY[i]*=d;
			}
		}

		public void draw(Graphics2D stage) {
		        live();	
		        if(drawAxis){
			         stage.setColor(Color.YELLOW);
			         stage.drawLine((int)x,(int)y,(int)(x+sight/2),(int)y);
			         stage.setColor(Color.PINK);
			         stage.drawLine((int)x,(int)y,(int)x,(int)(y+sight/2));
		        }
		        
			
			
			
				AffineTransform old = stage.getTransform();
		        stage.translate(x, y);
		        stage.rotate(Math.toRadians(rotation));
		        //Draw shrimp
		        
		        if(drawSight){
		        	 stage.setColor(Color.YELLOW);
		        	 stage.setStroke(new BasicStroke(1));
		        	 int vXL = (int) (Math.cos(Math.toRadians(leftSightAngle))*sight/2);
		        	 int vYL = (int)(Math.sin(Math.toRadians(leftSightAngle))*sight/2);
				     int vXR = (int) (Math.cos(Math.toRadians(rightSightAngle))*sight/2);
					 int vYR = (int)(Math.sin(Math.toRadians(rightSightAngle))*sight/2);
			         stage.drawLine(0,0,(int)(vXL),(int)(vYL));
			         stage.drawLine(0,0,(int)(vXR),(int)(vYR));
			         //stage.setStroke(new BasicStroke(1));
			         //stage.drawOval((int)-sight/2, (int)-sight/2, (int)sight, (int)sight);
			    }
		        
		        if(drawPOVAxis){
		        	 stage.setColor(Color.CYAN);
			         stage.drawLine(0,0,(int) (sight/2),0);
			         stage.setColor(Color.BLUE);
			         stage.drawLine(0,0,0,(int)(sight/2));
		        }
		        
		        stage.setColor(new Color(skin_red,skin_green,skin_blue));
		        stage.fillPolygon(bodyX, bodyY, bodyX.length);
		        if(glow){
		        	stage.setColor(Color.CYAN);
		        	stage.drawPolygon(bodyX,bodyY,bodyX.length);
		        }
		        stage.setColor(new Color(204,0,0));
		        stage.fillRect((int)(-init_healthBar/2), -12, (int)(health), 3);
		        stage.setTransform(old);
		        
		        
		       
		        if(debug){
		        	stage.setColor(Color.WHITE);
		        	stage.drawString(debugString,(int)x,(int)y);
		        }
		}

		private void live() {
			grow();
			health-=sight*sightBurn;
			health-=width*sizeBurn;
			health-=metabolism;
			if(alive){
				reproduce();
				if(health<care){
					seekFood();
				}
				if(health<1){
					alive = false;
					Tree.died(shrimpID);
				}
			}else{
				performDeath();
				speed = 0;
			}
			
			speed*=Game.FRICTION;
			double angle= ((rotation-90)*Math.PI/180);
			xVel = (Math.cos(angle)*speed);
			yVel = (Math.sin(angle)*speed);
			x+=xVel;
			y+=yVel;
		}

		private void grow() { //this method would get refined
			age++;
		}

		private void reproduce() {
			age++;
			recovery++;
			double currentHunger = health/maxHealth;
				if(		
					age>=rep_MinTimer && //you are old enough to reproduce
					age<=rep_MaxTimer &&  // you aren't too old to get it on
					currentHunger>=rep_HungerMin && //you are not too full to reproduce
					currentHunger<=rep_HungerMax && //you are not too hungry to reproduce
					recovery>=rep_Recovery && // you are recovered from any last pregnant
					foodEaten>=minFoodEatenForRep
					){
						health-=preg_cost*fertility;
						recovery = 0;
						Shrimp[] children = new Shrimp[(int)fertility];
						for(int i =0; i<(int)fertility;i++){
							Shrimp shrimp = new Shrimp(
									x,
									y,
									Math.random()*360,
									growth,
									metabolism,
									food_speed,
									acceleration,
									dexterity,
									fertility,
									sight,
									initial_size,
									hunt_speed,
									running_burn,
									care,
									digestiveSystem,
									deathSpread,
									deathAmount,
									mutate(deathMaxNutrition,deathMinNutrition,254),
									mutate(deathMinNutrition,1,deathMaxNutrition),
									rep_MinTimer,
									rep_MaxTimer,
									rep_HungerMin,
									rep_HungerMax,
									rep_Recovery,
									preg_cost,
									birth_spread,
									maxHealth,
									init_healthBar,
									skin_red,
									skin_green,
									skin_blue,
									birth_spread,
									minFoodEatenForRep,
									leftSightAngle,
									rightSightAngle,
									sightBurn,
									sizeBurn,
									bodyX,
									bodyY,
									foodSeeks
									);
							children[i]= shrimp;
							Game.shrimps.add(shrimp);
						}
						Tree.haveChildren(shrimpID, children);
					}
			
			
		}

		private void performDeath() {
			deathTimer--;
			if(deathTimer<0){
				//explode
				Game.shrimps.remove(this);
				for(int i=0;i<(int)deathAmount;i++){
					int nutrition = (int)((Math.random()*(deathMaxNutrition-deathMinNutrition))+deathMinNutrition);
					Food food = new Food((int)x,(int)y,nutrition);
					double angle= ((Math.random()*360-90)*Math.PI/180);
					food.xVel = (Math.cos(angle)*deathSpread);
					food.yVel = (Math.sin(angle)*deathSpread);
					Game.food.add(food);
				}
			}
		}
		private void seekFood() {
			Food food = getNearestFood(); 
			if(food==null){
				hunt();
			}else{
				//food.flag=true;
				engageFood(food);
			}
			eatFood(); 
		}

		private void eatFood() {
			double[] coords = {x,y};
			try {
				Food nearestFood = Food.kd.nearest(coords);
				double distFromFood = Math.sqrt(Math.pow((x-nearestFood.x),2)+Math.pow((y-nearestFood.y), 2));
				if(distFromFood<width/2){
					health+=nearestFood.nutrition*digestiveSystem;
					foodEaten++;
					if(health>maxHealth){
						health = maxHealth;
					}
					double [] A = {nearestFood.x, nearestFood.y};
				
					Food.rott++;
					if(Food.rott%150==0){
						Food.kd.delete(A,false);
						//F.trace(Food.rott);
					}else{
						Food.kd.delete(A,true);
					}
					
					Game.food.remove(nearestFood);
				}
			} catch (KeySizeException e1) {
				e1.printStackTrace();
			} catch (KeyMissingException e) {
				e.printStackTrace();
			}
		}

		private void engageFood(Food food) {
			//food.flag=true;
			double fex=(food.x-x);
			double fi=(food.y-y);
			double t_angle = Math.atan(fi/fex)/(Math.PI/180);
			double destin = t_angle;
			if(fex>0 && fi<0){
				destin+=360;
			}else if(fex<0){
				destin+=180;
			}
			
			double rep=rotation-90;
			if(rep<0){
				rep+=360;
			}
			
			if(destin+(360-rep)<180){
				destin+=360;
			}
			if(rep+(360-destin)<180){
				destin-=360;
			}
			
			rotation+=(destin-rep)/120;
			//debugString = "actual:"+(int)destin+" current:"+(int)rep;
			//rotation+=demand;
			
			if(speed<food_speed){
				speed+=acceleration;
			}
			health-=speed*running_burn;
		}

		private Food getNearestFood() {
			int closestDist = Integer.MAX_VALUE;
			int indexOfClosest = 0;
			double[] coords = {x,y};
			try {
				List<Food> foods = Food.kd.nearest(coords, seek);
				//System.out.println(foods);
			for(int i =0; i<foods.size();i++){
				Food food = foods.get(i);
				
				double distFromFood = Math.sqrt(Math.pow((x-food.x),2)+Math.pow((y-food.y), 2));
				double fex=(food.x-x);
				double fi=(food.y-y);
				double t_angle = Math.atan(fi/fex)/(Math.PI/180);
				double destin = t_angle;
				if(fex>0 && fi<0){
					destin+=360;
				}else if(fex<0){
					destin+=180;
				}
				destin-=rotation;
				if(destin<0){
					destin+=360;
				}
				boolean leftBound = destin>leftSightAngle;
				boolean rightBound = destin<rightSightAngle;
				if(distFromFood<closestDist && leftBound && rightBound){
					closestDist = (int) distFromFood;
					indexOfClosest = Game.food.indexOf(food);
				}
			}
			
			Food food = Game.food.get(indexOfClosest);
			if(closestDist>sight/2){
				return null;
			}else{
				return food;
			}
			
			
			
			} catch (KeySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private void hunt() {
			if(speed<hunt_speed){
				speed+=acceleration;
			}
			health-=speed*running_burn;
		}
	}
