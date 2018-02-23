package coffeecatteam.bubbleburst.app.component.sprites;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.utill.handlers.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteCheese extends SpriteObj {
	
	private static final ResourceLocation CHEESE_WHEEL = new ResourceLocation(Reference.MODID, "textures/app/sprites/cheese/cheese_wheel.png");
	private static final ResourceLocation CHEESE_SPLAT = new ResourceLocation(Reference.MODID, "textures/app/sprites/cheese/cheese_splat.png");
	
	public static boolean CURSOR_SLOW_DOWN = false;

	public SpriteCheese(int x, int y, ApplicationGame application) {
		super(x, y, 8, CHEESE_WHEEL, application);
		setLength(10);
		setMaxProgressOff(100);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		LayoutGame layoutGame = (LayoutGame) layout;
		this.speed = 5 + layoutGame.randInt(0, 3);

		if (this.canMove())
			this.yPosition += this.speed;
		if (this.yPosition > layoutGame.height * 2 + 10)
			layoutGame.respawn(this, layoutGame.width, layoutGame.height);

		// Check if cursor is touching a hydrogen bubble
		if (layoutGame.cursor.isTouching(this)) {
			if (!(this.getSprite().equals(CHEESE_SPLAT))) {
				mc.player.playSound(SoundHandler.SPLAT, getVolume(), getPitch());

				this.setCanMove(false);
			}
		}

		if (!this.canMove()) {
			super.update(app, layoutGame, mc);
			if (pointer < getLength()) {
				this.setSprite(CHEESE_SPLAT);
				
				CURSOR_SLOW_DOWN = true;
				this.xPosition = layoutGame.cursor.xPosition;
				this.yPosition = layoutGame.cursor.yPosition;
			} else {
				if (pointer >= getLength())
					pointer = 0;
				this.setSprite(CHEESE_WHEEL);
				CURSOR_SLOW_DOWN = false;
				this.setCanMove(true);
				layoutGame.respawn(this, layoutGame.width, layoutGame.height);
			}
		}
	}
}
