package coffeecatteam.bubbleburst.app.layouts.game;

import java.awt.Color;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.layouts.LayoutStanard;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class LayoutGameOver extends LayoutStanard {

	private Image background;

	private Button buttonRetry;

	private Label labelLogo;
	private Label labelScore;
	private Label labelVersion;

	public LayoutGameOver(int width, int height, ApplicationGame application) {
		super(width, height, application);
	}

	@Override
	public void init(Layout layout) {
		this.background = new Image(0, 0, this.width, this.height, this.width * 2 - 144, this.height / 2 - 49,
				this.width + 56, this.height + 155,
				new ResourceLocation(Reference.MODID, "textures/app/backgrounds/background.png"));
		super.addComponent(this.background);

		this.buttonRetry = new Button(3, 3, "Retry", Icons.HOME);
		this.buttonRetry.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.getLayoutGame().resetScore();
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonRetry);

		this.labelLogo = new Label("Bubble Burst", this.width / 2,
				this.height / 2-20);
		this.labelLogo.setAlignment(this.ALIGN_CENTER);
		this.labelLogo.setTextColour(Color.ORANGE);
		this.labelLogo.setScale(2.0D);
		super.addComponent(this.labelLogo);

		this.labelScore = new Label(Minecraft.getMinecraft().player.getName() + ", you gained an amazing:", this.width / 2,
				this.height / 2);
		this.labelScore.setAlignment(this.ALIGN_CENTER);
		this.labelScore.setTextColour(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.9D);
		super.addComponent(this.labelScore);

		this.labelScore = new Label(String.valueOf(this.application.getTopScore()), this.width / 2-10,
				this.height / 2+10);
		this.labelScore.setAlignment(this.ALIGN_RIGHT);
		this.labelScore.setTextColour(Color.ORANGE);
		super.addComponent(this.labelScore);

		this.labelScore = new Label("points!", this.width / 2+12,
				this.height / 2+10);
		this.labelScore.setAlignment(this.ALIGN_CENTER);
		this.labelScore.setTextColour(Color.LIGHT_GRAY);
		super.addComponent(this.labelScore);

		this.labelVersion = new Label("Version: " + Reference.VERSION, 3, this.height - 10);
		this.labelVersion.setTextColour(Color.LIGHT_GRAY);
		this.labelVersion.setScale(0.95D);
		super.addComponent(this.labelVersion);
	}

	@Override
	public void onTick() {
	}

	@Override
	public void load(NBTTagCompound nbt) {
	}

	@Override
	public void save(NBTTagCompound nbt) {
	}
}
