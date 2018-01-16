package coffeecatteam.bubbleburst.app;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteCursor;
import coffeecatteam.bubbleburst.app.layouts.LayoutSettings;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGameScores;
import coffeecatteam.bubbleburst.app.layouts.instructions.LayoutLevelsScore;
import coffeecatteam.bubbleburst.app.layouts.instructions.LayoutInstructions;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ApplicationGame extends Application {

	// Start Menu
	private Button buttonStart;
	private Button buttonInstructions;
	private Button buttonSettings;

	private Label labelVersion;

	// Layouts
	private LayoutGame layoutGame;
	private LayoutGameScores layoutGameScores;

	private LayoutInstructions layoutInstructions;
	private LayoutLevelsScore layoutLevelsScore;

	private LayoutSettings layoutSettings;

	// Game
	private long topScore;
	private long topBombCount;

	public static boolean DEBUG = false;

	// Settings
	private int bombsAmount;
	private int bubblesAmount;

	private float gameVolume;

	public ApplicationGame() {
		this.setDefaultWidth(95);
		this.setDefaultHeight(85);
	}

	public int getBombsAmount() {
		return bombsAmount;
	}

	public void setBombsAmount(int bombsAmount) {
		this.bombsAmount = bombsAmount;
	}

	public int getBubblesAmount() {
		return bubblesAmount;
	}

	public void setBubblesAmount(int bubblesAmount) {
		this.bubblesAmount = bubblesAmount;
	}

	public float getGameVolume() {
		return gameVolume;
	}

	public void setGameVolume(float gameVolume) {
		this.gameVolume = gameVolume;
	}

	public void init() {
		// Layouts
		this.layoutGame = new LayoutGame(200, 100, this);
		this.layoutGameScores = new LayoutGameScores(200, 100, this);

		this.layoutInstructions = new LayoutInstructions(260, 125, this);
		this.layoutLevelsScore = new LayoutLevelsScore(237, 115, this);

		this.layoutSettings = new LayoutSettings(260, 105, this);

		// Start Menu
		this.buttonStart = new Button(5, 5, "Start", Icons.PLAY);
		this.buttonStart.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.layoutGame.init();
				this.setCurrentLayout(this.layoutGame);
			}
		});
		super.addComponent(this.buttonStart);

		this.buttonInstructions = new Button(5, 50, "Instructions", Icons.INFO);
		this.buttonInstructions.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.setCurrentLayout(this.layoutInstructions);
			}
		});
		super.addComponent(this.buttonInstructions);

		this.buttonSettings = new Button(5, 28, "Settings", Icons.WRENCH);
		this.buttonSettings.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.layoutSettings.init();
				this.setCurrentLayout(this.layoutSettings);
			}
		});
		super.addComponent(this.buttonSettings);

		this.labelVersion = new Label("Version: " + this.getInfo().getVersion(), 5, 73);
		super.addComponent(this.labelVersion);

		int fsp_x = 55;
		int fsp_y = 5;
		Sprite fire_stick = new SpriteCursor(fsp_x, fsp_y);
		// fire_stick.setScale(1.05);
		super.addComponent(fire_stick);

		// Player's face
		ResourceLocation p_skin = Minecraft.getMinecraft().player.getLocationSkin();
		int px = fsp_x + 20;
		int py = fsp_y;
		int psize = 16;

		Image p_face = new Image(px, py, psize, psize, 32, 32, 32, 32, p_skin);
		super.addComponent(p_face);
		p_face = new Image(px - 1, py - 1, psize + 2, psize + 2, 160, 32, 32, 32, p_skin);
		super.addComponent(p_face);
	}
	public LayoutGame getLayoutGame() {
		return layoutGame;
	}

	public LayoutGameScores getLayoutGameScores() {
		return layoutGameScores;
	}

	public LayoutInstructions getLayoutInstructions() {
		return layoutInstructions;
	}

	public LayoutLevelsScore getLayoutLevelsScore() {
		return layoutLevelsScore;
	}

	public LayoutSettings getLayoutSettings() {
		return layoutSettings;
	}

	public void setLayout(Layout layout) {
		this.setCurrentLayout(layout);
	}

	public Layout getLayout() {
		return this.getCurrentLayout();
	}

	// Game
	public long getTopScore() {
		return topScore;
	}

	public void setTopScore(long topScore) {
		this.topScore = topScore;
	}

	public long getTopBombCount() {
		return topBombCount;
	}

	public void setTopBombCount(long topBombCount) {
		this.topBombCount = topBombCount;
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public void onTick() {
		this.layoutGame.onTick();
		this.layoutGameScores.onTick();

		this.layoutInstructions.onTick();
		this.layoutLevelsScore.onTick();

		this.layoutSettings.onTick();

		super.onTick();
	}

	@Override
	public void onClose() {
		this.setTopScore(this.layoutGame.getScore());
		this.setTopBombCount(this.layoutGame.getBombCount());

		// Settings
		this.setBombsAmount(this.layoutSettings.getBombsAmount());
		super.onClose();
	}

	public void load(NBTTagCompound nbt) {
		this.topScore = nbt.getLong("topScore");
		this.topBombCount = nbt.getLong("topBombCount");

		// Settings
		this.bombsAmount = nbt.getInteger("bombsAmount");
		this.bubblesAmount = nbt.getInteger("bubblesAmount");

		this.gameVolume = nbt.getFloat("gameVolume");
	}

	public void save(NBTTagCompound nbt) {
		nbt.setLong("topScore", this.getTopScore());
		nbt.setLong("topBombCount", this.getTopBombCount());

		// Settings
		nbt.setInteger("bombsAmount", this.bombsAmount);
		nbt.setInteger("bubblesAmount", this.bubblesAmount);

		nbt.setFloat("gameVolume", this.gameVolume);
	}
}
