package coffeecatteam.bubbleburst.app.component;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;

import coffeecatteam.bubbleburst.utill.Utills;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SpriteObj extends Sprite {

	protected int pointer;
	private int length;
	private long scoreIncrease;

	private double elapsedTime;
	private double currentTime;
	private double lastTime;
	private double fps;

	public SpriteObj(int left, int top, int fps, ResourceLocation sprite) {
		super(left, top, sprite);
		pointer = 0;
		elapsedTime = 0;
		currentTime = 0;
		lastTime = Utills.getTime();
		this.fps = 1.0 / (double) fps;
	}
	
	protected long getScoreIncrease() {
		return scoreIncrease;
	}
	
	protected void setScoreIncrease(long scoreIncrease) {
		this.scoreIncrease = scoreIncrease;
	}
	
	protected int getLength() {
		return length;
	}
	
	/**
	 * Length 1 = 1 (secs)
	 * 
	 * @param length {@code Integer}
	 */
	protected void setLength(int length) {
		this.length = length;
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
