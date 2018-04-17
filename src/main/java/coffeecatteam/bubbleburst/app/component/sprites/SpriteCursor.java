package coffeecatteam.bubbleburst.app.component.sprites;

import org.lwjgl.util.vector.Vector3f;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.core.Laptop;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteCursor extends Sprite {

	private static final ResourceLocation FULL = new ResourceLocation(Reference.MODID,
			"textures/app/sprites/cursor/cursor_full.png");
	private static final ResourceLocation HALF = new ResourceLocation(Reference.MODID,
			"textures/app/sprites/cursor/cursor_half.png");
	private static final ResourceLocation EMPTY = new ResourceLocation(Reference.MODID,
			"textures/app/sprites/cursor/cursor_empty.png");

	private int mouseX;
	private int mouseY;

	public SpriteCursor(int x, int y) {
		super(x, y, FULL);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		// What sprite to use at what time
		LayoutGame layoutGame = (LayoutGame) layout;
		int gameTime = layoutGame.getCurrentGameTime();
		int maxGameTime = layoutGame.getMaxGameTime();

		if (gameTime > maxGameTime / 2) {
			this.setSprite(FULL);
		} else if (gameTime <= maxGameTime / 2 && gameTime > maxGameTime / 4) {
			this.setSprite(HALF);
		} else if (gameTime <= maxGameTime / 4) {
			this.setSprite(EMPTY);
		}

		// Cursor movement
		int offset = 4;

//		this.mouseX = (this.mouseX >= layoutGame.width) ? layoutGame.width : this.mouseX;
//		this.mouseY = (this.mouseY >= layoutGame.height) ? layoutGame.height : this.mouseY;
		
		this.xPosition = mouseX - offset;
		this.yPosition = mouseY - offset;

		if (this.xPosition < 110)
			this.xPosition = 110;
		else if (this.xPosition > 305)
			this.xPosition = 305;
		if (this.yPosition < 65)
			this.yPosition = 65;
		else if (this.yPosition > 160)
			this.yPosition = 160;
	}

	@Override
	protected void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive,
			float partialTicks) {
		super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
}
