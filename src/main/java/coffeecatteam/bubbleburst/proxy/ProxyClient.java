package coffeecatteam.bubbleburst.proxy;

import coffeecatteam.bubbleburst.init.entity.EntityBomb;
import coffeecatteam.bubbleburst.init.entity.RenderBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProxyClient extends ProxyCommon {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBomb(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()) {
        });
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
