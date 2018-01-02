package coffeecatteam.bubbleburst.utill;

import coffeecatteam.bubbleburst.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {

	public static SoundEvent BUBBLE_POP = registerSound(Reference.MODID + ":bubble_pop");
	public static SoundEvent BOMB_1 = registerSound(Reference.MODID + ":bomb_1");
	public static SoundEvent BOMB_2 = registerSound(Reference.MODID + ":bomb_2");

	private static SoundEvent registerSound(String soundNameIn) {
		ResourceLocation resource = new ResourceLocation(soundNameIn);
		SoundEvent sound = new SoundEvent(resource).setRegistryName(soundNameIn);
		return sound;
	}

	@EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().register(BUBBLE_POP);
		}
	}
}
