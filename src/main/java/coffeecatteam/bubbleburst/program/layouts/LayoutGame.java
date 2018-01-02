package coffeecatteam.bubbleburst.program.layouts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.program.ApplicationGame;
import coffeecatteam.bubbleburst.program.component.Sprite;
import coffeecatteam.bubbleburst.utill.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class LayoutGame extends Layout {

	private ApplicationGame application;

	private Image background;
	private Button buttonBack;

	private Label labelVersion;
	private static long time;

	// Sprites
	private Sprite cursor;
	private List<Sprite> hydrogen_bubbles;
	private static final ResourceLocation HYDROGEN_BUBBLE = new ResourceLocation(Reference.MODID,
			"textures/sprites/hydrogen_bubble.png");
	private static final ResourceLocation FIRE_BALL1 = new ResourceLocation(Reference.MODID,
			"textures/sprites/fire_ball1.png");
	private static final ResourceLocation FIRE_BALL2 = new ResourceLocation(Reference.MODID,
			"textures/sprites/fire_ball2.png");

	private List<Sprite> bombs;
	private static final ResourceLocation BOMB = new ResourceLocation(Reference.MODID, "textures/sprites/bomb.png");
	private static final ResourceLocation EXPLOSION = new ResourceLocation(Reference.MODID,
			"textures/sprites/explosion.png");

	// Score
	private Label labelScore;
	private long score = -4l;
	private boolean canScoreUpdate = false;

	private String[] gameScores;
	private String gameScorePlayer;

	public LayoutGame(int width, int height, ApplicationGame application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.components.clear();
		this.application.load(Minecraft.getMinecraft().player.getEntityData());
		time = new Date().getTime();
		this.hydrogen_bubbles = new ArrayList<>();
		this.bombs = new ArrayList<>();

		this.background = new Image(0, 0, this.width, this.height, this.width * 2 - 144, this.height / 2 - 49,
				this.width + 56, this.height + 155,
				new ResourceLocation(Reference.MODID, "textures/backgrounds/background.png"));
		super.addComponent(this.background);

		// Sprites
		int bubblesAmount = 6; // default: 6
		for (int i = 0; i < bubblesAmount; i++) {
			Sprite hydrogen_bubble = new Sprite(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10),
					HYDROGEN_BUBBLE);
			this.hydrogen_bubbles.add(hydrogen_bubble);
			super.addComponent(this.hydrogen_bubbles.get(i));
		}

		int bombAmount = bubblesAmount / 2; // default: 2
		for (int i = 0; i < bombAmount; i++) {
			Sprite bomb = new Sprite(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10), BOMB);
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

		// this.score -= bubblesAmount;
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
		int speed = 5 + randInt(0, 2); // default: 5

		if (this.application.getCurrentLayout() == this) {
			// Time
			long currentTime = new Date().getTime();
			long newTime = currentTime - time;

			long minTime = 1000 * 1;
			long maxTime = 1000 * 2;

			// Hydrogen Bubbles
			for (Sprite hydrogen_bubble : this.hydrogen_bubbles) {

				if (hydrogen_bubble.canMove())
					hydrogen_bubble.yPosition += speed;
				if (hydrogen_bubble.yPosition > this.height * 2)
					respawn(hydrogen_bubble, this.width, this.height);
				// System.out.println(this.hydrogen_bubbles.size() + " | " +
				// hydrogen_bubble.yPosition + " | " +
				// hydrogen_bubble.xPosition);

				// Check if cursor is touching a hydrogen bubble
				if (this.cursor.isTouching(hydrogen_bubble)) {
					if (!(hydrogen_bubble.getSprite().equals(FIRE_BALL1))) {
						updateScore(this.score, 1l, this.labelScore);

						Minecraft.getMinecraft().player.playSound(SoundHandler.BUBBLE_POP, 1.0f,
								(0.5f + new Random().nextFloat()) * 1.5f);

						hydrogen_bubble.setCanMove(false);
					}
				}

				if (!hydrogen_bubble.canMove()) {
					for (float i = 0; i < 5; i += 0.01) {
						if (inTime(newTime, minTime, maxTime)) {
							if (randInt(0, 10) <= 2)
								hydrogen_bubble.setSprite(FIRE_BALL2);
							else
								hydrogen_bubble.setSprite(FIRE_BALL1);
						} else {
							hydrogen_bubble.setSprite(HYDROGEN_BUBBLE);
							hydrogen_bubble.setCanMove(true);
							respawn(hydrogen_bubble, this.width, this.height);
						}
					}
				}
			}
			// Bombs
			for (Sprite bomb : this.bombs) {

				if (bomb.canMove())
					bomb.yPosition += speed;
				if (bomb.yPosition > this.height * 2)
					respawn(bomb, this.width, this.height);
				// System.out.println(this.hydrogen_bubbles.size() + " | " +
				// hydrogen_bubble.yPosition + " | " +
				// hydrogen_bubble.xPosition);

				// Check if cursor is touching a hydrogen bubble
				if (this.cursor.isTouching(bomb)) {
					if (!(bomb.getSprite().equals(EXPLOSION))) {
						updateScore(this.score, -2l, this.labelScore);

						if (randInt(0, 10) < 2)
							Minecraft.getMinecraft().player.playSound(SoundHandler.BOMB_1, 0.2f,
									(0.5f + new Random().nextFloat()) * 1.5f);
						else
							Minecraft.getMinecraft().player.playSound(SoundHandler.BOMB_2, 0.2f,
									(0.5f + new Random().nextFloat()) * 1.5f);

						bomb.setCanMove(false);
					}
				}

				if (!bomb.canMove()) {
					for (float i = 0; i < 5; i += 0.01) {
						if (inTime(newTime, minTime, maxTime)) {
							bomb.setSprite(EXPLOSION);
						} else {
							bomb.setSprite(BOMB);
							bomb.setCanMove(true);
							respawn(bomb, this.width, this.height);
						}
					}
				}
			}

			// End Time
			// System.out.println(new SimpleDateFormat("mm:ss:SSS").format(new
			// Date(newTime)) + "\n" + newTime + " | " + minTime + " | " +
			// maxTime);
			if (newTime > maxTime) {
				time = new Date().getTime();
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

	public boolean inTime(long time, long minTime, long maxTime) {
		return time >= minTime && time <= maxTime;
	}

	public long getScore() {
		return score;
	}

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
		// System.out.println(this.score + " | " +
		// this.application.getTopScore());
		this.labelScore = label;
		this.application.markDirty();
		this.application.save(Minecraft.getMinecraft().player.getEntityData());
	}

	public void respawn(Sprite sprite, int width, int height) {
		int x = randInt(width - 40, width + 50);
		sprite.xPosition = x; // 160; // x; // 250; // x;
		sprite.yPosition = ((height / 2) - 4) + randInt(-10, 10);
	}

	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
}
