package coffeecatteam.bubble_burst.program.component;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.core.Laptop;

import net.minecraft.client.Minecraft;
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
	 * Creates a sprite (animated image) with ResourceLocation sprite and sets the width and height of the texture.
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
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F,
					color.getAlpha() / 255F);
			mc.getTextureManager().bindTexture(this.sprite);

			drawModalRectWithCustomSizedTexture(xPosition, yPosition, (currentProgress % 8) * 16,
					16 + 16 * (int) Math.floor((double) currentProgress / 8), 16, 16, this.textureWidth,
					this.textureHeight);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
