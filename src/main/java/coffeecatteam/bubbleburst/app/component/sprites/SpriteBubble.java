package coffeecatteam.bubbleburst.app.component.sprites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.util.handlers.AnimationHandler;
import coffeecatteam.bubbleburst.util.handlers.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteBubble extends SpriteObj {

	public static final ResourceLocation HYDROGEN_BUBBLE = new ResourceLocation(Reference.MODID, "textures/app/sprites/bubble/normal.png");
	public static final ResourceLocation SUPER_BUBBLE = new ResourceLocation(Reference.MODID, "textures/app/sprites/bubble/super.png");
	
	public static final List<ResourceLocation> FIRE_BALL = AnimationHandler.FIRE_BALL;

	public SpriteBubble(int x, int y, int scoreIncrease, ApplicationGame application) {
		super(x, y, 8, HYDROGEN_BUBBLE, application);
		setScoreIncrease((long) scoreIncrease);
		setLength(10);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		LayoutGame layoutGame = (LayoutGame) layout;
		boolean isSuper = this.getSprite().equals(SUPER_BUBBLE);
		this.speed = 5 + layoutGame.randInt(0, 3) + ((isSuper) ? 2 : 0);

		if (this.canMove())
			this.yPosition += this.speed;
		if (this.yPosition > layoutGame.height * 2 + 10)
			layoutGame.respawn(this, layoutGame.width, layoutGame.height);

		// Check if cursor is touching a bubble
		if (layoutGame.cursor.isTouching(this)) {
			if (isSuper) {
				layoutGame.updateScore(layoutGame.getScore(), getScoreIncrease() * 5, layoutGame.labelScore);
				mc.player.playSound(SoundHandler.BUBBLE_POP, getVolume() * 10f, getPitch());
				this.setCanMove(false);
			} else if (this.getSprite().equals(HYDROGEN_BUBBLE)) {
				layoutGame.updateScore(layoutGame.getScore(), getScoreIncrease(), layoutGame.labelScore);
				mc.player.playSound(SoundHandler.BUBBLE_POP, getVolume(), getPitch());
				this.setCanMove(false);
			}
		}

		// Check for movement
		if (!this.canMove()) {
			super.update(app, layoutGame, mc);
			if (pointer < getLength()) {
				this.setSprite(AnimationHandler.getRandomAnimation(FIRE_BALL));
			} else {
				if (pointer >= getLength())
					pointer = 0;
				this.setSprite(isSuperBubble());
				this.setCanMove(true);
				layoutGame.respawn(this, layoutGame.width, layoutGame.height);
			}
		}
	}
	
	private ResourceLocation isSuperBubble() {
		int v = new Random().nextInt(100)+1;
		return (v <= 5) ? SUPER_BUBBLE : HYDROGEN_BUBBLE;
	}
}
