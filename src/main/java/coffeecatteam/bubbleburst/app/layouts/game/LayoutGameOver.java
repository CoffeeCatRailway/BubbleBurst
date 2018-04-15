package coffeecatteam.bubbleburst.app.layouts.game;

import java.awt.Color;

import com.mrcrayfish.device.api.app.Dialog;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.ApplicationScoreBoard;
import coffeecatteam.bubbleburst.app.layouts.LayoutSettings;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class LayoutGameOver extends LayoutStandard {
	
	private Button buttonRetry;
	private Button buttonSaveScore;

	private Label labelLogo;
	private Label labelScore;
	private Label labelVersion;

	public LayoutGameOver(int width, int height, ApplicationGame application) {
		super(width, height, application, true, BG_DEFAULT);
	}

	@Override
	public void init() {
		this.buttonRetry = new Button(3, 3, "Retry", Icons.HOME);
		this.buttonRetry.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.getLayoutGame().resetScore();
				this.application.getLayoutGame().resetBombCount();
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonRetry);

		this.buttonSaveScore = new Button(140, 3, "Score", Icons.SAVE);
		this.buttonSaveScore.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				LayoutSettings settings = application.getLayoutSettings();
				Dialog dialog;
				
				if (settings.isBombsSetToDefault() && settings.isBubblesSetToDefault()) {
					ApplicationScoreBoard.addScore(Minecraft.getMinecraft().player.getName(), this.application.getTopScore());
					dialog = new Dialog.Message("Saved score to global scoreboard!");
				} else {
					dialog = new Dialog.Message("Can not save score, settings have been changed!");
				}
				application.openDialog(dialog);
			}
		});
		super.addComponent(this.buttonSaveScore);

		this.labelLogo = new Label("Bubble Burst", this.width / 2, this.height / 2 - 20);
		this.labelLogo.setAlignment(this.ALIGN_CENTER);
		this.labelLogo.setTextColor(Color.ORANGE);
		this.labelLogo.setScale(2.0D);
		super.addComponent(this.labelLogo);

		// Score
		this.labelScore = new Label("", this.width / 2, this.height / 2);
		this.labelScore.setAlignment(this.ALIGN_CENTER);
		this.labelScore.setTextColor(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.9D);
		this.labelScore.setText(Minecraft.getMinecraft().player.getName() + ", you gained an amazing:");
		super.addComponent(this.labelScore);

		this.labelScore = new Label("", this.width / 2, this.height / 2 + 10);
		this.labelScore.setAlignment(this.ALIGN_CENTER);
		this.labelScore.setTextColor(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.9D);
		this.labelScore.setText("" + TextFormatting.GOLD + TextFormatting.BOLD
				+ String.valueOf(this.application.getTopScore()) + TextFormatting.RESET + " points!");
		super.addComponent(this.labelScore);

		// Bombs hit
		this.labelScore = new Label("", this.width / 2, this.height / 2 + 20);
		this.labelScore.setAlignment(this.ALIGN_CENTER);
		this.labelScore.setTextColor(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.9D);
		this.labelScore.setText("You also hit: " + TextFormatting.GOLD + TextFormatting.BOLD
				+ String.valueOf(this.application.getTopBombCount()) + TextFormatting.RESET + " bombs!");
		super.addComponent(this.labelScore);

		// Total Score (score-bombsHit)
		long score = this.application.getTopScore();
		long bombsHit = this.application.getTopBombCount();

		this.labelScore = new Label("", this.width / 2, this.height / 2 + 30);
		this.labelScore.setAlignment(this.ALIGN_CENTER);
		this.labelScore.setTextColor(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.9D);
		this.labelScore.setText(TextFormatting.RESET + "Total score: " + TextFormatting.GOLD + TextFormatting.BOLD
				+ (score - bombsHit));
		super.addComponent(this.labelScore);

		// Game Version
		this.labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 3, this.height - 10);
		this.labelVersion.setTextColor(Color.LIGHT_GRAY);
		this.labelVersion.setScale(0.95D);
		super.addComponent(this.labelVersion);

		// Player's face
		ResourceLocation p_skin = Minecraft.getMinecraft().player.getLocationSkin();
		int px = 100 - 16;
		int py = 3;
		int psize = 24;

		Image p_face = new Image(px, py, psize, psize, 32, 32, 32, 32, p_skin);
		super.addComponent(p_face);
		p_face = new Image(px - 1, py - 1, psize + 2, psize + 2, 160, 32, 32, 32, p_skin);
		super.addComponent(p_face);
	}

	@Override
	public void onTick() {
	}
}
