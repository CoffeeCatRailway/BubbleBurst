package coffeecatteam.bubbleburst.app.layouts.game;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteBomb;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteCursor;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteHydrogenBall;
import coffeecatteam.bubbleburst.app.layouts.LayoutStanard;
import coffeecatteam.bubbleburst.utill.Utills.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class LayoutGame extends LayoutStanard {

	private Image background;
	private Button buttonBack;

	public Label labelVersion;
	private boolean gameFinished = false;

	// Timer
	public Label labelTimer;
	private static long time;

	private int maxGameTime = 30*4; // 30 = 1 min
	private int timer = 0;

	private long newTime;
	public long minTime = 1000 * 1;
	public long maxTime = 1000 * 2;

	// Sprites
	public Sprite cursor;

	public List<Sprite> hydrogen_bubbles;
	public List<Sprite> bombs;

	// Score
	public Label labelScore;
	private long score = -4l;
	private boolean canScoreUpdate = false;
	private boolean resetScore = false;

	private String[] gameScores;
	private String gameScorePlayer;

	public LayoutGame(int width, int height, ApplicationGame application) {
		super(width, height, application);
	}

	@Override
	public void init(Layout layout) {
		time = new Date().getTime();
		this.hydrogen_bubbles = new ArrayList<>();
		this.bombs = new ArrayList<>();

		if (gameFinished) {
			if (timer == maxGameTime) {
				gameFinished = false;
				timer = 0;
			}
		}

		this.background = new Image(0, 0, this.width, this.height, this.width * 2 - 144, this.height / 2 - 49,
				this.width + 56, this.height + 155,
				new ResourceLocation(Reference.MODID, "textures/app/backgrounds/background.png"));
		super.addComponent(this.background);

		// Sprites
		int bubblesAmount = 8; // default: 6
		for (int i = 0; i < bubblesAmount; i++) {
			Sprite hydrogen_bubble = new SpriteHydrogenBall(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10),
					randInt(1, 3));
			this.hydrogen_bubbles.add(hydrogen_bubble);
			super.addComponent(this.hydrogen_bubbles.get(i));
		}

		int bombAmount = 3; // default: 3
		for (int i = 0; i < bombAmount; i++) {
			Sprite bomb = new SpriteBomb(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10),
					randInt(2, 3));
			this.bombs.add(bomb);
			super.addComponent(this.bombs.get(i));
		}

		// (this.width / 2) - 4, (this.height / 2) - 4
		this.cursor = new SpriteCursor(5, this.height / 2);
		super.addComponent(this.cursor);

		this.buttonBack = new Button(this.width - 45, 3, "Exit", Icons.HOME);
		this.buttonBack.setToolTip("Exit", "Exiting the game will pause the current game.");
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

		this.labelTimer = new Label("Time left: " + getCurrentGameTime(), this.width / 2, 3);
		this.labelTimer.setTextColour(Color.LIGHT_GRAY);
		this.labelTimer.setScale(0.95D);
		this.labelTimer.setAlignment(this.ALIGN_CENTER);
		super.addComponent(this.labelTimer);

		this.labelScore = new Label("Score: " + this.score, 3, 3);
		this.labelScore.setTextColour(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.95D);
		super.addComponent(this.labelScore);

		if (resetScore) {
			this.score = 0;
			resetScore = false;
		} else {
			this.score = this.application.getTopScore();
		}
	}

	@Override
	public void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
		int offset = 4;
		this.cursor.xPosition = mouseX - offset;
		this.cursor.yPosition = mouseY - offset;
	}

	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			// Time
			long currentTime = new Date().getTime();
			newTime = currentTime - time;

			this.cursor.update(application, this, Minecraft.getMinecraft());
			this.hydrogen_bubbles.forEach(hydrogen_bubble -> hydrogen_bubble.update(application, this, Minecraft.getMinecraft()));
			this.bombs.forEach(bomb -> bomb.update(application, this, Minecraft.getMinecraft()));

			// End Time
			// System.out.println(getTime(newTime)[0] + " | " +
			// getTime(newTime)[1] + " | " + getTime(newTime)[2]);
			// System.out.println(newTime + " | " + minTime + " | " + maxTime);
			if (newTime > maxTime) {
				time = new Date().getTime();
				timer++;
			}
			labelTimer.setText("Time left: " + getCurrentGameTime());

			// System.out.println(timer);
			if (timer >= maxGameTime) {
				gameFinished = true;
				this.application.setLayout(new LayoutGameOver(200, 100, this.application));
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

	@Override
	public void load(NBTTagCompound nbt) {
	}

	@Override
	public void save(NBTTagCompound nbt) {
	}

	/*
	 * Game methods
	 */
	public long getNewTime() {
		return newTime;
	}
	
	public int getCurrentGameTime() {
		return maxGameTime - timer;
	}

	public int getMaxGameTime() {
		return maxGameTime;
	}

	public void resetScore() {
		resetScore = true;
	}

	public String[] getTime(long time) {
		return new SimpleDateFormat("mm:ss:SSS").format(new Date(time)).split(":");
	}

	public boolean inTime(long time, long minTime, long maxTime) {
		return time >= minTime && time <= maxTime;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public void updateScore(long score, long amount, Label label) {
		score += amount;
		label.setText("Score: " + this.score);

		label.setTextColour(score > 25000 ? Colors.DARK_BLUE.getColor()
				: score > 20000 ? Colors.CYAN.getColor()
						: score > 15000 ? Colors.PURPLE.getColor()
								: score > 10000 ? Colors.MAGENTA.getColor()
										: score > 5000 ? Colors.DARK_RED.getColor()
												: score > 2000 ? Colors.RED.getColor()
														: score > 1500 ? Colors.ORANGE.getColor()
																: score > 1000 ? Colors.YELLOW.getColor()
																		: score > 500 ? Colors.GREEN.getColor()
																				: Colors.LIGHT_GRAY.getColor());

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

	public static float randFloat(float min, float max) {
		float out = min + (float) (Math.random() * (max - min + 1));
		System.out.println(out);
		return out;
	}
}
