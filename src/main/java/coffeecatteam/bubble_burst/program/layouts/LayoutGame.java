package coffeecatteam.bubble_burst.program.layouts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubble_burst.Reference;
import coffeecatteam.bubble_burst.SoundHandler;
import coffeecatteam.bubble_burst.program.ApplicationGame;
import coffeecatteam.bubble_burst.program.component.Sprite;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class LayoutGame extends Layout {

	private ApplicationGame application;

	private Image background;
	private Button buttonBack;

	private Label labelVersion;

	// Sprites
	private Sprite cursor;
	private List<Sprite> hydrogen_bubbles;
	private List<Sprite> bombs;

	// Score
	private Label labelScore;
	private long score = -4l;
	private boolean canScoreUpdate = false;

	public LayoutGame(int width, int height, ApplicationGame application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.hydrogen_bubbles = new ArrayList<>();
		this.bombs = new ArrayList<>();

		this.background = new Image(0, 0, this.width, this.height, this.width * 2 - 145, this.height / 2 - 49,
				this.width + 57, this.height + 155,
				new ResourceLocation(Reference.MODID, "textures/backgrounds/background.png"));
		super.addComponent(this.background);

		// Sprites
		int bubblesAmount = 6; // default: 6
		for (int i = 0; i < bubblesAmount; i++) {
			Sprite hydrogen_bubble = new Sprite(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10),
					new ResourceLocation(Reference.MODID, "textures/sprites/hydrogen_bubble.png"));
			this.hydrogen_bubbles.add(hydrogen_bubble);
			super.addComponent(this.hydrogen_bubbles.get(i));
		}

		int bombAmount = bubblesAmount / 2; // default: 2
		for (int i = 0; i < bombAmount; i++) {
			Sprite bomb = new Sprite(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10),
					new ResourceLocation(Reference.MODID, "textures/sprites/bomb.png"));
			this.bombs.add(bomb);
			super.addComponent(this.bombs.get(i));
		}

		// (this.width / 2) - 4, (this.height / 2) - 4
		this.cursor = new Sprite(5, this.height / 2,
				new ResourceLocation(Reference.MODID, "textures/sprites/cursor.png"));
		super.addComponent(this.cursor);

		this.buttonBack = new Button(3, 15, Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		// Score
		this.labelVersion = new Label("Version: " + Reference.VERSION, 3, this.height - 10);
		this.labelVersion.setTextColour(Color.LIGHT_GRAY);
		this.labelVersion.setScale(0.95D);
		super.addComponent(this.labelVersion);

		this.labelScore = new Label("Score: " + this.score, 3, 3);
		this.labelScore.setTextColour(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.95D);
		super.addComponent(this.labelScore);

		//this.score -= bubblesAmount;
		this.score = this.application.getTopScore();

		super.init();
	}

	@Override
	public void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
		int offset = 4;
		this.cursor.xPosition = mouseX - offset;
		this.cursor.yPosition = mouseY - offset;
	}

	public void onTick() {
		int speed = 5; // default: 5

		if (this.application.getCurrentLayout() == this) {
			// Hydrogen Bubbles
			for (Sprite hydrogen_bubble : this.hydrogen_bubbles) {

				hydrogen_bubble.yPosition += speed;
				if (hydrogen_bubble.yPosition > this.height * 2) {
					respawn(hydrogen_bubble, this.width, this.height);
				}
				// System.out.println(this.hydrogen_bubbles.size() + " | " + hydrogen_bubble.yPosition + " | " + hydrogen_bubble.xPosition);

				// Check if cursor is touching a hydrogen bubble
				if (this.cursor.isTouching(hydrogen_bubble)) {
					updateScore(this.score, 1l, this.labelScore);

					Minecraft.getMinecraft().player.playSound(SoundHandler.BUBBLE_POP, 1.0f,
							(0.5f + new Random().nextFloat()) * 1.5f);
					respawn(hydrogen_bubble, this.width, this.height);
				}
			}
			// Bombs
			for (Sprite bomb : this.bombs) {

				bomb.yPosition += speed;
				if (bomb.yPosition > this.height * 2) {
					respawn(bomb, this.width, this.height);
				}
				// System.out.println(this.hydrogen_bubbles.size() + " | " + hydrogen_bubble.yPosition + " | " + hydrogen_bubble.xPosition);

				// Check if cursor is touching a hydrogen bubble
				if (this.cursor.isTouching(bomb)) {
					updateScore(this.score, -2l, this.labelScore);

					if (randInt(0, 10) < 2)
						Minecraft.getMinecraft().player.playSound(SoundHandler.BOMB_1, 0.2f, (0.5f + new Random().nextFloat())*1.5f);
					else
						Minecraft.getMinecraft().player.playSound(SoundHandler.BOMB_2, 0.2f, (0.5f + new Random().nextFloat())*1.5f);
					respawn(bomb, this.width, this.height);
				}
			}
		} else {
			this.score = this.application.getTopScore();
		}
		
		if (this.score < 0)
			this.score = 0;
		if (this.canScoreUpdate && this.application.getCurrentLayout() == this)
			updateScore(this.score, 0l, this.labelScore);
		else
			this.canScoreUpdate = true;
	}

	public long getScore() {
		return score;
	}

	boolean fire_ball = false;
	boolean bomb = false;

	public void updateScore(long score, long amount, Label label) {
		score += amount;
		label.setText("Score: " + this.score);

		label.setTextColour(score > 25000 ? Colors.DARK_BLUE
				: score > 20000 ? Colors.CYAN
						: score > 15000 ? Colors.PURPLE
								: score > 10000 ? Colors.MAGENTA
										: score > 5000 ? Colors.DARK_RED
												: score > 2000 ? Colors.RED
														: score > 1500 ? Colors.ORANGE
																: score > 1000 ? Colors.YELLOW
																		: score > 500 ? Colors.GREEN
																				: Colors.LIGHT_GRAY);

		this.application.setTopScore(score);
		this.score = this.application.getTopScore();
		//System.out.println(this.score + " | " + this.application.getTopScore());
		this.labelScore = label;
		this.application.markDirty();
	}

	public void respawn(Sprite sprite, int width, int height) {
		int x = randInt(this.width - 40, width + 50);
		sprite.xPosition = x; // 160; // x; // 250; // x;
		sprite.yPosition = ((height / 2) - 4) + randInt(-10, 10);
	}

	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
}
