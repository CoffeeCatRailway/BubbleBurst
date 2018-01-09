package coffeecatteam.bubbleburst.init;

import java.util.HashSet;
import java.util.Set;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.init.items.ItemBase;
import coffeecatteam.bubbleburst.init.items.ItemBomb;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {

	public static Item fire_stick;
	public static Item bomb;

	public static void init() {
		fire_stick = new ItemBase("fire_stick", 1);
		bomb = new ItemBomb("bomb");
	}

	@EventBusSubscriber(modid = Reference.MODID)
	public static class ItemRegistrationHandler {
		public static final Set<Item> ITEM_LIST = new HashSet<>();

		private static final Set<Item> registeredItemList = new HashSet<>();
		private static final Item[] items = { fire_stick, bomb };

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			IForgeRegistry<Item> reg = event.getRegistry();

			for (Item item : items) {
				reg.register(item);
				ITEM_LIST.add(item);
			}
		}

		@SubscribeEvent
		public static void registerModels(final ModelRegistryEvent event) {
			for (Item item : ITEM_LIST)
				if (!registeredItemList.contains(item))
					registerItemModel(item);
		}

		private static void registerItemModel(final Item item) {
			final String registryName = item.getRegistryName().toString();
			final ModelResourceLocation location = new ModelResourceLocation(registryName, "inventory");
			registerItemModel(item, location);
		}

		private static void registerItemModel(final Item item, final ModelResourceLocation modelResourceLocation) {
			ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
			registeredItemList.add(item);
		}
	}
}
