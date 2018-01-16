package coffeecatteam.bubbleburst.app.component.sprites;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteCheese extends SpriteObj {
	
	private static final ResourceLocation CHEESE = new ResourceLocation(Reference.MODID, "textures/app/sprites/cheese.png");

	public SpriteCheese(int x, int y, ApplicationGame application) {
		super(x, y, 8, CHEESE, application);
		setScoreIncrease(0l);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		
	}
}
