package coffeecatteam.bubbleburst.utill;

import java.awt.Color;

public class Utills {

	public enum Colors {
		LIGHT_GRAY(150, 150, 150), 	// Level: 1 | score < 500
		GREEN(0, 255, 0), 			// Level: 2 | score > 500
		YELLOW(255, 255, 0), 		// Level: 3 | score > 1000
		ORANGE(255, 190, 0), 		// Level: 4 | score > 1500
		RED(255, 0, 0), 			// Level: 5 | score > 2000
		DARK_RED(127, 0, 0), 		// Level: 6 | score > 2500
		MAGENTA(255, 0, 220), 		// Level: 7 | score > 5000
		PURPLE(178, 0, 255), 		// Level: 8 | score > 10000
		CYAN(0, 148, 255), 			// Level: 9 | score > 15000
		DARK_BLUE(0, 38, 255); 		// Level: 10 | score > 20000
		
		int r;
		int g;
		int b;
		
		Colors(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
		
		public Color getColor() {
			return new Color(r, g, b);
		}
	}
	
	public static double getTime() {
		return (double) System.nanoTime() / (double) 1000000000L;
	}
}
