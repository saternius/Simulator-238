import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Tree {
	static enum TreeType{NONE,LIVING,TREE;
	public TreeType getNext() {
	     return this.ordinal() < TreeType.values().length - 1
	         ? TreeType.values()[this.ordinal() + 1]
	         : NONE;
	   }
	};
	static TreeType graph = TreeType.TREE;
	static Branch[] branches;// An array of branches from the tree of life
	static int centerX = Game.width/2;
	static int lineHeight = 30;
	static int lineWidth = 2;
	static int durationWait = 8;
	static Map<Integer,Branch> branchMap = new HashMap<Integer, Branch>();
	static ArrayList<Integer> aliveShrimp = new ArrayList<Integer>();
	static boolean dragEnabled = true;
	static int shiftX = 0;
	static int shiftY = 0;
	static int TreeXPos = 0;
	static boolean discardDead =  true;
	static int aliveBranches = 0;
	static int ALIVE_BRANCHES = 0;
	static boolean clickedOn = false;
	static int refreshRate = 20000;
	private static int nextGenYShift=0;
	private static int lastClear = 0;
	static void initialize(){
		int num_branches = Game.shrimps.size();
		branches = new Branch[num_branches];
		for(int i =0; i<num_branches; i++){
			Shrimp shrimp = Game.shrimps.get(i);
			branches[i]= new Branch();
			branches[i].ID = i;
			branches[i].color = new Color(shrimp.skin_red,shrimp.skin_green,shrimp.skin_blue);
			branchMap.put(i, branches[i]);
			aliveShrimp.add(i);
		}
		
	}
	public static void drawLiving(Graphics stage) {
		update();
		/*
		for(int i=0; i<branchMap.size();i++){
			Branch branch = branchMap.get(i);
			stage.setColor(branch.color);
			int lineX = centerX-(i*lineWidth)+(branchMap.size()/2)*lineWidth+shiftX;
			if(dragEnabled){
				lineX+=Cursor.dragX;
			}
			stage.drawLine(lineX,Game.height-40,lineX,Game.height-branch.duration-40);
		}*/
	}
	public static void drawTree(Graphics stage) {
		ALIVE_BRANCHES = aliveBranches;
		aliveBranches = 0;
		update();
		TreeXPos=0;
		//F.trace("branchLength ="+branches.length);
		//F.trace("aliveShrimp = "+aliveShrimp);
		//F.trace("branchMap = "+branchMap);
		for(int i=0; i<branches.length;i++){
			int ID = aliveShrimp.get(i);
			//F.trace("ID = "+ID);
			if(discardDead){
				if(branches[i].aliveByProxy){
					drawBranchLine(stage,ID,centerX);
					TreeXPos++;
				}
			}else{
				drawBranchLine(stage,ID,centerX);
				TreeXPos++;
			}
		}
	}
	private static void drawBranchLine(Graphics stage, int ID,int parentX) {
		aliveBranches++;
		Branch branch = branchMap.get(ID);
		stage.setColor(branch.color);
		int numLines = branchMap.size();
		if(discardDead){
			numLines = ALIVE_BRANCHES;
			//F.trace(ALIVE_BRANCHES);
		}
		int lineX = centerX-(TreeXPos*lineWidth)+(numLines/2)*lineWidth+shiftX;
		int yStart = Game.height-(branch.start/durationWait)-40+shiftY+nextGenYShift;
		if(dragEnabled && clickedOn){
			lineX+=Cursor.dragX;
			yStart+=Cursor.dragY;
		}
		
		//draw the Merge
		stage.drawLine(parentX,yStart,lineX,yStart);
		//draw the Line
		stage.drawLine(lineX,yStart,lineX,yStart-branch.duration);
		//drawYourChildrenLine
		for(int c=0; c<branch.children.size();c++){
			if(discardDead){
				if(branch.aliveByProxy){
					drawBranchLine(stage,branch.children.get(c).ID,lineX);
					TreeXPos++;
				}
			}else{
				drawBranchLine(stage,branch.children.get(c).ID,lineX);
				TreeXPos++;
			}
		}
	}
	private static void update() {
		
		if(Game.clock%refreshRate==0){
			//throw away dead data.
			ArrayList<Integer> tempArray = new ArrayList<Integer>();
			for(int i=0; i<aliveShrimp.size();i++){
				Branch branch = branchMap.get(aliveShrimp.get(i));
				if(branch.alive){//remove them from the branchMap
					tempArray.add(aliveShrimp.get(i));
					branch.start=Game.clock-20;
				}
			}
			aliveShrimp.clear();
			aliveShrimp = tempArray;
				
			int num_branches = aliveShrimp.size();
			branches = new Branch[num_branches];
			for(int i =0; i<num_branches; i++){
				branches[i]= branchMap.get(aliveShrimp.get(i));
			}
			nextGenYShift+=refreshRate/8;
			branchMapDump();
		}
	
		for(int i=0; i<aliveShrimp.size();i++){
			Branch branch = branchMap.get(aliveShrimp.get(i));
			if(branch.alive){
				branch.duration =(Game.clock-branch.start)/durationWait;
			}
		}
		
	}
	private static void branchMapDump() {
		if(aliveShrimp.get(0)!=null){
			int oldest = aliveShrimp.get(0);
			int currentMapSize = branchMap.size();
			F.trace("oldest = "+oldest);
			for(int i=lastClear; i<oldest;i++){
				branchMap.remove(i);
			}
			int deleted = currentMapSize-branchMap.size();
			F.trace("DELETED: "+deleted);
			F.trace("MAPSIZE = "+branchMap.size());
			lastClear = oldest;
		}else{
			int currentMapSize = branchMap.size();
			branchMap.clear();
			int deleted = currentMapSize-branchMap.size();
			F.trace("DELETED: "+deleted);
			F.trace("MAPSIZE = "+branchMap.size());
		}
	}
	public static void died(int i){
		Branch branch = branchMap.get(i);
		branch.alive = false;
		branch.diedAt = Game.clock;
		branch.duration = (branch.diedAt-branch.start)/durationWait;
		//check if children are alive by proxy. If not then update parent's status of proxyLiving, and recurse
		boolean deadByProxy = true;
		for(int c=0; c<branch.children.size();c++){
			if(branch.children.get(c).aliveByProxy){
				deadByProxy = false;
			}
		}
		if(deadByProxy){//no children aliveByProxy. The oraganism's legacy is dead. Remove them from the tree of life.
			updateAliveProxy(i);
		}
		if(branchMap.get(branch.parentID)!=null){
			updateLivingDecendanceDied(branch.parentID);
		}
	}
	private static void updateLivingDecendanceDied(int i) {
		Branch parent = branchMap.get(i);
		parent.numLivingDecendants--;
		if(branchMap.get(parent.parentID)!=null){
			updateLivingDecendanceDied(parent.parentID);
		}
		
	}
	private static void updateLivingDecendanceBorn(int i, int num) {
		Branch parent = branchMap.get(i);
		parent.numLivingDecendants+=num;
		if(branchMap.get(parent.parentID)!=null){
			updateLivingDecendanceBorn(parent.parentID,num);
		}
		
	}
	
	private static void updateAliveProxy(int i) {
		Branch branch = branchMap.get(i);
		branch.aliveByProxy = false;
		if(branchMap.get(branch.parentID)==null){
			return;
		}
		Branch parent = branchMap.get(branch.parentID);
		boolean deadByProxy = true;
		for(int c=0; c<parent.children.size();c++){
			if(parent.children.get(c).aliveByProxy){
				deadByProxy = false;
			}
		}
		if(deadByProxy){//no children aliveByProxy. The oraganism's legacy is dead. Remove them from the tree of life.
			updateAliveProxy(branch.parentID);
		}
	}
	public static void haveChildren(int shrimpID,Shrimp[] shrimp_children){
		branchMap.get(shrimpID).haveChildren(shrimp_children);
	}
	
	 //Subclass Branch.
	static class Branch {
        protected Color color;
        protected int ID;
        protected int parentID=-1;
        protected int start = 0;
        protected int diedAt = 0;
        protected int duration=0;
        protected boolean alive = true;
        protected boolean aliveByProxy = true;
        protected int numLivingDecendants = 0;
        public ArrayList<Branch> children = new ArrayList<Branch>();
        protected void haveChildren(Shrimp[] shrimp_children){
        	numLivingDecendants+=shrimp_children.length;
        	if(branchMap.get(parentID)!=null){
        		updateLivingDecendanceBorn(parentID,shrimp_children.length);
        	}
        	for(int i=0;i<shrimp_children.length;i++){
        		Branch br = new Branch();
        		br.parentID = ID;
        		br.ID = shrimp_children[i].shrimpID;
        		br.color = new Color(shrimp_children[i].skin_red,shrimp_children[i].skin_green,shrimp_children[i].skin_blue);
        		br.start = Game.clock;
        		children.add(br);
        		branchMap.put(shrimp_children[i].shrimpID, br);
        		aliveShrimp.add(shrimp_children[i].shrimpID);
        	}
        }
    }

	

}
