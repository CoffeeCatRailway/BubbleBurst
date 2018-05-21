package coffeecatteam.bubbleburst.app.layouts.menu.settings;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.Sprite;
import coffeecatteam.bubbleburst.app.component.sprites.SpriteCursor;
import coffeecatteam.bubbleburst.app.component.sprites.entity.SpriteBomb;
import coffeecatteam.bubbleburst.app.layouts.LayoutStandard;
import coffeecatteam.bubbleburst.util.handlers.AnimationHandler;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.Layout;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.app.component.Label;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class LayoutSettingsSkins extends LayoutStandard {

	// Cursor
	private ItemList<String> itemListCursor;
	private Sprite spriteCursor;

	// Bomb
	private ItemList<String> itemListBomb;
	private Sprite spriteBomb;

	public LayoutSettingsSkins(int width, int height, ApplicationGame application, String title) {
		super(width, height, application, true, BG_SETTINGS);
		setTitle(title);
	}
	
	@Override
	public void init() {
		super.init();

        Button buttonBack = new Button(5, 3, "Back", Icons.ARROW_LEFT);
		buttonBack.setToolTip("Back", "Go back to settings.");
		buttonBack.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0) {
				this.application.setLayout(this.application.getLayoutSettings());
			}
		});
		super.addComponent(buttonBack);

        Label labelVersion = new Label("Version: " + this.application.getInfo().getVersion(), 55, 10);
		super.addComponent(labelVersion);
		
		// Skins
		int skinsX = 3;
		int skinsY = 28;
		
		// Cursor
        Label labelCursor = new Label("Cursor skins:", skinsX, skinsY);
		super.addComponent(labelCursor);
		this.itemListCursor = new ItemList<>(skinsX, skinsY + 10, 70, 4);
		for (String value : AnimationHandler.CURSOR_SKINS) {
			this.itemListCursor.addItem(value);
		}
		this.itemListCursor.setSelectedIndex(0);
		super.addComponent(this.itemListCursor);
		Label cursorSkin = new Label("Skin:", skinsX, skinsY + 75);
		super.addComponent(cursorSkin);
		this.spriteCursor = new Sprite(skinsX + 25, skinsY + 70, new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/" + this.itemListCursor.getSelectedItem() + "/full.png")) {

			@Override
			public void update(Application app, Layout layout, Minecraft mc) {
				this.setSprite(new ResourceLocation(Reference.MODID, "textures/app/sprites/cursor/" + itemListCursor.getSelectedItem() + "/full.png"));
			}
		};
		super.addComponent(this.spriteCursor);
		
		// Bomb
        Label labelBomb = new Label("Bomb skins:", skinsX + 80, skinsY);
		super.addComponent(labelBomb);
		this.itemListBomb = new ItemList<>(skinsX + 80, skinsY + 10, 70, 4);
		for (String value : AnimationHandler.BOMB_SKINS) {
			this.itemListBomb.addItem(value);
		}
		this.itemListBomb.setSelectedIndex(0);
		super.addComponent(this.itemListBomb);
		Label bombSkin = new Label("Skin:", skinsX + 80, skinsY + 75);
		super.addComponent(bombSkin);
		this.spriteBomb = new Sprite(skinsX + 25 + 80, skinsY + 70, new ResourceLocation(Reference.MODID, "textures/app/sprites/bomb/" + this.itemListBomb.getSelectedItem() + "/anim0.png"), 64, 64) {

			@Override
			public void update(Application app, Layout layout, Minecraft mc) {
				this.setSprite(new ResourceLocation(Reference.MODID, "textures/app/sprites/bomb/" + itemListBomb.getSelectedItem() + "/normal.png"));
			}
		};
		super.addComponent(this.spriteBomb);
	}

	@Override
	public void onTick() {
		if (this.application.getCurrentLayout() == this && this.application.getCurrentLayout() != this.application.getLayoutSettings()) {
			// Cursor
			this.spriteCursor.update(this.application, this, Minecraft.getMinecraft());
			SpriteCursor.setSkin(this.itemListCursor.getSelectedItem());
			
			// Bomb
			this.spriteBomb.update(this.application, this, Minecraft.getMinecraft());
			String bombSkin = this.itemListBomb.getSelectedItem();
			SpriteBomb.setSkin(bombSkin);
		}
	}
}
