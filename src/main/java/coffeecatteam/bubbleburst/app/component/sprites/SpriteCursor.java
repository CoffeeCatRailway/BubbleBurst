package coffeecatteam.bubbleburst.app.component.sprites;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteCursor extends Sprite {
	
	private static final ResourceLocation FULL = new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/cursor_full.png");
	private static final ResourceLocation HALF = new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/cursor_half.png");
	private static final ResourceLocation EMPTY = new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/cursor_empty.png");

	public SpriteCursor(int x, int y) {
		super(x, y, FULL);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		LayoutGame layoutGame = (LayoutGame) layout;
		int gameTime = layoutGame.getCurrentGameTime();
		int maxGameTime = layoutGame.getMaxGameTime();
		
		if (gameTime > maxGameTime/2) {
			this.setSprite(FULL);
		} else if (gameTime <= maxGameTime/2 && gameTime > maxGameTime/4) {
			this.setSprite(HALF);
		} else if (gameTime <= maxGameTime/4) {
			this.setSprite(EMPTY);
		}
	}
}
