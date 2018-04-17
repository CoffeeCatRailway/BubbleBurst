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

public class SpriteHydrogenBall extends SpriteObj {

	private static final ResourceLocation HYDROGEN_BUBBLE = new ResourceLocation(Reference.MODID, "textures/app/sprites/hydrogen_bubble.png");
	private static final ResourceLocation FIRE_BALL1 = new ResourceLocation(Reference.MODID, "textures/app/sprites/fire_ball1.png");
	private static final ResourceLocation FIRE_BALL2 = new ResourceLocation(Reference.MODID, "textures/app/sprites/fire_ball2.png");

	public SpriteHydrogenBall(int x, int y, int scoreIncrease, ApplicationGame application) {
		super(x, y, 8, HYDROGEN_BUBBLE, application);
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
			if (!(this.getSprite().equals(FIRE_BALL1) || this.getSprite().equals(FIRE_BALL2))) {
				layoutGame.updateScore(layoutGame.getScore(), getScoreIncrease(), layoutGame.labelScore);

				mc.player.playSound(SoundHandler.BUBBLE_POP, getVolume(), getPitch());

				this.setCanMove(false);
			}
		}

		if (!this.canMove()) {
			super.update(app, layoutGame, mc);
			if (pointer < getLength()) {
				this.setSprite((layoutGame.randInt(0, 10) <= 2) ? FIRE_BALL2 : FIRE_BALL1);
			} else {
				if (pointer >= getLength())
					pointer = 0;
				this.setSprite(HYDROGEN_BUBBLE);
				this.setCanMove(true);
				layoutGame.respawn(this, layoutGame.width, layoutGame.height);
			}
		}
	}
}
