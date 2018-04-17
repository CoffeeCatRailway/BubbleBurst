package coffeecatteam.bubbleburst;

import org.apache.logging.log4j.Logger;

import com.mrcrayfish.device.api.ApplicationManager;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.init.ItemInit;
import coffeecatteam.bubbleburst.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.WORKING_MC_VERSION)
public class BubbleBurst {

	public static final CreativeTabs BUBBLEBURSTTAB = new TabBubbleBurst(Reference.MODID);

	public static Logger logger = Utils.getLogger();

	@Mod.Instance
	public static BubbleBurst instance;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ItemInit.init();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MODID, "bubble_game"),
				ApplicationGame.class);
	}

	private static class TabBubbleBurst extends CreativeTabs {

		public TabBubbleBurst(String label) {
			super(label + "tab");
			this.setBackgroundImageName(label + ".png");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemInit.fire_stick);
		}
	}
}
