package coffeecatteam.bubbleburst.app.component;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.core.Laptop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author CoffeeCatTeam
 */
public abstract class Sprite extends Component {

	private final int MAX_PROGRESS = 8 * (8 ^ 100);
	private int currentProgress = 0;

	private Color color = Color.WHITE;

	private ResourceLocation sprite;
	private int textureWidth;
	private int textureHeight;
	
	protected boolean canMove = true;
	protected boolean canTouch = true;
	
	protected int speed = 1;
	protected float scale = 1;
	
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
		this.xPosition = left;
		this.yPosition = top;
		this.sprite = sprite;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public ResourceLocation getSprite() {
		return sprite;
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
	
	public abstract void update(Application app, Layout layout, Minecraft mc);

	@Override
	protected void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive,
			float partialTicks) {
		if (this.visible) {
			GlStateManager.pushMatrix();
			{
				GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F,
						color.getAlpha() / 255F);
				
				GlStateManager.scale(scale, scale, 1);
				mc.getTextureManager().bindTexture(sprite);
				
				drawModalRectWithCustomSizedTexture(xPosition, yPosition, (float) ((currentProgress % 8) * 16*scale),
						(float) (16 + 16 * (int) Math.floor((double) currentProgress / 8)*scale), 16, 16,
						textureWidth, textureHeight);

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			GlStateManager.popMatrix();
		}
	}
}
