package coffeecatteam.bubble_burst.program.layouts;

import java.awt.Color;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.Text;

import coffeecatteam.bubble_burst.Reference;

public class LayoutInstructions extends Layout {

	private Application application;

	private Button buttonBack;
	private Label labelVersion;
	
	private Text textInfo;
	private Label levelInfo;

	public LayoutInstructions(int width, int height, Application application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.components.clear();

		this.buttonBack = new Button(3, 3, "Back", Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.labelVersion = new Label("Version: "+Reference.VERSION, 55, 8);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		this.textInfo = new Text("", 3, 25, 145);
		this.textInfo.setTextColour(Color.DARK_GRAY);
		this.textInfo.setText("Game Info:"
				+ "\nThe goal of this game is to \"burst\" the hydrogen bubbles and get the highest score you can."
				+ "\n\nControls:" + "\n- Click and drag/hold to move the \"fire stick\"");
		super.addComponent(this.textInfo);

		this.levelInfo = new Label("", 147, 5);
		this.levelInfo.setTextColour(Colors.LIGHT_GRAY);
		this.levelInfo.setText("Level: 1 | Score < 500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 15);
		this.levelInfo.setTextColour(Colors.GREEN);
		this.levelInfo.setText("Level: 2 | Score > 500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 25);
		this.levelInfo.setTextColour(Colors.YELLOW);
		this.levelInfo.setText("Level: 3 | Score > 1000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 35);
		this.levelInfo.setTextColour(Colors.ORANGE);
		this.levelInfo.setText("Level: 4 | Score > 1500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 45);
		this.levelInfo.setTextColour(Colors.RED);
		this.levelInfo.setText("Level: 5 | Score > 2000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 55);
		this.levelInfo.setTextColour(Colors.DARK_RED);
		this.levelInfo.setText("Level: 6 | Score > 2500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 65);
		this.levelInfo.setTextColour(Colors.MAGENTA);
		this.levelInfo.setText("Level: 7 | Score > 5000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 75);
		this.levelInfo.setTextColour(Colors.PURPLE);
		this.levelInfo.setText("Level: 8 | Score > 10000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 85);
		this.levelInfo.setTextColour(Colors.CYAN);
		this.levelInfo.setText("Level: 9 | Score > 15000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147, 95);
		this.levelInfo.setTextColour(Colors.DARK_BLUE);
		this.levelInfo.setText("Level: 10 | Score > 20000");
		super.addComponent(this.levelInfo);
		
		super.init();
	}
}
