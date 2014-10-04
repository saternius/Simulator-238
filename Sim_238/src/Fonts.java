import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Fonts {
	public static Font regFont;
	public static Font boldFont;
	public Fonts() {
		try {
			regFont = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("Fonts//Biko_Regular.ttf"));
			regFont = regFont.deriveFont(Font.PLAIN, 13f);

			boldFont = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("Fonts//Biko_Bold.ttf"));
			boldFont = boldFont.deriveFont(Font.PLAIN, 13f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setSize(int i) {
		regFont = regFont.deriveFont(Font.PLAIN, i);
		boldFont = boldFont.deriveFont(Font.PLAIN, i);
	}
}
