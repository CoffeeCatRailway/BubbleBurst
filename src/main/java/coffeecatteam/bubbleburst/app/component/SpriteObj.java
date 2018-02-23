package coffeecatteam.bubbleburst.app.component;

import java.util.Random;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.utill.Utills;
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
		super(left, top, sprite);
		pointer = 0;
		this.application = application;
		
		this.WIDTH = this.application.getWidth();
		this.HEIGHT = this.application.getHeight();
		
		this.volume = this.application.getGameVolume();
		this.pitch = (0.5f + new Random().nextFloat()) * 1.5f;
		
		elapsedTime = 0;
		currentTime = 0;
		lastTime = Utills.getTime();
		this.fps = 1.0 / (double) fps;
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
		currentTime = Utills.getTime();
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
