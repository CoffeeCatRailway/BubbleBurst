package coffeecatteam.bubbleburst.program.layouts.instructions;

import java.awt.Color;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.Text;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.program.ApplicationGame;
import coffeecatteam.bubbleburst.program.layouts.Colors;

public class InstructionsLevelsScore extends Layout {

	private ApplicationGame application;

	private Button buttonBack;
	private Label labelVersion;

	private Text textInfo;
	private Label levelInfo;

	public InstructionsLevelsScore(int width, int height, ApplicationGame application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.components.clear();

		this.buttonBack = new Button(3, 3, "Back", Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.setLayout(this.application.getLayoutInstructions());
			}
		});
		super.addComponent(this.buttonBack);

		this.labelVersion = new Label("Version: " + Reference.VERSION, 3, 25);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		this.textInfo = new Text("", 3, 35, 99);
		this.textInfo.setTextColour(Color.DARK_GRAY);
		this.textInfo.setText("Scoring Info:"
				+ "\nBubbles | Score + 1"
				+ "\nBombs   | Score - 2");
		super.addComponent(this.textInfo);

		int liOffset = 40;
		this.levelInfo = new Label("", 147-liOffset, 5);
		this.levelInfo.setTextColour(Colors.LIGHT_GRAY);
		this.levelInfo.setText("Level: 1 | Score < 500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 15);
		this.levelInfo.setTextColour(Colors.GREEN);
		this.levelInfo.setText("Level: 2 | Score > 500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 25);
		this.levelInfo.setTextColour(Colors.YELLOW);
		this.levelInfo.setText("Level: 3 | Score > 1000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 35);
		this.levelInfo.setTextColour(Colors.ORANGE);
		this.levelInfo.setText("Level: 4 | Score > 1500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 45);
		this.levelInfo.setTextColour(Colors.RED);
		this.levelInfo.setText("Level: 5 | Score > 2000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 55);
		this.levelInfo.setTextColour(Colors.DARK_RED);
		this.levelInfo.setText("Level: 6 | Score > 2500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 65);
		this.levelInfo.setTextColour(Colors.MAGENTA);
		this.levelInfo.setText("Level: 7 | Score > 5000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 75);
		this.levelInfo.setTextColour(Colors.PURPLE);
		this.levelInfo.setText("Level: 8 | Score > 10000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 85);
		this.levelInfo.setTextColour(Colors.CYAN);
		this.levelInfo.setText("Level: 9 | Score > 15000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147-liOffset, 95);
		this.levelInfo.setTextColour(Colors.DARK_BLUE);
		this.levelInfo.setText("Level: 10 | Score > 20000");
		super.addComponent(this.levelInfo);

		super.init();
	}
}
