package coffeecatteam.bubbleburst.init;

import coffeecatteam.bubbleburst.BubbleBurst;
import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.init.entity.EntityBomb;
import coffeecatteam.bubbleburst.init.entity.render.RenderBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {

	public static void init() {
		registerEntity("bomb", EntityBomb.class, -1, 0, 1, true);
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, RenderBomb::new);
		
		EntityBomb.registerFixesBomb(Minecraft.getMinecraft().getDataFixer());
	}

	private static void registerEntity(String name, Class<? extends Entity> clazz, int id, int trackingRange,
			int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), clazz, name, id,
				BubbleBurst.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}
