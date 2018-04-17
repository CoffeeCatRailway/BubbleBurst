package coffeecatteam.bubbleburst.app.layouts.menu;

import java.awt.Color;
import java.text.DecimalFormat;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.ComboBox;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.Slider;
import com.mrcrayfish.device.api.app.component.TextArea;
import com.mrcrayfish.device.api.app.renderer.ListItemRenderer;
import com.mrcrayfish.device.api.io.Drive;
import com.mrcrayfish.device.api.utils.RenderUtil;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LayoutSettings extends LayoutStandard {
	
	public static boolean hasDefaults = true;

	private Button buttonBack;
	private Label labelVersion;

	// Bombs Amount
	private Label labelBombsAmount;
	private TextArea textAreaBombsAmount;
	private int bombsDefault = 3; // default: 3
	private int bombsAmount = bombsDefault;
	private int maxBombsAmount = 10000; // default: 10000
	private boolean textAreaBombsAmountCanUpdate = true;

	// Bubbles Amount
	private Label labelBubblesAmount;
	private TextArea textAreaBubblesAmount;
	private int bubblesDefault = 6; // default: 6
	private int bubblesAmount = bubblesDefault;
	private int maxBubblesAmount = 10000; // default: 10000
	private boolean textAreaBubblesAmountCanUpdate = true;
	
	// Game Volume
	private Label labelGameVolume;
	private Slider sliderGameVolume;
	private float gameVolume = 1.0f;

	public LayoutSettings(int width, int height, ApplicationGame application) {
		super(width, height, application, true, BG_SETTINGS);
	}

	@Override
	public void init() {
		super.init();
		
		this.buttonBack = new Button(5, 3, "Exit", Icons.HOME);
		this.buttonBack.setToolTip("Exit", "Exit to main menu.");
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				if (this.bombsAmount > maxBombsAmount) {
					this.bombsAmount = maxBombsAmount;
					this.textAreaBombsAmount.setText(String.valueOf(this.bombsAmount));
				}
				if (this.bubblesAmount > maxBubblesAmount) {
					this.bubblesAmount = maxBubblesAmount;
					this.textAreaBubblesAmount.setText(String.valueOf(this.bubblesAmount));
				}
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 50, 8);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		// Settings
		this.labelBombsAmount = new Label("Amount of Bombs", 3, 27);
		super.addComponent(this.labelBombsAmount);
		this.labelBombsAmount = new Label(
				"" + TextFormatting.DARK_RED + TextFormatting.BOLD + "Max: " + this.maxBombsAmount, 3, 37);
		super.addComponent(this.labelBombsAmount);
		this.textAreaBombsAmount = new TextArea(3, 46, 42, 17);
		this.textAreaBombsAmount.setText(String.valueOf(this.bombsAmount));
		super.addComponent(this.textAreaBombsAmount);

		this.labelBubblesAmount = new Label("Amount of Bubbles", 3, 67);
		super.addComponent(this.labelBubblesAmount);
		this.labelBubblesAmount = new Label(
				"" + TextFormatting.DARK_RED + TextFormatting.BOLD + "Max: " + this.maxBubblesAmount, 3, 77);
		super.addComponent(this.labelBubblesAmount);
		this.textAreaBubblesAmount = new TextArea(3, 86, 42, 17);
		this.textAreaBubblesAmount.setText(String.valueOf(this.bubblesAmount));
		super.addComponent(this.textAreaBubblesAmount);
		
		this.sliderGameVolume = new Slider(105, 37, 70);
		super.addComponent(this.sliderGameVolume);
		this.labelGameVolume = new Label("Game Volume: ", 105, 27);
		super.addComponent(this.labelGameVolume);

		// Set Settings Values
		this.bombsAmount = this.application.getBombsAmount();
		this.bubblesAmount = this.application.getBubblesAmount();
		
		this.gameVolume = this.application.getGameVolume();
		this.sliderGameVolume.setPercentage(this.gameVolume);
	}

	@Override
	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			// Bombs Amount
			String textAreaBombsAmountText = this.textAreaBombsAmount.getText();
			for (int i = 0; i < textAreaBombsAmountText.length(); i++) {
				if (Character.isDigit(textAreaBombsAmountText.charAt(i)))
					this.textAreaBombsAmountCanUpdate = true;
				else
					this.textAreaBombsAmountCanUpdate = false;
			}
			if (this.textAreaBombsAmountCanUpdate)
				if (!(textAreaBombsAmountText.equals(""))) {
				this.bombsAmount = Integer.valueOf(textAreaBombsAmountText);
				this.textAreaBombsAmount.setText(String.valueOf(this.bombsAmount));
			}

			// Bubbles Amount
			String textAreaBubblesAmountText = this.textAreaBubblesAmount.getText();
			for (int i = 0; i < textAreaBubblesAmountText.length(); i++) {
				if (Character.isDigit(textAreaBubblesAmountText.charAt(i)))
					this.textAreaBubblesAmountCanUpdate = true;
				else
					this.textAreaBubblesAmountCanUpdate = false;
			}
			if (this.textAreaBubblesAmountCanUpdate)
				if (!(textAreaBubblesAmountText.equals(""))) {
					this.bubblesAmount = Integer.valueOf(textAreaBubblesAmountText);
					this.textAreaBubblesAmount.setText(String.valueOf(this.bubblesAmount));
				}
			
			// Game Volume - (this.sliderGameVolume.getPercentage()%1)*100
			this.labelGameVolume.setText("Game Volume: "+this.gameVolume);
			this.gameVolume = getGameVolume();

			this.application.markDirty();
			this.application.save(Minecraft.getMinecraft().player.getEntityData());
		}
		this.application.setBombsAmount(this.bombsAmount);
		this.application.setBubblesAmount(this.bubblesAmount);
		this.application.setGameVolume(this.gameVolume);
	}
	
	// Bombs
	public boolean isBombsSetToDefault() {
		return bombsAmount == bombsDefault;
	}

	public int getBombsAmount() {
		return bombsAmount;
	}
	
	// Bubbles
	public boolean isBubblesSetToDefault() {
		return bubblesAmount == bubblesDefault;
	}

	public int getBubblesAmount() {
		return bubblesAmount;
	}
	
	// Volume
	public float getGameVolume() {
		return Float.valueOf(new DecimalFormat("#.##").format(this.sliderGameVolume.getPercentage()));
	}
}
