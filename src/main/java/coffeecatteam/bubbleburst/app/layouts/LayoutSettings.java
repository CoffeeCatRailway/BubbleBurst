package coffeecatteam.bubbleburst.app.layouts;

import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.TextArea;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class LayoutSettings extends LayoutStandard {

	private Button buttonBack;
	private Label labelVersion;

	// Settings
	private Label labelBombsAmount;
	private TextArea textAreaBombsAmount;
	private int bombsAmount = 3; // default: 3

	private Label labelBubblesAmount;
	private TextArea textAreaBubblesAmount;
	private int bubblesAmount = 6; // default: 6

	public LayoutSettings(int width, int height, ApplicationGame application) {
		super(width, height, application);
	}

	@Override
	public void init(Layout layout) {
		this.buttonBack = new Button(3, 3, "Exit", Icons.HOME);
		this.buttonBack.setToolTip("Exit", "Exit to main menu.");
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 45, 8);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		// Settings
		this.labelBombsAmount = new Label("Amount of Bombs", 3, 27);
		super.addComponent(this.labelBombsAmount);
		this.textAreaBombsAmount = new TextArea(3, 36, 40, 17);
		this.textAreaBombsAmount.setText(String.valueOf(this.bombsAmount));
		super.addComponent(this.textAreaBombsAmount);

		this.labelBubblesAmount = new Label("Amount of Bubbles", 3, 57);
		super.addComponent(this.labelBubblesAmount);
		this.textAreaBubblesAmount = new TextArea(3, 66, 40, 17);
		this.textAreaBubblesAmount.setText(String.valueOf(this.bubblesAmount));
		super.addComponent(this.textAreaBubblesAmount);

		this.bombsAmount = this.application.getBombsAmount();
		this.bubblesAmount = this.application.getBubblesAmount();
	}

	@Override
	public void onTick() {
		if (this.application.getCurrentLayout() == this) {
			// Bombs Amount
			if (!(this.textAreaBombsAmount.getText().equals(""))) {
				this.bombsAmount = Integer.valueOf(this.textAreaBombsAmount.getText());
				this.textAreaBombsAmount.setText(String.valueOf(this.bombsAmount));
			}

			// Bubbles Amount
			if (!(this.textAreaBubblesAmount.getText().equals(""))) {
				this.bubblesAmount = Integer.valueOf(this.textAreaBubblesAmount.getText());
				this.textAreaBubblesAmount.setText(String.valueOf(this.bubblesAmount));
			}

			this.application.markDirty();
			this.application.save(Minecraft.getMinecraft().player.getEntityData());
		}
		this.application.setBombsAmount(this.bombsAmount);
		this.application.setBubblesAmount(this.bubblesAmount);
	}

	public int getBombsAmount() {
		return bombsAmount;
	}

	public int getBubblesAmount() {
		return bubblesAmount;
	}
}
