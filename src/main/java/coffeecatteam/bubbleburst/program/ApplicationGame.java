package coffeecatteam.bubbleburst.program;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.program.component.Sprite;
import coffeecatteam.bubbleburst.program.layouts.LayoutGame;
import coffeecatteam.bubbleburst.program.layouts.instructions.LayoutInstructions;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ApplicationGame extends Application {

	// Start Menu
	private Button buttonStart;
	private Button buttonInstructions;

	private Label labelVersion;
	private Sprite fire_stick;

	// Layouts
	private LayoutGame layoutGame;
	private LayoutInstructions layoutInstructions;

	// Game
	private long topScore;
	public boolean scoreInit = false;

	public ApplicationGame() {
		this.setDefaultWidth(95);
		this.setDefaultHeight(65);
	}

	public long getTopScore() {
		return topScore;
	}

	public void setTopScore(long topScore) {
		this.topScore = topScore;
	}

	public void init() {
		this.layoutGame = new LayoutGame(200, 100, this);
		this.layoutInstructions = new LayoutInstructions(260, 95, this);

		// Start Menu
		this.buttonStart = new Button(5, 5, "Start", Icons.PLAY);
		this.buttonStart.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.setCurrentLayout(this.layoutGame);
			}
		});
		super.addComponent(this.buttonStart);

		this.buttonInstructions = new Button(5, 30, "Instructions", Icons.INFO);
		this.buttonInstructions.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.setCurrentLayout(this.layoutInstructions);
			}
		});
		super.addComponent(this.buttonInstructions);

		this.labelVersion = new Label("Version: " + Reference.VERSION, 5, 53);
		super.addComponent(this.labelVersion);

		this.fire_stick = new Sprite(60, 5, new ResourceLocation(Reference.MODID, "textures/sprites/cursor.png"));
		this.fire_stick.setScale(1.5f);
		super.addComponent(this.fire_stick);
	}

	public LayoutInstructions getLayoutInstructions() {
		return layoutInstructions;
	}

	public void setLayout(Layout layout) {
		this.setCurrentLayout(layout);
	}

	public Layout getLayout() {
		return this.getCurrentLayout();
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	@Override
	public void onTick() {
		this.layoutGame.onTick();
		this.layoutInstructions.onTick();
		super.onTick();
	}

	@Override
	public void onClose() {
		this.setTopScore(this.layoutGame.getScore());
		super.onClose();
	}

	public void load(NBTTagCompound nbt) {
		this.topScore = nbt.getLong("topScore");
	}

	public void save(NBTTagCompound nbt) {
		nbt.setLong("topScore", this.getTopScore());
	}
}
