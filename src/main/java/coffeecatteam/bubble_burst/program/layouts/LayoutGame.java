package coffeecatteam.bubble_burst.program.layouts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubble_burst.Reference;
import net.minecraft.util.ResourceLocation;

public class LayoutGame extends Layout {

	private Application application;

	private Image background;
	private Image cursor;
	private List<Image> hydrogen_bubbles;

	private Label labelScore;
	private int score;

	private Button buttonBack;

	public LayoutGame(int width, int height, Application application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.hydrogen_bubbles = new ArrayList<>();
		int width = this.application.getWidth();
		int height = this.application.getHeight();

		this.background = new Image(0, 0, width, height, width * 2 - 145, height / 2 - 49, width + 57, height + 155,
				new ResourceLocation(Reference.MODID, "textures/backgrounds/background.png"));
		super.addComponent(this.background);

		for (int i = 0; i < 5; i++) {
			Image hydrogen_bubble = new Image(randInt(40, 160), ((height / 2) - 4) + randInt(-10, 10), 16, 16, 0, 0,
					16 * 16, 16 * 16, new ResourceLocation(Reference.MODID, "textures/sprites/hydrogen_bubble.png"));
			this.hydrogen_bubbles.add(hydrogen_bubble);
			super.addComponent(this.hydrogen_bubbles.get(i));
		}

		// (width / 2) - 4, (height / 2) - 4
		this.cursor = new Image(-20, -20, 16, 16, 0, 0, 16 * 16, 16 * 16,
				new ResourceLocation(Reference.MODID, "textures/sprites/cursor.png"));
		super.addComponent(this.cursor);

		this.buttonBack = new Button(3, 15, Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.labelScore = new Label("Score: " + this.score, 3, 3);
		this.labelScore.setTextColour(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.95D);
		super.addComponent(this.labelScore);

		super.init();
	}

	@Override
	public void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
		int offset = 4;
		this.cursor.xPosition = mouseX - offset;
		this.cursor.yPosition = mouseY - offset;
	}

	public void onTick() {
		int width = this.application.getWidth();
		int height = this.application.getHeight();

		if (this.application.getCurrentLayout() == this) {
			for (Image hydrogen_bubble : this.hydrogen_bubbles) {
				
				hydrogen_bubble.yPosition += 5;
				// System.out.println(this.hydrogen_bubbles.size() + " | " + i +
				// " | " + hydrogen_bubble.yPosition + " | "
				// + hydrogen_bubble.xPosition);

				if (hydrogen_bubble.yPosition > height * 2) {
					respawn(hydrogen_bubble, width, height);
				}

				int offset = 10;
				if (this.cursor.xPosition + offset > hydrogen_bubble.xPosition
						&& this.cursor.xPosition - offset < hydrogen_bubble.xPosition) {
					if (this.cursor.yPosition + offset > hydrogen_bubble.yPosition
							&& this.cursor.yPosition - offset < hydrogen_bubble.yPosition) {
						this.score++;
						this.labelScore.setText("Score: " + this.score);
						respawn(hydrogen_bubble, width, height);
					}
				}
			}
		} else {
			this.score = -5;
		}
	}
	
	public static void respawn(Image image, int width, int height) {
		int x = randInt(width - 40, width + 50);
		image.xPosition = x; // 160; // x; // 250; // x;
		image.yPosition = ((height / 2) - 4) + randInt(-10, 10);
	}

	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
}
