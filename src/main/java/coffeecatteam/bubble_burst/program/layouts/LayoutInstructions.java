package coffeecatteam.bubble_burst.program.layouts;

import java.awt.Color;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Text;

public class LayoutInstructions extends Layout {

	private Application application;

	private Button buttonBack;
	private Text textInfo;

	public LayoutInstructions(int width, int height, Application application) {
		super(width, height);
		this.application = application;
	}

	@Override
	public void init() {
		this.components.clear();

		this.buttonBack = new Button(3, 3, "Back", Icons.ARROW_LEFT);
		this.buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.restoreDefaultLayout();
			}
		});
		super.addComponent(this.buttonBack);

		this.textInfo = new Text("", 3, 25, width - 6);
		this.textInfo.setTextColour(Color.DARK_GRAY);
		this.textInfo.setText("Game Info:"
				+ "\nThe goal of this game is to \"burst\" the hydrogen bubbles and get the highest score you can."
				+ "\n\nControls:" + "\n- Click and drag/hold to move the \"fire stick\""
				+ "\n- Press ESC to go back to main menu");
		super.addComponent(this.textInfo);
		super.init();
	}
}
