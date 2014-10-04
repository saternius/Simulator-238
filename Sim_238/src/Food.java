import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import edu.wlu.cs.levy.CG.KDTree;
import edu.wlu.cs.levy.CG.KeyMissingException;
import edu.wlu.cs.levy.CG.KeySizeException;



public class Food {
	public static KDTree<Food> kd = new KDTree<Food>(2);
	
	
	public static int lowerBoundDefaultNut = 50;
	public static int upperBoundDefaultNut = 200;
	public double x;
	public double y;
	public double xVel=0;
	public double yVel=0;
	public int nutrition;
	public int rotate;
	public static int width = 6;
	public static int height = 6;
	public boolean flag = false;
	public static int spoilSpeed = 40;
	public int spoil;
	String debugString ="test";
	static int rott =0;
	public Food(int x, int y, int nutrition) {
		this.x = x;
		this.y = y;
		this.nutrition = nutrition;
		rotate = (int) (Math.random()*360);
	}
	
	public void draw(Graphics2D stage){
			spoil--;
			if(spoil<0){
				spoil = spoilSpeed;
				nutrition--;
			}
			if(nutrition<15){
				double [] A = {x, y};
			
					try {
						
						rott++;
						if(rott%150==0){
							Food.kd.delete(A,false);
							F.trace(rott);
						}else{
							Food.kd.delete(A,true);
						}
					} catch (KeySizeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyMissingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				Game.food.remove(this);
			}
			xVel*=Game.FRICTION;
			yVel*=Game.FRICTION;
			x+=xVel;
			y+=yVel;
	        AffineTransform old = stage.getTransform();
	        stage.translate(x, y);
	        stage.rotate(Math.toRadians(rotate));
	        if(!flag){
	        	stage.setColor(new Color(255,255,nutrition));
	        }else{
	        	stage.setColor(new Color(255,0,0));
	        }
	        stage.fillRect(-width/2, -height/2, width/2, height/2);
	        //draw shape/image (will be rotated)
	        stage.setTransform(old);
	        
	        if(flag){
	        	
	        }
	}

}
