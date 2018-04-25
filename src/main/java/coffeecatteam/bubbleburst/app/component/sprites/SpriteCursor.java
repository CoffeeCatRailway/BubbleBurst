package coffeecatteam.bubbleburst.app.component.sprites;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.core.Laptop;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteCursor extends Sprite {

	private static ResourceLocation FULL = new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/default/full.png");
	private static ResourceLocation HALF = new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/default/half.png");
	private static ResourceLocation EMPTY = new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/default/empty.png");
	private static ResourceLocation CURRENT_SKIN = FULL;

	private int mouseX;
	private int mouseY;

	public SpriteCursor(int x, int y) {
		super(x, y, CURRENT_SKIN);
	}
	
	public static void setSkin(String skin) {
		String path = "textures/app/sprites/cursor/" + skin;
		FULL = new ResourceLocation(Reference.MODID, path + "/full.png");
		HALF = new ResourceLocation(Reference.MODID, path + "/half.png");
		EMPTY = new ResourceLocation(Reference.MODID, path + "/empty.png");
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		// What sprite to use at what time
		LayoutGame layoutGame = (LayoutGame) layout;
		int gameTime = layoutGame.getCurrentGameTime();
		int maxGameTime = layoutGame.getMaxGameTime();

		if (gameTime > maxGameTime / 2) {
			CURRENT_SKIN = FULL;
		} else if (gameTime <= maxGameTime / 2 && gameTime > maxGameTime / 4) {
			CURRENT_SKIN = HALF;
		} else if (gameTime <= maxGameTime / 4) {
			CURRENT_SKIN = EMPTY;
		}

		if (CURRENT_SKIN.getResourcePath().contains("laser_sword")) {
			setTextureWidth(128);
			setTextureHeight(64);
		} else {
			setTextureWidth(64);
			setTextureHeight(32);
		}
		this.setSprite(CURRENT_SKIN);

		// Cursor movement
		int offset = 4;

//		this.mouseX = (this.mouseX >= layoutGame.width) ? layoutGame.width : this.mouseX;
//		this.mouseY = (this.mouseY >= layoutGame.height) ? layoutGame.height : this.mouseY;
		
		this.xPosition = mouseX - offset;
		this.yPosition = mouseY - offset;

		if (this.xPosition < 30)
			this.xPosition = 30;
		else if (this.xPosition > layoutGame.width + 20)
			this.xPosition = layoutGame.width + 20;
		if (this.yPosition < 35)
			this.yPosition = 35;
		else if (this.yPosition > layoutGame.height + 25)
			this.yPosition = layoutGame.height + 25;
	}

	@Override
	protected void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive,
			float partialTicks) {
		super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
}
