package coffeecatteam.bubbleburst.app;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.util.handlers.score.PlayerScoreHolder;
import coffeecatteam.bubbleburst.util.handlers.score.ScoreboardHandler;
import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.component.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;

public class ApplicationScoreBoard extends Application {

	private static final List<PlayerScoreHolder> SCORES = new ArrayList<>();
	private static ItemList<String> listScores;

    private static final int WIDTH = 200;
    private static final int HEIGHT = 150;

	public ApplicationScoreBoard() {
		this.setDefaultWidth(WIDTH);
		this.setDefaultHeight(HEIGHT);
	}

	@Override
	public void init(@Nullable NBTTagCompound intent) {
        Image background = new Image(0, 0, WIDTH, HEIGHT, 0, 0, 256, 255, new ResourceLocation(Reference.MODID, "textures/app/backgrounds/scoreboard.png"));
		super.addComponent(background);

        Label labelVersion = new Label("Version: " + this.getInfo().getVersion(), WIDTH - 5, 8);
		labelVersion.setAlignment(Component.ALIGN_RIGHT);
		labelVersion.setScale(1d);
		super.addComponent(labelVersion);

		listScores = new ItemList<>(5, 27, WIDTH - 10, 5);
		refreshScores();
		super.addComponent(listScores);

        Text textScoreInfo = new Text("meow", 25, 100, WIDTH - 10);
		super.addComponent(textScoreInfo);

		Button refreshScores = new Button(5, 100, Icons.RELOAD);
		refreshScores.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0)
				refreshScores();
		});
		super.addComponent(refreshScores);
	}

	@Override
	public void load(NBTTagCompound tagCompound) {
	}

	@Override
	public void save(NBTTagCompound tagCompound) {
	}
	
	/**
	 * Sort scores from higher to lower and remove doubles.
	 */
	private static void sortScores() {
		if (SCORES.size() != 1) {
			for (int i = 0; i < SCORES.size(); i++) {
				PlayerScoreHolder psh = SCORES.get(i);
				PlayerScoreHolder nextPsh = SCORES.get((i < SCORES.size()-1) ? i+1 : 0);
				
				//System.out.println(psh.getPlayerName() + " | " + nextPsh.getPlayerName());
				//System.out.println(psh.getPlayerScore() + " | " + nextPsh.getPlayerScore());
				if (psh.getPlayerName().equals(nextPsh.getPlayerName())) {
					if (psh.getPlayerScore() == nextPsh.getPlayerScore()) {
						SCORES.remove(psh);
						i--;
					}
				}
			}
		}

        SCORES.sort(Comparator.comparingLong(PlayerScoreHolder::getPlayerScore));
	}

	/**
	 * Refresh the scoreboard, will also sort the scoreboard.
	 */
	public static void refreshScores() {
		sortScores();
		if (listScores.getItems().size() > 0)
			listScores.getItems().clear();
		
		Map<UUID, PlayerScoreHolder> scoreboard = ScoreboardHandler.INSTANCE.getScoreboard();
		List<PlayerScoreHolder> scoresTmp = new ArrayList<>();
		scoreboard.forEach(((uuid, playerScoreHolder) -> scoresTmp.add(playerScoreHolder)));
		SCORES.clear();
		SCORES.addAll(scoresTmp);

		List<String> scores = new ArrayList<>();
		for (PlayerScoreHolder holder : SCORES) {
            if (!scores.contains(holder.getPlayerName())) {
                scores.add(holder.getPlayerName());
                listScores.addItem(holder.getPlayerName());
            }
        }
		listScores.setSelectedIndex(-1);
	}

	/**
	 * Adds the player's score to the global scoreboard.
	 * Will also save and refresh scoreboard.
	 */
	public static void addScore(String player, long score) {
        SCORES.add(new PlayerScoreHolder(player, score));
	}
}
