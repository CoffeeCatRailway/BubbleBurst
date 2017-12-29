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
import com.mrcrayfish.device.tileentity.TileEntityLaptop;

import coffeecatteam.bubble_burst.Reference;
import coffeecatteam.bubble_burst.SoundHandler;
import coffeecatteam.bubble_burst.program.component.Sprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.WoodlandMansion;

public class LayoutGame extends Layout {

	private Application application;

	private Image background;
	private Button buttonBack;

	// Sprites
	private Sprite cursor;
	private List<Sprite> hydrogen_bubbles;

	// Score
	private Label labelScore;
	private long score;

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

		int bubbles = 5; // default: 5
		for (int i = 0; i < bubbles; i++) {
			Sprite hydrogen_bubble = new Sprite(randInt(40, 160), ((height / 2) - 4) + randInt(-10, 10),
					new ResourceLocation(Reference.MODID, "textures/sprites/hydrogen_bubble.png"));
			this.hydrogen_bubbles.add(hydrogen_bubble);
			super.addComponent(this.hydrogen_bubbles.get(i));
		}

		// (width / 2) - 4, (height / 2) - 4
		this.cursor = new Sprite(5, height / 2, new ResourceLocation(Reference.MODID, "textures/sprites/cursor.png"));
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
		int speed = 5; // default: 5
		int width = this.application.getWidth();
		int height = this.application.getHeight();

		if (this.application.getCurrentLayout() == this) {
			for (Sprite hydrogen_bubble : this.hydrogen_bubbles) {

				hydrogen_bubble.yPosition += speed;
				// System.out.println(this.hydrogen_bubbles.size() + " | " + i +
				// " | " + hydrogen_bubble.yPosition + " | " +
				// hydrogen_bubble.xPosition);

				if (hydrogen_bubble.yPosition > height * 2) {
					respawn(hydrogen_bubble, width, height);
				}

				int offset = 8; // default: 8
				if (this.cursor.xPosition + offset > hydrogen_bubble.xPosition
						&& this.cursor.xPosition - offset < hydrogen_bubble.xPosition) {
					if (this.cursor.yPosition + offset > hydrogen_bubble.yPosition
							&& this.cursor.yPosition - offset < hydrogen_bubble.yPosition) {
						updateScore(this.score, this.labelScore);

						TileEntityLaptop laptop = new TileEntityLaptop();
						laptop.setPos(this.application.getLaptopPositon());
						BlockPos pos = laptop.getPos();

						World world = laptop.getWorld();
						// final EntityPlayer player =
						// world.getClosestPlayer(pos.getX(), pos.getY(),
						// pos.getZ(), 3l, false);

						// world.playSound(null, pos, SoundHandler.BUBBLE_POP,
						// SoundCategory.BLOCKS,
						// 0.5f, 1.0f);

						respawn(hydrogen_bubble, width, height);
					}
				}
			}
		} else {
			this.score = -4;
		}
	}
	
	boolean fire_ball = false;
	boolean bomb = false;
	@Override
	public void handleKeyTyped(char character, int code) {
		//System.out.println(character + " | " + code);
		if (character == '1' && code == 2) {
			fire_ball = true;
			bomb = false;
		}
		if (character == '2' && code == 3) {
			fire_ball = false;
			bomb = true;
		}
		if (character == '3' && code == 4) {
			fire_ball = false;
			bomb = false;
		}
		super.handleKeyTyped(character, code);
	}

	public void updateScore(long score, Label label) {
		score++;
		label.setText("Score: " + this.score);

		Color LIGHT_GRAY = new Color(128, 128, 128); // Level: 1 | score < 500
		Color GREEN = new Color(0, 255, 0); // Level: 2 | score > 500
		Color YELLOW = new Color(255, 255, 0); // Level: 3 | score > 1000
		Color ORANGE = new Color(255, 190, 0); // Level: 4 | score > 1500
		Color RED = new Color(255, 0, 0); // Level: 5 | score > 2000
		Color DARK_RED = new Color(127, 0, 0); // Level: 6 | score > 2500
		Color MAGENTA = new Color(255, 0, 220); // Level: 7 | score > 5000
		Color PURPLE = new Color(178, 0, 255); // Level: 8 | score > 10000
		Color CYAN = new Color(0, 148, 255); // Level: 9 | score > 15000
		Color DARK_BLUE = new Color(0, 38, 255); // Level: 10 | score > 20000

		label.setTextColour(score > 25000 ? DARK_BLUE
				: score > 20000 ? CYAN
						: score > 15000 ? PURPLE
								: score > 10000 ? MAGENTA
										: score > 5000 ? DARK_RED
												: score > 2000 ? RED
														: score > 1500 ? ORANGE
																: score > 1000 ? YELLOW
																		: score > 500 ? GREEN : LIGHT_GRAY);

		this.score = score;
		this.labelScore = label;
	}

	public void respawn(Sprite hydrogen_bubble, int width, int height) {
		ResourceLocation sprite = new ResourceLocation(Reference.MODID, "textures/sprites/hydrogen_bubble.png");
		if (fire_ball)
			sprite = new ResourceLocation(Reference.MODID, "textures/sprites/fire_ball.png");
		if (bomb)
			sprite = new ResourceLocation(Reference.MODID, "textures/sprites/bomb.png");
		hydrogen_bubble.setSprite(sprite);
		
		int x = randInt(width - 40, width + 50);
		hydrogen_bubble.xPosition = x; // 160; // x; // 250; // x;
		hydrogen_bubble.yPosition = ((height / 2) - 4) + randInt(-10, 10);
	}

	public static int randInt(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
}
