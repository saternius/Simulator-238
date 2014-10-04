import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Cursor implements MouseMotionListener, MouseListener, MouseWheelListener {
	public static int x;
	public static int y;
	public static int dragX;
	public static int dragY;
	public static boolean hold;
	private Graph focus;
	public Cursor(){
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		
	}

	@Override
	public void mouseEntered(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		x=m.getX();
		y=m.getY();
		hold = true;
		focus=null;
		for(int i=0; i<Game.graphs.size();i++){
			if(Game.graphs.get(i).visible && Game.graphs.get(i).grab(x,y)){
				focus = Game.graphs.get(i);
			}
		}
		if(focus==null){
			Tree.clickedOn=true;
		}else{
			focus.clickedOn = true;
		}
		 if (m.getModifiers() == 4) {
			 Shrimp focusedShrimp = null;
			 for(int i=0;i<Game.shrimps.size();i++){ //you could use the KDTree approach here as well, but unnecessary at this point
				 Shrimp shrimp = Game.shrimps.get(i);
				 shrimp.glow=false;
				 if(F.dist(x, y, shrimp.x, shrimp.y)<20){
					focusedShrimp = shrimp;
				 }
			 }
			 if(focusedShrimp!=null){
				 Game.focus(focusedShrimp);
			 }else{
				 Game.shrimpData = null;
			 }
		 }
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		hold = false;
		if(Tree.dragEnabled && Tree.clickedOn){
			Tree.shiftX+=dragX;
			Tree.shiftY+=dragY;
			dragX=0;
			dragY=0;
		}
		if(focus!=null){
			focus.shiftX+=dragX;
			focus.shiftY+=dragY;
			dragX=0;
			dragY=0;
		}
		for(int i=0;i<Game.graphs.size();i++){
			Game.graphs.get(i).clickedOn=false;
		}
		Tree.clickedOn=false;
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		dragX = m.getX()-x;
		dragY = m.getY()-y;
	}

	@Override
	public void mouseMoved(MouseEvent m) {
	}

}
