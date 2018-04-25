package coffeecatteam.bubbleburst.util.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import coffeecatteam.bubbleburst.BubbleBurst;
import coffeecatteam.bubbleburst.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class AnimationHandler {
	
	private static final int MAX_ANIMS = 10;

	public static final String[] CURSOR_SKINS = new String[] { "default", "stone", "laser_sword", "magic" };
	public static List<ResourceLocation> CUSOR;
	public static final String[] BOMB_SKINS = new String[] { "default", "future" };
	public static List<ResourceLocation> BOMB;
	public static List<ResourceLocation> FIRE_BALL;
	
	public static void init() {
		CUSOR = getAnimations("cursor", CURSOR_SKINS);
		BOMB = getAnimations("bomb", BOMB_SKINS);
		FIRE_BALL = getAnimations("fire_ball", "default");
	}

	private static List<ResourceLocation> getAnimations(String name, String... anims) {
		List<ResourceLocation> animations = new ArrayList<ResourceLocation>();
		for (String anim : anims) {
			for (int i = 0; i < MAX_ANIMS; i++) {
				String path = "textures/app/sprites/" + name + "/" + anim + "/anim" + String.valueOf(i) + ".png";
				ResourceLocation newAnim = new ResourceLocation(Reference.MODID, path);
				InputStream stream = null;
				try {
					stream = Minecraft.getMinecraft().getResourceManager().getResource(newAnim).getInputStream();
				} catch (IOException e) {
					//System.err.println(e.getMessage());
					continue;
				}
				if (stream != null) {
					BubbleBurst.logger.info("Sprite [" + newAnim + "] has been loaded!");
					animations.add(newAnim);
				}
			}
		}
		return animations;
	}
	
	public static ResourceLocation getRandomAnimation(List<ResourceLocation> animations) {
		return (animations.size() == 1) ? animations.get(0) : animations.get(new Random().nextInt(animations.size()));
	}
}
