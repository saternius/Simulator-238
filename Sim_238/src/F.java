import java.util.ArrayList;


public class F {
	public static void trace(String str){
		System.out.println(str);
	}
	public static void trace(int i){
		System.out.println(i);
	}
	public static void trace(double dubs){
		System.out.println(dubs);
	}
	public static void trace(ArrayList<Integer> aliveShrimp) {
		System.out.println(aliveShrimp);
	}
	public static double dist(int x1, int y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1), 2));
	}
	
	public static boolean hitTest(int x, int y, int width, int height, float mouseX, float mouseY){
		 return mouseX>x && mouseX<x+width && mouseY>y && mouseY<y+height;
	 }

}
