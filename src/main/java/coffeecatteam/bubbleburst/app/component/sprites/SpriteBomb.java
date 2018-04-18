package coffeecatteam.bubbleburst.app.component.sprites;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.util.handlers.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SpriteBomb extends SpriteObj {

	public static final ResourceLocation[] BOMB = getAnims("bomb", 2);
	private static ResourceLocation C_BOMB;
	public static final ResourceLocation EXPLOSION = new ResourceLocation(Reference.MODID, "textures/app/sprites/explosion.png");
	
	public SpriteBomb(int x, int y, int scoreIncrease, ApplicationGame application) {
		super(x, y, 8, getRandomAnim(BOMB), application, 64, 64);
		setScoreIncrease((long) scoreIncrease);
		setLength(5);
		C_BOMB = getRandomAnim(BOMB);
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
			if (!(this.getSprite().equals(EXPLOSION))) {
				layoutGame.updateScore(layoutGame.getScore(), -getScoreIncrease(), layoutGame.labelScore);
				layoutGame.updateBombCount(layoutGame.getBombCount(), 1, layoutGame.labelBombCount);

				float v = this.application.getGameVolume()-0.3f;
				setVolume(v < 0.0f ? 0.0f : v <= 0.3f ? v += ((0.1f+v)*0.5f) : v);
				SoundEvent sound = (layoutGame.randInt(0, 10) < 2) ? SoundHandler.BOMB_1 : SoundHandler.BOMB_2;
				mc.player.playSound(sound, getVolume(), getPitch());

				this.setCanMove(false);
			}
		}

		if (!this.canMove()) {
			super.update(app, layoutGame, mc);
			if (pointer < getLength()) {
				this.setTextureHeight(32);
				this.setSprite(EXPLOSION);
				C_BOMB = getRandomAnim(BOMB);
			} else {
				if (pointer >= getLength())
					pointer = 0;
				this.setTextureHeight(64);
				this.setSprite(C_BOMB);
				this.setCanMove(true);
				layoutGame.respawn(this, layoutGame.width, layoutGame.height);
			}
		}
	}
}
