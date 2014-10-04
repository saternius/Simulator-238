import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener{

	@Override
	public void keyPressed(KeyEvent k) {//Key is Pressed
		if(k.getKeyChar()==' '){
			Game.repaintInProgress=!Game.repaintInProgress;
		}
		if(k.getKeyChar()=='c'){
			
		}
		if(k.getKeyChar()=='f'){
			Game.showFoodSpawnRate = !Game.showFoodSpawnRate;
		}
		if(k.getKeyChar()=='s'){
			Shrimp.drawSight=!Shrimp.drawSight;
		}
		if(k.getKeyChar()=='d'){
			F.trace(Tree.graph.getNext().toString());
			Tree.graph = Tree.graph.getNext();
		}
		//Make a Terminal for this shit, because I and nobody else wanna memorize
		F.trace(k.getKeyCode());
		if(k.isControlDown() && k.getKeyCode() == 38){
			Tree.lineWidth++;
		}
		if(k.isControlDown() &&  k.getKeyCode() == 40){
			Tree.lineWidth--;
		}
		if(k.isAltDown() && k.getKeyCode() == 38){
			Tree.durationWait++;
		}
		if(k.isAltDown() &&  k.getKeyCode() == 40){
			Tree.durationWait--;
		}
		if(k.getKeyCode()==49){
			Game.graphs.get(0).visible = !Game.graphs.get(0).visible;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
