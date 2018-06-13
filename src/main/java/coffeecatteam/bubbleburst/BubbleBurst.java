package coffeecatteam.bubbleburst;

import coffeecatteam.bubbleburst.init.InitItem;
import coffeecatteam.bubbleburst.proxy.ProxyCommon;
import coffeecatteam.bubbleburst.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class BubbleBurst {

	public static final CreativeTabs BUBBLEBURSTTAB = new TabBubbleBurst();

	public static Logger logger = Utils.getLogger();

	@Mod.Instance
	public static BubbleBurst instance;

    @SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.COMMONPROXY)
    private static ProxyCommon proxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
	    proxy.preInit(event);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
        proxy.init(event);
	}

    @EventHandler
    public static void init(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }


        private static class TabBubbleBurst extends CreativeTabs {

		public TabBubbleBurst() {
			super(Reference.MODID + "tab");
			this.setBackgroundImageName(Reference.MODID + ".png");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(InitItem.fire_stick);
		}
	}
}
