package coffeecatteam.bubbleburst.init.items;

import coffeecatteam.bubbleburst.BubbleBurst;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(BubbleBurst.BUBBLEBURSTTAB);
	}
}
