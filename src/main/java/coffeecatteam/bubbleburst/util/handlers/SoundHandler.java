package coffeecatteam.bubbleburst.util.handlers;

import java.util.ArrayList;
import java.util.List;

import coffeecatteam.bubbleburst.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {

	public static final SoundEvent BUBBLE_POP = registerSound(Reference.MODID + ":bubble_pop");
	
	public static final SoundEvent BOMB_1 = registerSound(Reference.MODID + ":bomb_1");
	public static final SoundEvent BOMB_2 = registerSound(Reference.MODID + ":bomb_2");
	
	public static final SoundEvent SPLAT = registerSound(Reference.MODID + ":splat");

	private static SoundEvent registerSound(String soundNameIn) {
		ResourceLocation resource = new ResourceLocation(soundNameIn);
		SoundEvent sound = new SoundEvent(resource).setRegistryName(soundNameIn);
		return sound;
	}

	@EventBusSubscriber(modid = Reference.MODID)
	public static class SoundRegistration {

		private static SoundEvent[] sounds = { BUBBLE_POP, BOMB_1, BOMB_2, SPLAT };

		@SubscribeEvent
		public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(sounds);
		}
	}
}
