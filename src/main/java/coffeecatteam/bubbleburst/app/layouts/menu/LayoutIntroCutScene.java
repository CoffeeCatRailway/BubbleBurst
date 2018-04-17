package coffeecatteam.bubbleburst.app.layouts.menu;

import java.awt.Color;
import java.util.Date;
import java.util.Random;

import com.mrcrayfish.device.MrCrayfishDeviceMod;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.core.Laptop;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import coffeecatteam.bubbleburst.util.handlers.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class LayoutIntroCutScene extends LayoutStandard {

	// Timer
	public Label labelTimer;
	private static long time;

	private int maxGameTime = 4;
	private int timer = 0;

	private long newTime;
	public long minTime = 1000 * 1;
	public long maxTime = 1000 * 2;

	// Features
	public Image title;
	private int titleWidth = 200;
	
	public Label madeBy;

	public LayoutIntroCutScene(int width, int height, ApplicationGame application) {
		super(width, height, application, true, new ResourceLocation(Reference.MODID, "textures/app/backgrounds/intro_cut_scene.png"));
	}

	@Override
	public void init() {
		super.init();

		this.title = new Image(this.width, this.height / 8, titleWidth, 60, 0, 0, 259, 250, new ResourceLocation(Reference.MODID, "textures/app/intro_cut_scene_title.png"));
		if (MrCrayfishDeviceMod.DEVELOPER_MODE) {
			this.title.setBorderThickness(2);
			this.title.setBorderVisible(true);
		}
		super.addComponent(this.title);
		this.madeBy = new Label("Made By: CoffeeCatRailway", this.width / 2 - 20, this.height);
		this.madeBy.setTextColor(Color.ORANGE);
		this.madeBy.setAlignment(this.ALIGN_CENTER);
		super.addComponent(this.madeBy);

		this.labelTimer = new Label("Time left: " + getCurrentGameTime(), this.width / 2, 3);
		this.labelTimer.setTextColor(Color.WHITE);
		this.labelTimer.setScale(0.95D);
		this.labelTimer.setAlignment(this.ALIGN_CENTER);
		if (MrCrayfishDeviceMod.DEVELOPER_MODE)
			super.addComponent(this.labelTimer);
	}
	
	@Override
	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			// Time
			long currentTime = new Date().getTime();
			newTime = currentTime - time;

			// Sound and Volume
			float v = this.application.getGameVolume() - 0.3f;
			this.application.setGameVolume(v < 0.0f ? 0.0f : v <= 0.3f ? v += ((0.1f + v) * 0.5f) : v);
			SoundEvent sound = (this.application.getLayoutGame().randInt(0, 10) < 2) ? SoundHandler.BOMB_1 : SoundHandler.BOMB_2;
			float pitch = (0.5f + new Random().nextFloat()) * 1.5f;

			// Update Features
			if (this.title.xPosition >= this.width / 2 - (this.titleWidth / 2)) {
				int moveSpeed = 7;
//				if (this.title.xPosition <= this.width / 2) {
//					moveSpeed = 4;
//					if (this.title.xPosition < this.width / 3 + 2)
//						Minecraft.getMinecraft().player.playSound(sound, this.application.getGameVolume(), pitch);
//				}
				this.title.xPosition -= moveSpeed;
			}
			if (this.madeBy.yPosition > (this.height / 2) + 35 && this.title.xPosition <= this.width / 2) {
				int moveSpeed = 7;
				if (this.madeBy.yPosition <= (this.height / 2) + 55)
					moveSpeed = 4;
				this.madeBy.yPosition -= moveSpeed;
			}

			// End Time
			if (newTime > maxTime) {
				time = new Date().getTime();
				timer++;
			}
			labelTimer.setText("Time left: " + getCurrentGameTime());

			if (timer >= maxGameTime) {
				timer = 0;
				this.application.getLayoutGame().init();
				this.application.setLayout(this.application.getLayoutGame());
			}
		}
	}

	public int getCurrentGameTime() {
		return maxGameTime - timer;
	}
}
