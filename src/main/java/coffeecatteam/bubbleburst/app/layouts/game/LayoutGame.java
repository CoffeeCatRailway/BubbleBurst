package coffeecatteam.bubbleburst.app.layouts.game;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteCursor;
import coffeecatteam.bubbleburst.app.component.sprites.entity.SpriteBomb;
import coffeecatteam.bubbleburst.app.component.sprites.entity.SpriteBubble;
import coffeecatteam.bubbleburst.app.component.sprites.special.SpriteCheese;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import coffeecatteam.bubbleburst.util.Utils.Colors;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LayoutGame extends LayoutStandard {

	private Button buttonBack;
    private Label labelVersion;

	// Timer
    private Label labelTimer;
	private static long time;

	private int maxGameTime = 30; //30 * 4; // 30 = 1 min
	private int timer = 0;

	private long newTime;
    private long maxTime = 1000 * 2;

	// Sprites
    public SpriteCursor cursor;

    public List<SpriteBubble> hydrogen_bubbles;
    public List<SpriteBomb> bombs;

	// Score
	public Label labelScore;
	private long score = -41;
	private boolean resetScore = false;

	// Bomb Count
	public Label labelBombCount;
	private long bombCount = 1;
	private boolean resetBombCount = false;

	public LayoutGame(int width, int height, ApplicationGame application) {
		super(width, height, application, true, getRandomBackground());
	}

	@Override
	public void init() {
		super.init();
		setBackground(getRandomBackground());
		time = new Date().getTime();
		this.hydrogen_bubbles = new ArrayList<>();
		this.bombs = new ArrayList<>();

		// Sprites
		int bubblesAmount = this.application.getBubblesAmount();
		for (int i = 0; i < bubblesAmount; i++) {
			SpriteBubble hydrogen_bubble = new SpriteBubble(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10), randInt(1, 3), this.application);
			this.hydrogen_bubbles.add(hydrogen_bubble);
			super.addComponent(this.hydrogen_bubbles.get(i));
		}

		int bombAmount = this.application.getBombsAmount();
		for (int i = 0; i < bombAmount; i++) {
			SpriteBomb bomb = new SpriteBomb(this.width / 2, ((this.height / 2) - 4) + randInt(-10, 10), randInt(1, 3), this.application);
			this.bombs.add(bomb);
			super.addComponent(this.bombs.get(i));
		}

		// (this.width / 2) - 4, (this.height / 2) - 4
		this.cursor = new SpriteCursor(5, this.height / 2);
		super.addComponent(this.cursor);

		this.buttonBack = new Button(this.width - 45, 3, "Exit", Icons.HOME);
		this.buttonBack.setToolTip("Exit", "Click or press E to exit to main menu."
				+ "\nExiting the game will pause the current game.");
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		// Score
		this.labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 3, this.height - 10);
		this.labelVersion.setTextColor(Color.LIGHT_GRAY);
		this.labelVersion.setScale(0.95D);
		super.addComponent(this.labelVersion);

		this.labelTimer = new Label("Time left: " + getCurrentGameTime(), this.width / 2, 3);
		this.labelTimer.setTextColor(Color.LIGHT_GRAY);
		this.labelTimer.setScale(0.95D);
		this.labelTimer.setAlignment(this.ALIGN_CENTER);
		super.addComponent(this.labelTimer);

		this.labelScore = new Label("Score: " + this.score, 3, 3);
		this.labelScore.setTextColor(Color.LIGHT_GRAY);
		this.labelScore.setScale(0.95D);
		super.addComponent(this.labelScore);

		this.labelBombCount = new Label("Bombs hit: " + this.bombCount, 3, 15);
		this.labelBombCount.setTextColor(Color.LIGHT_GRAY);
		this.labelBombCount.setScale(0.95D);
		super.addComponent(this.labelBombCount);

		if (resetScore) {
			this.score = 0;
			resetScore = false;
		} else {
			this.score = this.application.getTopScore();
		}
		if (resetBombCount) {
			this.bombCount = 0;
			resetBombCount = false;
		} else {
			this.bombCount = this.application.getTopBombCount();
		}
	}

	@Override
	public void handleKeyReleased(char character, int code) {
		// System.out.println(String.valueOf(character) + " | " + code);
		if (code == 18)
			this.application.restoreDefaultLayout();
		super.handleKeyReleased(character, code);
	}

	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			// Time
			long currentTime = new Date().getTime();
			newTime = currentTime - time;

			// Game Code
			this.cursor.update(application, this, Minecraft.getMinecraft());
			this.hydrogen_bubbles
					.forEach(hydrogen_bubble -> hydrogen_bubble.update(application, this, Minecraft.getMinecraft()));
			this.bombs.forEach(bomb -> bomb.update(application, this, Minecraft.getMinecraft()));

			// End Time
			if (newTime > maxTime) {
				time = new Date().getTime();
				timer++;
			}
			labelTimer.setText("Time left: " + getCurrentGameTime());

			if (timer >= maxGameTime) {
				timer = 0;
				this.application.setLayout(new LayoutGameOver(200, 100, this.application, this.getBackground()));
			}
		} else {
			this.score = this.application.getTopScore();
			this.bombCount = this.application.getTopBombCount();
		}

		// Score Check
		if (this.score < 0)
			this.score = 0;

		// Bomb Count Check
		if (this.bombCount < 0)
			this.bombCount = 0;
	}

	/*
	 * Game methods
	 */

	public int getCurrentGameTime() {
		return maxGameTime - timer;
	}

	public int getMaxGameTime() {
		return maxGameTime;
	}

	public void resetScore() {
		resetScore = true;
		this.application.setTopScore(1);
	}

	public void resetBombCount() {
		resetBombCount = true;
		this.application.setTopBombCount(1);
	}

	// Score Label Check
	public long getScore() {
		return score;
	}

	public void updateScore(long score, long amount, Label label) {
		score += amount;
		label.setText("Score: " + this.score);
		label.setTextColor(getColorFromValue(score));

		this.application.setTopScore(score);
		this.score = this.application.getTopScore();
		this.labelScore = label;
		this.application.markDirty();
		this.application.save(Minecraft.getMinecraft().player.getEntityData());
	}

	// Bomb Count Label Check
	public long getBombCount() {
		return bombCount;
	}

	public void updateBombCount(long bombCount, long amount, Label label) {
		bombCount += amount;
		label.setText("Bombs hit: " + this.bombCount);
		label.setTextColor(getColorFromValue(bombCount));

		this.application.setTopBombCount(bombCount);
		this.bombCount = this.application.getTopBombCount();
		this.labelBombCount = label;
		this.application.markDirty();
		this.application.save(Minecraft.getMinecraft().player.getEntityData());
	}

	private Color getColorFromValue(long value) {
		Colors color = value >= 25000 ? Colors.DARK_BLUE
				: value >= 20000 ? Colors.CYAN
						: value >= 15000 ? Colors.PURPLE
								: value >= 10000 ? Colors.MAGENTA
										: value >= 5000 ? Colors.DARK_RED
												: value >= 2000 ? Colors.RED
														: value >= 1500 ? Colors.ORANGE
																: value >= 1000 ? Colors.YELLOW
																		: value >= 500 ? Colors.GREEN
																				: Colors.LIGHT_GRAY;
		return color.getColor();
	}

	public void respawn(Sprite sprite, int width, int height) {
		sprite.xPosition = randInt(width-267, width-40);
		sprite.yPosition = randInt(-5, 15);
	}

	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
}
