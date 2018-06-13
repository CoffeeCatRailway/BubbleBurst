package coffeecatteam.bubbleburst.proxy;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.init.InitEntity;
import coffeecatteam.bubbleburst.init.InitItem;
import com.mrcrayfish.device.api.ApplicationManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProxyCommon {

	public void preInit(FMLPreInitializationEvent event) {
        InitEntity.init();
        InitItem.init();
	}

	public void init(FMLInitializationEvent event) {
        ApplicationManager.registerApplication(new ResourceLocation(Reference.MODID, "bubble_game"), ApplicationGame.class);
	}

	public void postInit(FMLPostInitializationEvent event) {
	}
}
