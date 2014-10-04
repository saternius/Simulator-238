import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.Timer;

/*TODO:
-> make the terminal
-> make line graphs on genetic variation over time
-> make dominant org line graph
-> implement poison
*/

// OK this the class where we will draw everything on the screen and take actions
// This is the grand mother of all classes as it manages what goes on where
@SuppressWarnings("serial")
public class Game extends Canvas{
	//debug options
	public static boolean showFoodSpawnRate = true;
	public static final double FRICTION = .95;//Physics constant for friction
	public static int width = 800;// The width of the screen
	public static int height = 600;// The height of the screen
	static boolean repaintInProgress = false;// checks if painting is going on
	public static ArrayList<Food> food = new ArrayList<Food>();
	public static ArrayList<Shrimp>shrimps = new ArrayList<Shrimp>();
	public static int spawnFoodRate = 5; //clicks until new food respawns 
	public static int numFoodSpawn = 1;//number of food that spawns every throw.
	public static int clock = 0;//game clock beats 1 per 60ms
	public static boolean seasons = true;//Do we have seasons or not
	public static int seasonFreq = 400;//How frequent the seasons come and go. A season is just a sin function that spawns food at a fluctuating rate.								   
	public static int seasonIntensity = 4;//How intense are the seasons varying
	public static int seasonBase = 3;//How fast the food spawns at the peak of a good season.
	public static ShrimpData shrimpData;
	public static int CLICKS_DELAY = 12;
	public static ArrayList<Graph> graphs = new ArrayList<Graph>();
	public static ArrayList<Integer> processTime = new ArrayList<Integer>();
	//public static int infoRate = 10;
	Game() { // creates the gameCanvas
		setIgnoreRepaint(true); // Ignore this, established game engine
		Chrono chrono = new Chrono(this);
		new Timer(CLICKS_DELAY, chrono).start(); //50 fps
		Cursor cursor = new Cursor();
		this.addMouseListener(cursor);// adds all the eventListener
		this.addMouseMotionListener(cursor);
		this.addMouseWheelListener(cursor);
		Keyboard keyboard = new Keyboard();
		this.addKeyListener(keyboard);
		generateFoodRandomly(500);
		generateShrimp(35);
		Tree.initialize();
		
		graphs.add(new DominanceGraph());
		
	}

	private static void generateShrimp(int i) {
		for(int l=1;l<=i;l++){
			Shrimp s = new Shrimp();
			shrimps.add(s);
		}
	}

	private static void generateFoodRandomly(int i) {
		for(int f=1; f<=i;f++){
			int nutrition = (int) (Math.random()*(Food.upperBoundDefaultNut-Food.lowerBoundDefaultNut)+Food.lowerBoundDefaultNut);
			int foodX = (int)(Math.random()*width);
			int foodY = (int)(Math.random()*height);
			Food grub = new Food(foodX,foodY,nutrition);
			food.add(grub);
			double [] A = {foodX, foodY};
			try {
			    Food.kd.insert(A, grub);
			}
			catch (Exception e) {
			    //System.err.println(e);
			}
		}
	}

	public void myRepaint() { // Repaints
		long startTime = System.currentTimeMillis();
		if (repaintInProgress)
			return;
		repaintInProgress = true;
		// ok doing the repaint on the not showed page
		BufferStrategy strategy = getBufferStrategy();// Prevents flickering
		Graphics stage = strategy.getDrawGraphics();
		
		
		
		onEvent();
		//DRAW STUFF HERE
		stage.setColor(Color.BLACK);
		stage.fillRect(0, 0, width, height);
		for(int i =0; i<food.size();i++){
			food.get(i).draw((Graphics2D)stage);
		}
		for(int i =0; i<shrimps.size();i++){
			shrimps.get(i).draw((Graphics2D)stage);
		}
		
		if(Tree.graph == Tree.TreeType.LIVING){
			Tree.drawLiving(stage);
		}else if(Tree.graph == Tree.TreeType.TREE){
			Tree.drawTree(stage);
		}
		
		if(shrimpData!=null){
			shrimpData.draw((Graphics2D)stage);
		}
		
		if(showFoodSpawnRate){
			stage.setColor(Color.WHITE);
			stage.setFont(new Font("Arial", Font.PLAIN, 18));
			stage.drawString("BranchMap Size: "+Tree.branchMap.size(),0,520);
			stage.drawString("Food Spawn Rate: "+spawnFoodRate,0,560);
			stage.drawString("KDtree Size:"+Food.kd.size(), 0, 540);
		}
		
		for(int i=0; i<graphs.size();i++){
			graphs.get(i).draw(stage);
		}
		
		stage.setColor(Color.WHITE);
		long finishTime = System.currentTimeMillis();
		//if(processTime.size()>100){
		//	processTime.clear();
		//}
		//processTime.add((int) (finishTime-startTime));
		stage.drawString("Time Taken: "+((finishTime)-(startTime)), 0, 410);
		//stage.drawString("Time Taken: "+((finishTime)+" "+(startTime)), 0, 430);
		//int total = 0;
		//for(int i=0; i<processTime.size();i++){
		//	total+=processTime.get(i);
		//}
		//stage.drawString("Average: "+total/processTime.size(),0,430);
		stage.drawString("Clock: "+clock,0,460);
		Shrimp.seek=Food.kd.size()/50;
		if(Shrimp.seek>Shrimp.seekMaxima){
			Shrimp.seek = Shrimp.seekMaxima;
		}else if(Shrimp.seek<Shrimp.seekMinima){
			Shrimp.seek = Shrimp.seekMinima;
		}
		stage.drawString("SEEK: "+Shrimp.seek,0,490);
	    Runtime runtime = Runtime.getRuntime();  
	    long maxMemory = runtime.maxMemory();  
	    long allocatedMemory = runtime.totalMemory();  
	    long freeMemory = runtime.freeMemory();  
	      
	    stage.drawString("free memory: " + freeMemory / 1024,0,280);  
	    stage.drawString("used memory: " + ((allocatedMemory / 1024)-(freeMemory / 1024)),0,300); 
	    stage.drawString("allocated memory: " + allocatedMemory / 1024,0,320);  
	    stage.drawString("max memory: " + maxMemory /1024,0,340);  
	    stage.drawString("total free memory: " +   
	       (freeMemory + (maxMemory - allocatedMemory)) / 1024,0,360);   
		
	    
	    stage.drawString("shrimpArraySize: " + shrimps.size(),0,240); 
	    stage.drawString("foodArraySize: " + food.size(),0,260);  
	   // stage.drawString("used memory: " + ((allocatedMemory / 1024)-(freeMemory / 1024)),0,300); 
	   // stage.drawString("allocated memory: " + allocatedMemory / 1024,0,320);  
	   // stage.drawString("max memory: " + maxMemory /1024,0,340);  
	    
		if (stage != null)
			stage.dispose();
		// show next buffer
		strategy.show();
		// synchronized the blitter page shown
		Toolkit.getDefaultToolkit().sync();
		repaintInProgress = false;
		
	}

	private void onEvent() {
		clock++;
		if(seasons){
		spawnFoodRate = seasonBase+seasonIntensity+(int)(Math.cos(clock/(seasonFreq*1.0))*seasonIntensity);
		}
		if(clock%spawnFoodRate==0){
			generateFoodRandomly(numFoodSpawn);
		}
	}

	public static void focus(Shrimp shrimp) {
		shrimp.glow = true;
		shrimpData = new ShrimpData(shrimp);
	}
}
