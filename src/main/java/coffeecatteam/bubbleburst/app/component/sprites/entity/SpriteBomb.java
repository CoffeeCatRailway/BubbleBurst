package coffeecatteam.bubbleburst.app.component.sprites.entity;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.app.ApplicationGame;
import coffeecatteam.bubbleburst.app.component.SpriteEnemy;
import coffeecatteam.bubbleburst.app.layouts.game.LayoutGame;
import coffeecatteam.bubbleburst.util.handlers.SoundHandler;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Layout;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.Random;

public class SpriteBomb extends SpriteEnemy {

    public SpriteBomb(int x, int y, int scoreIncrease, ApplicationGame application) {
        super("bomb", x, y, scoreIncrease, new ResourceLocation(Reference.MODID, "textures/app/sprites/bomb/default/normal.png"), application, false);
    }

    @Override
    public void update(Application app, Layout layout, Minecraft mc) {
        LayoutGame layoutGame = (LayoutGame) layout;
        this.speed = getSpeed() + ((this.isSuper) ? 2 : 0);

        if (this.canMove())
            this.yPosition += this.speed;
        if (this.yPosition > layoutGame.height * 2 + 10)
            layoutGame.respawn(this, layoutGame.width, layoutGame.height);

        // Check if cursor is touching a hydrogen bubble
        if (layoutGame.cursor.isTouching(this)) {
            if (!this.getSprite().equals(EXPLOSION)) {
                // Check is super
                if (this.isSuper) { // Super bomb effect
                    int scoreMultiple = new Random().nextInt(1) + 2;

                    for (int i = 0; i < this.application.getLayoutGame().bombs.size(); i++) {
                        SpriteBomb bomb = this.application.getLayoutGame().bombs.get(i);
                        if (!bomb.isSuper) {
                            // X Distance
                            int xd = bomb.xPosition - this.xPosition; // getDistance(bomb);
                            // xd = negToPos(xd);
                            // Y Distance
                            int yd = bomb.yPosition - this.yPosition; // getDistance(bomb);
                            // yd = negToPos(yd);
                            System.out.println(i + " -x- " + xd + " -y- " + yd);

                            int maxD = 20;
                            boolean xdB = (xd < 0) ? (xd >= -maxD) : (xd <= maxD);
                            boolean ydB = (yd < 0) ? (yd >= -maxD) : (yd <= maxD);
                            if (xdB && ydB) {
                                bomb.setCanMove(false);
                                scoreMultiple++;
                            }
                        }
                    }

                    updateScore(layoutGame, scoreMultiple);
                    this.isSuper = false;
                } else { // Normal bomb effect
                    updateScore(layoutGame, 1);
                    this.isSuper = pickIsSuper();
                }

                // Play sound(s) and stop moving (trigger explosion anim)
                playExplosionSound(layoutGame, mc);
                this.setCanMove(false);
            }
        }

        // Set anim
        if (!this.canMove()) {
            super.update(app, layoutGame, mc);
            if (pointer < getLength()) {
                this.setTextureHeight(32);
                CURRENT_SKIN = EXPLOSION;
            } else {
                if (pointer >= getLength())
                    pointer = 0;
                this.setTextureHeight(64);
                CURRENT_SKIN = pickSkin();
                this.setCanMove(true);
                layoutGame.respawn(this, layoutGame.width, layoutGame.height);
            }
            this.setSprite(CURRENT_SKIN);
        }
    }

    private int negToPos(int n) {
        return (n < 0) ? -n : n;
    }

    private int getDistance(SpriteBomb bomb) {
        Double d = Math.sqrt(Math.pow(this.xPosition - bomb.xPosition, 2) + Math.pow(this.yPosition - bomb.yPosition, 2));
        return negToPos(d.intValue());
    }

    private void updateScore(LayoutGame layoutGame, int multiple) {
        layoutGame.updateScore(layoutGame.getScore(), -getScoreIncrease() * multiple, layoutGame.labelScore);
        layoutGame.updateBombCount(layoutGame.getBombCount(), 1 * multiple, layoutGame.labelBombCount);
    }

    private void playExplosionSound(LayoutGame layoutGame, Minecraft mc) {
        float v = this.application.getGameVolume() - 0.3f;
        setVolume(v < 0.0f ? 0.0f : v <= 0.3f ? v += ((0.1f + v) * 0.5f) : v);
        SoundEvent sound = (layoutGame.randInt(0, 10) < 2) ? SoundHandler.BOMB_1 : SoundHandler.BOMB_2;
        mc.player.playSound(sound, getVolume(), getPitch());
    }
}
