package coffeecatteam.bubbleburst.app.layouts.instructions;

import java.awt.Color;
import java.util.Date;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.Text;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import coffeecatteam.bubbleburst.utill.Utills.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class LayoutLevelsScore extends LayoutStandard {

	private Button buttonBack;
	private Sprite hydrogen_bubble;

	private Label labelVersion;

	private Text textInfo;
	private Label levelInfo;

	public LayoutLevelsScore(int width, int height, ApplicationGame application) {
		super(width, height, application);
	}

	@Override
	public void init() {
		super.init();
		this.buttonBack = new Button(3, 3, "Back", Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.setLayout(this.application.getLayoutInstructions());
			}
		});
		super.addComponent(this.buttonBack);

		this.hydrogen_bubble = new Sprite(50, 5,
				new ResourceLocation(Reference.MODID, "textures/app/sprites/hydrogen_bubble.png")) {

			private final ResourceLocation HYDROGEN_BUBBLE = new ResourceLocation(Reference.MODID,
					"textures/app/sprites/hydrogen_bubble.png");
			private final ResourceLocation FIRE_BALL1 = new ResourceLocation(Reference.MODID,
					"textures/app/sprites/fire_ball1.png");
			private final ResourceLocation FIRE_BALL2 = new ResourceLocation(Reference.MODID,
					"textures/app/sprites/fire_ball2.png");

			private long time = new Date().getTime();

			@Override
			public void update(Application app, Layout layout, Minecraft mc) {
				long currentTime = new Date().getTime();
				long newTime = currentTime - time;

				long minTime = 1000 * 1;
				long maxTime = 1000 * 3;

				if (newTime >= minTime && newTime <= maxTime) {
					if (randInt(0, 10) <= 2)
						this.setSprite(FIRE_BALL2);
					else
						this.setSprite(FIRE_BALL1);
				} else {
					this.setSprite(HYDROGEN_BUBBLE);
				}

				if (newTime > maxTime) {
					time = new Date().getTime();
				}
			}
		};
		super.addComponent(this.hydrogen_bubble);

		this.labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 3, 25);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		this.textInfo = new Text("", 3, 35, 99);
		this.textInfo.setTextColor(Color.DARK_GRAY);
		this.textInfo.setText("Score Info:" + "\nBubbles | +random(1,2)" + "\nBombs   | -random(2,3)"
				+ TextFormatting.DARK_RED + TextFormatting.BOLD + "\nScore will be reset when time is up!"); // §4§L
		super.addComponent(this.textInfo);

		int liOffset = 40;
		this.levelInfo = new Label("", 147 - liOffset, 5);
		this.levelInfo.setTextColor(Colors.LIGHT_GRAY.getColor());
		this.levelInfo.setText("Level: 1 | Score < 500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 15);
		this.levelInfo.setTextColor(Colors.GREEN.getColor());
		this.levelInfo.setText("Level: 2 | Score > 500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 25);
		this.levelInfo.setTextColor(Colors.YELLOW.getColor());
		this.levelInfo.setText("Level: 3 | Score > 1000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 35);
		this.levelInfo.setTextColor(Colors.ORANGE.getColor());
		this.levelInfo.setText("Level: 4 | Score > 1500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 45);
		this.levelInfo.setTextColor(Colors.RED.getColor());
		this.levelInfo.setText("Level: 5 | Score > 2000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 55);
		this.levelInfo.setTextColor(Colors.DARK_RED.getColor());
		this.levelInfo.setText("Level: 6 | Score > 2500");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 65);
		this.levelInfo.setTextColor(Colors.MAGENTA.getColor());
		this.levelInfo.setText("Level: 7 | Score > 5000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 75);
		this.levelInfo.setTextColor(Colors.PURPLE.getColor());
		this.levelInfo.setText("Level: 8 | Score > 10000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 85);
		this.levelInfo.setTextColor(Colors.CYAN.getColor());
		this.levelInfo.setText("Level: 9 | Score > 15000");
		super.addComponent(this.levelInfo);

		this.levelInfo = new Label("", 147 - liOffset, 95);
		this.levelInfo.setTextColor(Colors.DARK_BLUE.getColor());
		this.levelInfo.setText("Level: 10 | Score > 20000");
		super.addComponent(this.levelInfo);
	}

	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}

	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			this.hydrogen_bubble.update(application, this, Minecraft.getMinecraft());
		}
	}
}
