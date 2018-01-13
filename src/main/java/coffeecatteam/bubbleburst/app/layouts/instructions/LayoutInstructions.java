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
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class LayoutInstructions extends LayoutStandard {

	private Button buttonBack;
	private Label labelVersion;
	private Sprite bomb;

	private Text textInfo;

	private Button buttonLevelsScore;

	public LayoutInstructions(int width, int height, ApplicationGame application) {
		super(width, height, application);
	}

	@Override
	public void init(Layout layout) {
		this.buttonBack = new Button(3, 1, "Back", Icons.HOME);
		this.buttonBack.setToolTip("Exit", "Exit to main menu.");
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 3, 45);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		this.bomb = new Sprite(50, 3, new ResourceLocation(Reference.MODID, "textures/app/sprites/bomb.png")) {

			private final ResourceLocation BOMB = new ResourceLocation(Reference.MODID,
					"textures/app/sprites/bomb.png");
			private final ResourceLocation EXPLOSION = new ResourceLocation(Reference.MODID,
					"textures/app/sprites/explosion.png");

			private long time = new Date().getTime();

			@Override
			public void update(Application app, Layout layout, Minecraft mc) {
				long currentTime = new Date().getTime();
				long newTime = currentTime - time;

				long minTime = 1000 * 1;
				long maxTime = 1000 * 3;

				if (newTime >= minTime && newTime <= maxTime) {
					this.setSprite(EXPLOSION);
				} else {
					this.setSprite(BOMB);
				}

				if (newTime > maxTime) {
					time = new Date().getTime();
				}
			}
		};
		super.addComponent(this.bomb);

		this.textInfo = new Text("", 3, 35, 115);
		this.textInfo.setTextColor(Color.DARK_GRAY);
		this.textInfo.setText("\n\nControls:" + "\n- Click and drag/hold to move the \"fire stick\"");
		super.addComponent(this.textInfo);

		this.textInfo = new Text("", 115, 3, 145);
		this.textInfo.setTextColor(Color.DARK_GRAY);
		this.textInfo.setText("Game Info:"
				+ "\nThe goal of this game is to \"burst\" the hydrogen bubbles and get the highest score you can."
				+ "\n\nYou will have a max time limit of ~" + TextFormatting.GOLD + TextFormatting.BOLD
				+ this.application.getLayoutGame().getMaxGameTime() * 2 + TextFormatting.RESET
				+ " seconds to score as many points as you posibly can."
				+ TextFormatting.DARK_RED + TextFormatting.BOLD + "\nSettings will be reset on game closed.");
		super.addComponent(this.textInfo);

		this.buttonLevelsScore = new Button(3, 23, "Levels & Scoring", Icons.INFO);
		this.buttonLevelsScore.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.setLayout(this.application.getLayoutLevelsScore());
			}
		});
		super.addComponent(this.buttonLevelsScore);
	}

	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			this.bomb.update(application, this, Minecraft.getMinecraft());
		}
	}
}
