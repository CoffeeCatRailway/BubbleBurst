package coffeecatteam.bubble_burst.program;

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
import coffeecatteam.bubble_burst.program.layouts.LayoutGame;
import coffeecatteam.bubble_burst.program.layouts.LayoutInstructions;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ApplicationGame extends Application {

	// Start Menu
	private Button buttonStart;
	private Button buttonInstructions;

	// Layouts
	private LayoutGame layoutGame;
	private LayoutInstructions layoutInstructions;

	// Game

	public ApplicationGame() {
		this.setDefaultWidth(95);
		this.setDefaultHeight(55);
	}

	public void init() {
		this.layoutGame = new LayoutGame(200, 100, this);
		this.layoutInstructions = new LayoutInstructions(200, 120, this);

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

		// Game
		
	}

	@Override
	public void onTick() {
		this.layoutGame.onTick();
		super.onTick();
	}

	public void load(NBTTagCompound nbt) {
	}

	public void save(NBTTagCompound nbt) {
	}
}
