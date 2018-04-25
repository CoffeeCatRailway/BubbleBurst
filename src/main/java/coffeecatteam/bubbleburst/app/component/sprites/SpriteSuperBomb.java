package coffeecatteam.bubbleburst.app.component.sprites;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteObj;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteSuperBomb extends SpriteObj {

	public SpriteSuperBomb(int left, int top, int fps, ResourceLocation sprite, ApplicationGame application) {
		super(left, top, fps, sprite, application);
	}

	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		
	}
}
