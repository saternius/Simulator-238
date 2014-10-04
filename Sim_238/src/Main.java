import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {
	private static final long serialVersionUID = -2934268077288894946L;
	Main() {
		super("Simulator 238");
		Game canvas = new Game();
		add(canvas, BorderLayout.CENTER);
		setSize(800, 600);
		setVisible(true);		
		canvas.createBufferStrategy(2);
	}
	// just to start the application
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Initializing..");
				new Main();
			}
		});
	}
}
