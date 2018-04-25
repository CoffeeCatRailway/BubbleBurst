package coffeecatteam.bubbleburst.init.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBomb extends ModelBase {
    public ModelRenderer renderer;

    public ModelBomb() {
        this.textureWidth = 16;
        this.textureHeight = 16;

        this.renderer = new ModelRenderer(this, 0, 0);
        this.renderer.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.renderer.addBox(0.0F, 0.0F, 0.0F, 16, 16, 0);
        this.renderer.addBox(8.0F, 0.0F, 0.0F, 16, 16, 0);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
        this.renderer.render(scale);        
    }
}
