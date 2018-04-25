package coffeecatteam.bubbleburst.init;

import coffeecatteam.bubbleburst.BubbleBurst;
import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.init.entity.EntityBomb;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class InitEntity {

	public static void init() {
		register("bomb", EntityBomb.class, -1, 1);
	}

	private static void register(String name, Class<? extends Entity> entity, int id, int trackingRange) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id,
				BubbleBurst.instance, trackingRange, 1, true);
	}

	private static void register(String name, Class<? extends Entity> entity, int id, int trackingRange, int eggPrimary,
			int eggSecondary) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id,
				BubbleBurst.instance, trackingRange, 1, true, eggPrimary, eggSecondary);
	}
}
