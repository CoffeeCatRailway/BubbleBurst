package coffeecatteam.bubbleburst.app.component;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import com.mrcrayfish.device.core.Laptop;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Random;

public abstract class SpriteEnemy extends SpriteObj {

    private static String name;

    protected static ResourceLocation NORMAL;
    protected static ResourceLocation SPINNING;
    protected static ResourceLocation EXPLOSION = new ResourceLocation(Reference.MODID, "textures/app/sprites/explosion.png");
    protected static ResourceLocation CURRENT_SKIN;

    protected boolean isSuper;
    private Color superColor = new Color(255, 250, 0);

    public SpriteEnemy(String spriteName, int x, int y, int scoreIncrease, ResourceLocation sprite, ApplicationGame application, boolean isSuper) {
        super(x, y, 8, sprite, application, 64, 64);
        setScoreIncrease((long) scoreIncrease);
        setLength(5);
        this.isSuper = isSuper;

        name = spriteName;
        NORMAL = new ResourceLocation(Reference.MODID, "textures/app/sprites/" + name + "/default/normal.png");
        SPINNING = new ResourceLocation(Reference.MODID, "textures/app/sprites/" + name + "/default/spinning.png");
        CURRENT_SKIN = NORMAL;
    }

    public static void setSkin(String skin) {
        String path = "textures/app/sprites/" + name + "/" + skin;
        NORMAL = new ResourceLocation(Reference.MODID, path + "/normal.png");
        SPINNING = new ResourceLocation(Reference.MODID, path + "/spinning.png");
        CURRENT_SKIN = pickSkin();
    }

    public static boolean pickIsSuper() {
        int chance = 100;
        return (new Random().nextInt(chance) + 1) < chance/2;
    }

    protected static ResourceLocation pickSkin() {
        return (new Random().nextInt(1) == 0) ? NORMAL : SPINNING;
    }

    protected int getSpeed() {
        return 5 + new Random().nextInt(3);
    }

    @Override
    protected void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.isSuper)
            this.color = superColor;
        super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
    }
}
