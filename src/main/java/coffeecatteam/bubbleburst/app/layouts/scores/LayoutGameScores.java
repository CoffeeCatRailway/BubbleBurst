package coffeecatteam.bubbleburst.app.layouts.scores;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class LayoutGameScores extends LayoutStandard {

	public LayoutGameScores(int width, int height, ApplicationGame application) {
		super(width, height, application);
	}

	@Override
	public void init(Layout layout) {
		
	}
	
	public void onTick() {
		
	}
	
	public void load(NBTTagCompound nbt) {
		
	}

	public void save(NBTTagCompound nbt) {
		
	}
}
