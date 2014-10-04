import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class GraphLine {
	public ArrayList<Integer> xCoords;
	public ArrayList<Integer>yCoords;
	public Color color;
	public GraphLine(Color color) {
		this.color = color;
		xCoords = new ArrayList<Integer>();
		yCoords = new ArrayList<Integer>();
	}
	public void draw(Graphics stage, int x, int y, int push) {
		stage.setColor(color);
		for(int i=0; i<xCoords.size()-1;i++){
			if(xCoords.get(i)>push){
				stage.drawLine(xCoords.get(i)+x-push, yCoords.get(i)+y, xCoords.get(i+1)+x-push, yCoords.get(i+1)+y);
			}
		}
	}
}
