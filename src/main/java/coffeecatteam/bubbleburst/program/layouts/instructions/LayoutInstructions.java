package coffeecatteam.bubbleburst.program.layouts.instructions;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.Text;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.program.ApplicationGame;
import coffeecatteam.bubbleburst.program.component.Sprite;
import coffeecatteam.bubbleburst.program.layouts.Colors;
import net.minecraft.util.ResourceLocation;

public class LayoutInstructions extends Layout {

	private ApplicationGame application;

	private Button buttonBack;
	private Label labelVersion;
	private Sprite bomb;

	private Text textInfo;

	private Button buttonLevelsScore;
	private InstructionsLevelsScore layoutLevelsScore;

	private static long time;

	public LayoutInstructions(int width, int height, ApplicationGame application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.components.clear();
		time = new Date().getTime();
		this.layoutLevelsScore = new InstructionsLevelsScore(237, 115, this.application);

		this.buttonBack = new Button(3, 3, "Back", Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.labelVersion = new Label("Version: " + Reference.VERSION, 3, 50);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		this.bomb = new Sprite(50, 65, new ResourceLocation(Reference.MODID, "textures/sprites/bomb.png"));
		this.bomb.setScale(1.5f);
		super.addComponent(this.bomb);

		this.textInfo = new Text("", 115, 3, 145);
		this.textInfo.setTextColour(Color.DARK_GRAY);
		this.textInfo.setText("Game Info:"
				+ "\nThe goal of this game is to \"burst\" the hydrogen bubbles and get the highest score you can."
				+ "\n\nControls:"
				+ "\n- Click and drag/hold to move the \"fire stick\"");
		super.addComponent(this.textInfo);

		this.buttonLevelsScore = new Button(3, 25, "Levels & Scoring", Icons.INFO);
		this.buttonLevelsScore.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.setLayout(this.layoutLevelsScore);
			}
		});
		super.addComponent(this.buttonLevelsScore);

		super.init();
	}

	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			long currentTime = new Date().getTime();
			long newTime = currentTime - time;
			
			long minTime = 1000*1;
			long maxTime = 1000*3;
			//System.out.println(new SimpleDateFormat("mm:ss:SSS").format(new Date(newTime)) + " | " + newTime + " | " + minTime + " | " + maxTime);
			
			if (newTime >= minTime && newTime <= maxTime) {
				this.bomb.setSprite(new ResourceLocation(Reference.MODID, "textures/sprites/explosion.png"));
			} else {
				this.bomb.setSprite(new ResourceLocation(Reference.MODID, "textures/sprites/bomb.png"));
			}
			
			if (newTime > maxTime) {
				time = new Date().getTime();
			}
		}
	}
}
