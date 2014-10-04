import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Graph {
	int x = 5;
	int y = 25;
	int width = 500;
	int height = 200;
	int padding = 6;
	int shiftX = 0;
	int shiftY = 0;
	
	
	Rectangle display;
	String title ="Graph Title";
	ArrayList<GraphLine> lines = new ArrayList<GraphLine>();
	int startFrom = 0;
	public boolean visible;
	public boolean clickedOn=false;
	public void draw(Graphics stage) {
		if(visible){
			if(clickedOn){
			shiftX+=Cursor.dragX;
			shiftY+=Cursor.dragY;
			}
			
			stage.setColor(new Color(154,154,154,200));
			stage.fillRect(x+shiftX,y+shiftY-18,100,18);
			stage.fillRect(x+shiftX, y+shiftY, width, height);
			//stage.setColor(new Color(205,205,205,200));
			stage.setColor(Color.BLACK);
			stage.fillRect(x+shiftX+padding,y+shiftY+padding,width-padding*2,height-padding*2);
			display = new Rectangle(x+shiftX+padding,y+shiftY+padding,width-padding*2,height-padding*2);
			stage.setColor(new Color(50,50,50));
			stage.setFont(new Font("Arial", Font.PLAIN, 14));
			stage.drawString(title,shiftX+x+padding,y+shiftY);
			for(int i=0;i<lines.size();i++){
				lines.get(i).draw(stage,x+shiftX+padding,y+shiftY+height-padding-1,startFrom);
			}
			
			if(clickedOn){
			shiftX-=Cursor.dragX;
			shiftY-=Cursor.dragY;
			}
		}
	}
	public boolean grab(int mouseX, int mouseY) {
		return F.hitTest(x+shiftX, y+shiftY-18, 100, 18, mouseX, mouseY);
	}

}
