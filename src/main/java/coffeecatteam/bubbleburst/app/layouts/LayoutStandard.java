package coffeecatteam.bubbleburst.app.layouts;

import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import net.minecraft.nbt.NBTTagCompound;

public abstract class LayoutStandard extends Layout {
	
	protected ApplicationGame application;

	public LayoutStandard(int width, int height, ApplicationGame application) {
		super(width, height);
		this.application = application;
	}
	
	@Override
	public void init() {
		this.components.clear();
		init(this);
		super.init();
	}
	
	public abstract void init(Layout layout);
	
	public abstract void onTick();
	
	public abstract void load(NBTTagCompound nbt);
	public abstract void save(NBTTagCompound nbt);
}
