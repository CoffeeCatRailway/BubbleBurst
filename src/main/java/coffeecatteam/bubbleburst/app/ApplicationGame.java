package coffeecatteam.bubbleburst.app;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteCursor;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.app.layouts.game.instructions.InstructionsLevelsScore;
import coffeecatteam.bubbleburst.app.layouts.game.instructions.LayoutInstructions;
import coffeecatteam.bubbleburst.app.layouts.scores.LayoutGameScores;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

public class ApplicationGame extends Application {

	// Start Menu
	private Button buttonStart;
	private Button buttonInstructions;

	private Label labelVersion;

	// Layouts
	private LayoutGame layoutGame;
	private LayoutGameScores layoutGameScores;
	
	private LayoutInstructions layoutInstructions;
	private InstructionsLevelsScore layoutLevelsScore;
	

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
		// Layouts
		this.layoutGame = new LayoutGame(200, 100, this);
		this.layoutGameScores = new LayoutGameScores(200,  100, this);
		
		this.layoutInstructions = new LayoutInstructions(260, 105, this);
		this.layoutLevelsScore = new InstructionsLevelsScore(237, 115, this);
		

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

		Sprite fire_stick = new SpriteCursor(60, 5);
		//fire_stick.setScale(1.05);
		super.addComponent(fire_stick);
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
	
	public InstructionsLevelsScore getLayoutLevelsScore() {
		return layoutLevelsScore;
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
		this.layoutGameScores.onTick();
		
		this.layoutInstructions.onTick();
		this.layoutLevelsScore.onTick();
		
		super.onTick();
	}

	@Override
	public void onClose() {
		this.setTopScore(this.layoutGame.getScore());
		super.onClose();
	}

	public void load(NBTTagCompound nbt) {
		this.topScore = nbt.getLong("topScore");
		this.layoutGameScores.load(nbt);
	}

	public void save(NBTTagCompound nbt) {
		nbt.setLong("topScore", this.getTopScore());
		this.layoutGameScores.save(nbt);
	}
}
