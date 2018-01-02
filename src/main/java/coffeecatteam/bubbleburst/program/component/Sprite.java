package coffeecatteam.bubbleburst.program.component;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.core.Laptop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatTeam
 */
public class Sprite extends Component {

	private final int MAX_PROGRESS = 8 * (8 ^ 100);
	private int currentProgress = 0;

	private Color color = Color.WHITE;

	private ResourceLocation sprite;
	private int textureWidth;
	private int textureHeight;

	private float scale = 1.0f;
	
	private boolean canMove = true;
	private boolean canTouch = true;
	
	/**
	 * Creates a sprite (animated image) with ResourceLocation sprite.
	 * 
	 * @param left
	 * @param top
	 * @param sprite
	 */
	public Sprite(int left, int top, ResourceLocation sprite) {
		this(left, top, sprite, 64, 32);
	}

	/**
	 * Creates a sprite (animated image) with ResourceLocation sprite and sets
	 * the width and height of the texture.
	 * 
	 * @param left
	 * @param top
	 * @param sprite
	 * @param textureWidth
	 * @param textureHeight
	 */
	public Sprite(int left, int top, ResourceLocation sprite, int textureWidth, int textureHeight) {
		super(left, top);
		this.sprite = sprite;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}
	
	public ResourceLocation getSprite() {
		return sprite;
	}

	/**
	 * Sets the scale of the sprite.
	 * 
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	
	public boolean canMove() {
		return canMove;
	}
	
	public void setCanTouch(boolean canTouch) {
		this.canTouch = canTouch;
	}
	
	public boolean canTouch() {
		return canTouch;
	}

	/**
	 * Checks if this sprite is touching another.
	 * 
	 * @param sprite
	 */
	public boolean isTouching(Sprite sprite) {
		int offset = this.textureWidth / 8;
		return (this.xPosition + offset > sprite.xPosition && this.xPosition - offset < sprite.xPosition)&&(this.yPosition + offset > sprite.yPosition && this.yPosition - offset < sprite.yPosition)&&this.canTouch();
		//		if (this.xPosition + offset > sprite.xPosition && this.xPosition - offset < sprite.xPosition) {
//			if (this.yPosition + offset > sprite.yPosition && this.yPosition - offset < sprite.yPosition) {
//				return this.canTouch() && true;
//			}
//		}
//		return false;
	}
	
	public void setTextureWidth(int textureWidth) {
		this.textureWidth = textureWidth;
	}
	
	public int getTextureWidth() {
		return textureWidth;
	}
	
	public void setTextureHeight(int textureHeight) {
		this.textureHeight = textureHeight;
	}
	
	public int getTextureHeight() {
		return textureHeight;
	}

	/**
	 * Sets the texture of the sprite.
	 * 
	 * @param resourceLocation
	 */
	public void setSprite(ResourceLocation resourceLocation) {
		this.sprite = resourceLocation;
	}

	@Override
	public void handleTick() {
		if (currentProgress >= MAX_PROGRESS) {
			currentProgress = 0;
		}
		currentProgress++;
	}

	@Override
	protected void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive,
			float partialTicks) {
		if (this.visible) {
			GlStateManager.pushMatrix();
			{
				GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F,
						color.getAlpha() / 255F);
				
				//GlStateManager.scale(scale, scale, 0);
				
				mc.getTextureManager().bindTexture(sprite);
				drawModalRectWithCustomSizedTexture(xPosition, yPosition, (currentProgress % 8) * 16,
						16 + 16 * (int) Math.floor((double) currentProgress / 8), 16, 16,
						textureWidth, textureHeight);

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			GlStateManager.popMatrix();
		}
	}
}
