package coffeecatteam.bubbleburst.app.layouts;

import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Image;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class LayoutStandard extends Layout {

	protected static final ResourceLocation BG_DEFAULT = new ResourceLocation(Reference.MODID, "textures/app/backgrounds/default.png");
	protected static final ResourceLocation BG_DESERT = new ResourceLocation(Reference.MODID, "textures/app/backgrounds/desert.png");
	private ResourceLocation currentBackground;
	private boolean hasBackground;
	private Image background;

	protected ApplicationGame application;
	protected LayoutSettings settings;

	public LayoutStandard(int width, int height, ApplicationGame application) {
		this(width, height, application, false);
	}

	public LayoutStandard(int width, int height, ApplicationGame application, boolean hasBackground) {
		super(width, height);
		this.application = application;
		this.settings = this.application.getLayoutSettings();
		this.hasBackground = hasBackground;
		this.currentBackground = BG_DEFAULT;
	}
	
	protected void setBackground(ResourceLocation currentBackground) {
		this.currentBackground = currentBackground;
		this.background.setImage(currentBackground);
	}
	
	protected ResourceLocation getBackground() {
		return currentBackground;
	}

	@Override
	public void init() {
		this.components.clear();

		if (this.hasBackground) {
			this.background = new Image(0, 0, this.width, this.height, 0, 0,
					256, 255, currentBackground);
			super.addComponent(this.background);
		}

		init(this);
		super.init();
	}

	public abstract void init(Layout layout);

	public abstract void onTick();
}
