import java.awt.Color;
import java.awt.Graphics;


public class DominanceGraph extends Graph{
	int timeSpeed = 10;
	private int heightPerDecendant=1;
	static int time = 0;
	int spacing = 1;
	public DominanceGraph(){
		super();
		title = "Dominance";
		for(int i=0;i<Game.shrimps.size();i++){
			Shrimp shrimp = Game.shrimps.get(i);
			Color color = new Color(shrimp.skin_red,shrimp.skin_green,shrimp.skin_blue);
			lines.add(new GraphLine(color));
		}
	}
	public void draw(Graphics stage){
		super.draw(stage);
		if(Game.clock%timeSpeed==0 && Game.clock<Tree.refreshRate){
			for(int i=0; i<lines.size();i++){
				lines.get(i).xCoords.add(time);
				lines.get(i).yCoords.add(Tree.branchMap.get(i).numLivingDecendants*heightPerDecendant*-1);
			}
			if((lines.get(0).xCoords.size()-startFrom)*spacing>width-padding*2){
				startFrom+=spacing;
			}
			time+=spacing;
		}
	}
}
