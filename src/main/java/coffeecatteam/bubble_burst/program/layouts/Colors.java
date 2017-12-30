package coffeecatteam.bubble_burst.program.layouts;

import java.awt.Color;

public class Colors {

	public static final Color LIGHT_GRAY = new Color(130, 130, 130); 	// Level: 1 | score < 500
	public static final Color GREEN = new Color(0, 255, 0); 			// Level: 2 | score > 500
	public static final Color YELLOW = new Color(255, 255, 0); 			// Level: 3 | score > 1000
	public static final Color ORANGE = new Color(255, 190, 0); 			// Level: 4 | score > 1500
	public static final Color RED = new Color(255, 0, 0); 				// Level: 5 | score > 2000
	public static final Color DARK_RED = new Color(127, 0, 0); 			// Level: 6 | score > 2500
	public static final Color MAGENTA = new Color(255, 0, 220); 		// Level: 7 | score > 5000
	public static final Color PURPLE = new Color(178, 0, 255); 			// Level: 8 | score > 10000
	public static final Color CYAN = new Color(0, 148, 255); 			// Level: 9 | score > 15000
	public static final Color DARK_BLUE = new Color(0, 38, 255); 		// Level: 10 | score > 20000
}
