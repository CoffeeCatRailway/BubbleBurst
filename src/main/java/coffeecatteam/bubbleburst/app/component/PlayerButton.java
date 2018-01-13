package coffeecatteam.bubbleburst.app.component;

import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.core.Laptop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Adds a simple button that displays a player's face layers.
 * 
 * @author Ocelot5836
 */
public class PlayerButton extends Button {

	private EntityPlayerSP player;

	/**
	 * Creates a new button with the default client player.
	 * 
	 * @param left
	 *            how many pixels from the left
	 * @param top
	 *            how many pixels from the top
	 */
	public PlayerButton(int left, int top) {
		this(left, top, Minecraft.getMinecraft().player);
	}

	/**
	 * Creates a new button with the specified player.
	 * 
	 * @param left
	 *            how many pixels from the left
	 * @param top
	 *            how many pixels from the top
	 * @param player
	 *            The player skin used to render the head
	 */
	public PlayerButton(int left, int top, EntityPlayerSP player) {
		super(left, top, 14, 14, "");
		this.player = player;
	}

	@Override
	public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
		super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(player.getLocationSkin());
		Gui.drawModalRectWithCustomSizedTexture(x + left + 3, y + top + 3, 8, 8, 8, 8, 64, 64);
		Gui.drawModalRectWithCustomSizedTexture(x + left + 3, y + top + 3, 40, 8, 8, 8, 64, 64);
	}
}