package coffeecatteam.bubbleburst.app.component.sprites;

import java.util.Random;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.util.handlers.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteBubble extends SpriteObj {

	public static final ResourceLocation[] HYDROGEN_BUBBLE = getAnims("hydrogen_bubble", 2);
	public static final ResourceLocation[] FIRE_BALL = getAnims("fire_ball", 2);

	public SpriteBubble(int x, int y, int scoreIncrease, ApplicationGame application) {
		super(x, y, 8, getRandomAnim(HYDROGEN_BUBBLE), application);
		setScoreIncrease((long) scoreIncrease);
		setLength(10);
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
			for (ResourceLocation sprite : FIRE_BALL) {
				if (this.getSprite() != sprite) {
					layoutGame.updateScore(layoutGame.getScore(), getScoreIncrease(), layoutGame.labelScore);
	
					mc.player.playSound(SoundHandler.BUBBLE_POP, getVolume(), getPitch());
	
					this.setCanMove(false);
				}
			}
		}

		if (!this.canMove()) {
			super.update(app, layoutGame, mc);
			if (pointer < getLength()) {
				this.setSprite(getRandomAnim(FIRE_BALL));
			} else {
				if (pointer >= getLength())
					pointer = 0;
				this.setSprite(getRandomAnim(HYDROGEN_BUBBLE));
				this.setCanMove(true);
				layoutGame.respawn(this, layoutGame.width, layoutGame.height);
			}
		}
	}
}
