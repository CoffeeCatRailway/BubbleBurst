package coffeecatteam.bubbleburst.app.component;

import java.util.Random;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SpriteObj extends Sprite {

	protected int pointer;
	protected ApplicationGame application;

	protected int WIDTH;
	protected int HEIGHT;
	
	// Sounds
	private float volume;
	private float pitch;
	
	// Score & Length
	private int length;
	private long scoreIncrease;

	// Timer
	private double elapsedTime;
	private double currentTime;
	private double lastTime;
	private double fps;

	public SpriteObj(int left, int top, int fps, ResourceLocation sprite, ApplicationGame application) {
		this(left, top, fps, sprite, application, 64, 32);
	}
	
	public SpriteObj(int left, int top, int fps, ResourceLocation sprite, ApplicationGame application, int tw, int th) {
		super(left, top, sprite, tw, th);
		pointer = 0;
		this.application = application;
		
		this.WIDTH = this.application.getWidth();
		this.HEIGHT = this.application.getHeight();
		
		this.volume = this.application.getGameVolume();
		this.pitch = (0.5f + new Random().nextFloat()) * 1.5f;
		
		elapsedTime = 0;
		currentTime = 0;
		lastTime = Utils.getTime();
		this.fps = 1.0 / (double) fps;
	}
	
	// Anims
	public static ResourceLocation[] getAnims(String sprite, int amount) {
		ResourceLocation[] locations = new ResourceLocation[amount];
		for (int i = 0; i < amount; i++) {
			locations[i] = new ResourceLocation(Reference.MODID, "textures/app/sprites/" + sprite + "/anim" + String.valueOf(i) + ".png");
		}
		return locations;
	}
	
	protected static ResourceLocation getRandomAnim(ResourceLocation[] anims) {
		return anims[new Random().nextInt(anims.length)];
	}
	
	// Sounds
	protected void setVolume(float volume) {
		this.volume = volume;
	}
	
	protected float getVolume() {
		return volume;
	}
	
	protected void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	protected float getPitch() {
		return pitch;
	}
	
	// Score & Length
	protected void setScoreIncrease(long scoreIncrease) {
		this.scoreIncrease = scoreIncrease;
	}
	
	protected long getScoreIncrease() {
		return scoreIncrease;
	}
	
	/**
	 * Length 1 = 1 (secs)
	 * 
	 * @param length {@code Integer}
	 */
	protected void setLength(int length) {
		this.length = length;
	}
	
	protected int getLength() {
		return length;
	}
	
	@Override
	public void update(Application app, Layout layout, Minecraft mc) {
		currentTime = Utils.getTime();
		elapsedTime += currentTime - lastTime;

		if (elapsedTime >= fps) {
			elapsedTime = 0;
			pointer++;
		}

		if (pointer >= length)
			pointer = length+1;
		lastTime = currentTime;
		//System.out.println(pointer);
	}
}
