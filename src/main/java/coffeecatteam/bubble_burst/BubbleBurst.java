package coffeecatteam.bubble_burst;

import com.mrcrayfish.device.api.ApplicationManager;

import coffeecatteam.bubble_burst.program.ApplicationGame;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class BubbleBurst {

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MODID, "bubble_game"), ApplicationGame.class);
	}
}
