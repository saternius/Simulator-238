import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;


public class Functions {
	public Functions(){
	}
	public void trace(String s){
		System.out.println(s);
	}
	public boolean hitTest(Rectangle rect,int x, int y) {
		return x>rect.x && x<rect.x+rect.width && y>rect.y && y<rect.y+rect.height;
	}
	public void fillRect(Rectangle rect, Graphics stage) {
		stage.fillRect(rect.x,rect.y,rect.width,rect.height);
	}
	public static Image loadImage(String url){
		System.out.println("loading image:" +url);		
		try{
		  BufferedImage img= ImageIO.read(new URL(url));
		  return img;
		}catch(Exception e){
			System.out.println("Yo image failed brah");
			return null;
		}

	}
	public static Font loadFont(String url){
		System.out.println("loading image:" +url);		
		try{
			 URL fontUrl = new URL(url);
			 Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
			 return font;
		}catch(Exception e){
			System.out.println("Yo font failed brah");
			return null;
		}

	}
	public String editString(KeyEvent k, String story, int max_length, Font font, String clearString, int StartBound, int EndBound,int max, boolean spaces){
		
		if(story.equals(clearString)){
			story = "";
		}
		if (k.getKeyCode() == 8 && story.length() > 0) {
            story = story.substring(0,
                    story.length() - 1);
        } else {
        	@SuppressWarnings("deprecation")
			FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
			int w = fm.stringWidth(story+k.getKeyChar());
            if (((k.getKeyChar() >= StartBound && k.getKeyChar() <= EndBound))
                    && w < max_length && story.length()<max && k.getKeyChar()!=' ') {
                story += k.getKeyChar();
            }
            if(k.getKeyChar() == ' ' && spaces){
            	story+="_";
            }
            
        }
		if(story.equals("")){
			return clearString;
		}
		return story;
	}
}
