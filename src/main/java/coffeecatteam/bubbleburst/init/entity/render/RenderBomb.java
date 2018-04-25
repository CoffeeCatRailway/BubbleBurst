package coffeecatteam.bubbleburst.init.entity.render;

import coffeecatteam.bubbleburst.init.InitItem;
import coffeecatteam.bubbleburst.init.entity.EntityBomb;
import coffeecatteam.bubbleburst.init.entity.model.ModelBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderBomb extends Render<EntityBomb> {

	private final ModelBomb model = new ModelBomb();

	protected RenderBomb(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityBomb entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(
				(float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX,
				1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		Minecraft mc = Minecraft.getMinecraft();
		mc.getRenderItem().renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public ItemStack getStackToRender(EntityBomb entityIn) {
		return new ItemStack(InitItem.bomb);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBomb entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
