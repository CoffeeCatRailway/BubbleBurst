package coffeecatteam.bubbleburst;

import com.mrcrayfish.device.api.ApplicationManager;
import com.mrcrayfish.device.core.Laptop;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.ApplicationScoreBoard;
import coffeecatteam.bubbleburst.init.ItemInit;
import coffeecatteam.bubbleburst.utill.handlers.score.ScoreboardFileHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions=Reference.WORKING_MC_VERSION)
public class BubbleBurst {

	public static final CreativeTabs BUBBLEBURSTTAB = new TabBubbleBurst(Reference.MODID);

	@Mod.Instance
	public static BubbleBurst instance;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ScoreboardFileHandler.init();
		
		ItemInit.init();
		//EntityInit.init();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
//		Laptop.addWallpaper(new ResourceLocation(Reference.MODID, "textures/gui/wallpaper_coffeecatrailway.png"));
//		Laptop.addWallpaper(new ResourceLocation(Reference.MODID, "textures/app/backgrounds/game/default.png"));
//		Laptop.addWallpaper(new ResourceLocation(Reference.MODID, "textures/app/backgrounds/game/desert.png"));
		
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MODID, "bubble_game"),
				ApplicationGame.class);
		ApplicationManager.registerApplication(new ResourceLocation(Reference.MODID, "bubble_scoreboard"),
				ApplicationScoreBoard.class);
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
