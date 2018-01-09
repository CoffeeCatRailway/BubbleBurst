package coffeecatteam.bubbleburst.init.entity.render;

import java.util.List;

import org.lwjgl.opengl.GL11;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.init.ItemInit;
import coffeecatteam.bubbleburst.init.entity.EntityBomb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RenderBomb extends Render<EntityBomb> {
	
	public RenderBomb(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.25F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBomb entity) {
		return new ResourceLocation(Reference.MODID, "textures/items/bomb.png");
	}

	@Override
	public void doRender(EntityBomb entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(180F, 0, 1, 0);
			GlStateManager.rotate(entityYaw, 0, 1, 0);
			GlStateManager.rotate(entity.rotationPitch, 1, 0, 0);
			GlStateManager.translate(-0.5, 0, -0.5);

			this.bindTexture(new ResourceLocation(Reference.MODID, "textures/items/bomb.png"));

			GlStateManager.disableLighting();

			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
					.getItemModel(new ItemStack(ItemInit.bomb));
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);

			for (EnumFacing enumfacing : EnumFacing.values()) {
				this.renderQuads(buffer, model.getQuads((IBlockState) null, enumfacing, 0L));
			}

			this.renderQuads(buffer, model.getQuads((IBlockState) null, (EnumFacing) null, 0L));
			tessellator.draw();
		}
		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	private void renderQuads(BufferBuilder buffer, List<BakedQuad> quads) {
		int i = 0;
		for (int j = quads.size(); i < j; ++i) {
			BakedQuad quad = quads.get(i);
			net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(buffer, quad, -1);
		}
	}
}
