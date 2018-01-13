package coffeecatteam.bubbleburst.app.component.sprites;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.core.Laptop;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.utill.SoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

public class SpriteHydrogenBall extends SpriteObj {

	private static final ResourceLocation HYDROGEN_BUBBLE = new ResourceLocation(Reference.MODID, "textures/app/sprites/hydrogen_bubble.png");
	private static final ResourceLocation FIRE_BALL1 = new ResourceLocation(Reference.MODID, "textures/app/sprites/fire_ball1.png");
	private static final ResourceLocation FIRE_BALL2 = new ResourceLocation(Reference.MODID, "textures/app/sprites/fire_ball2.png");

	public SpriteHydrogenBall(int x, int y, int scoreIncrease) {
		super(x, y, 8, HYDROGEN_BUBBLE);
		setScoreIncrease((long) scoreIncrease);
		setLength(10);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		LayoutGame layoutGame = (LayoutGame) layout;
		this.speed = 5 + layoutGame.randInt(0, 3);

		if (this.canMove())
			this.yPosition += this.speed;
		if (this.yPosition > layoutGame.height * 2)
			layoutGame.respawn(this, layoutGame.width, layoutGame.height);

		// Check if cursor is touching a hydrogen bubble
		if (layoutGame.cursor.isTouching(this)) {
			if (!(this.getSprite().equals(FIRE_BALL1) || this.getSprite().equals(FIRE_BALL2))) {
				layoutGame.updateScore(layoutGame.getScore(), getScoreIncrease(), layoutGame.labelScore);

				mc.player.playSound(SoundHandler.BUBBLE_POP, 1.0f, (0.5f + new Random().nextFloat()) * 1.5f);

				this.setCanMove(false);
			}
		}

		if (!this.canMove()) {
			super.update(app, layout, mc);
			if (pointer < getLength()) {
				if (layoutGame.randInt(0, 10) <= 2)
					this.setSprite(FIRE_BALL2);
				else
					this.setSprite(FIRE_BALL1);
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
