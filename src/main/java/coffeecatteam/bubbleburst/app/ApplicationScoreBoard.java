package coffeecatteam.bubbleburst.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.mrcrayfish.device.api.app.Application;
import com.mrcrayfish.device.api.app.Component;
import com.mrcrayfish.device.api.app.Icons;
import com.mrcrayfish.device.api.app.component.Button;
import com.mrcrayfish.device.api.app.component.Image;
import com.mrcrayfish.device.api.app.component.ItemList;
import com.mrcrayfish.device.api.app.component.Label;
import com.mrcrayfish.device.api.app.component.Text;

import coffeecatteam.bubbleburst.Reference;
import coffeecatteam.bubbleburst.util.handlers.score.PlayerScoreHolder;
import coffeecatteam.bubbleburst.util.handlers.score.ScoreboardFileHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ApplicationScoreBoard extends Application {

	private static ScoreboardFileHandler sbf;

	public static final List<PlayerScoreHolder> SCORES = new ArrayList<>();
	private static ItemList<String> listScores;
	
	private Text textScoreInfo;

	private Image background;

	private Button buttonBack;
	private Label labelVersion;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 150;

	public ApplicationScoreBoard() {
		this.setDefaultWidth(WIDTH);
		this.setDefaultHeight(HEIGHT);
	}

	@Override
	public void init() {
		ScoreboardFileHandler.init();
		sbf = new ScoreboardFileHandler();
		
		this.background = new Image(0, 0, WIDTH, HEIGHT, 0, 0, 256, 255,
				new ResourceLocation(Reference.MODID, "textures/app/backgrounds/scoreboard.png"));
		super.addComponent(this.background);

		this.labelVersion = new Label("Version: " + this.getInfo().getVersion(), WIDTH - 5, 8);
		this.labelVersion.setAlignment(Component.ALIGN_RIGHT);
		this.labelVersion.setScale(1d);
		super.addComponent(this.labelVersion);

		listScores = new ItemList<String>(5, 27, WIDTH - 10, 5);
		refreshScores();
		super.addComponent(this.listScores);
		
		textScoreInfo = new Text("meow", 25, 100, WIDTH - 10);
		super.addComponent(textScoreInfo);

		Button refreshScores = new Button(5, 100, Icons.RELOAD);
		refreshScores.setClickListener((mouseX, mouseY, mouseButton) -> {
			if (mouseButton == 0)
				refreshScores();
		});
		super.addComponent(refreshScores);
	}

	@Override
	public void onClose() {
		saveScores();
		super.onClose();
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
		
		Collections.sort(SCORES, new Comparator<PlayerScoreHolder>() {
		    @Override
		    public int compare(PlayerScoreHolder lhs, PlayerScoreHolder rhs) {
		        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
		        return lhs.getPlayerScore() > rhs.getPlayerScore() ? -1 : (lhs.getPlayerScore() < rhs.getPlayerScore()) ? 1 : 0;
		    }
		});
	}

	/**
	 * Refresh the scoreboard, will also sort the scoreboard.
	 */
	public static void refreshScores() {
		sortScores();
		if (listScores.getItems().size() > 0)
			listScores.getItems().clear();
		
		File scoreboard = sbf.getFile(sbf.GLOBAL_SCOREBOARD).getAbsoluteFile();
		List<PlayerScoreHolder> scoresTmp = new ArrayList<>();
		if (scoreboard.exists()) {
			try {
				Scanner scn = new Scanner(new BufferedReader(new FileReader(scoreboard)));
				String line = null;
				
				if (scn.hasNextLine())
					line = scn.nextLine();
				while (scn.hasNextLine()) {
					scoresTmp.add(new PlayerScoreHolder(line.split(", ")[0], Integer.valueOf(line.split(", ")[1])));
					line = scn.nextLine();
				}
				scn.close();
				
				SCORES.clear();
				SCORES.addAll(scoresTmp);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		SCORES.forEach(score -> listScores.addItem(score.getPlayerName() + ", " + score.getPlayerScore()));
		listScores.setSelectedIndex(-1);
	}

	/**
	 * Adds the player's score to the global scoreboard.
	 * Will also save and refresh scoreboard.
	 * 
	 * @param player
	 * @param score
	 */
	public static void addScore(String player, long score) {
		addScore(new PlayerScoreHolder(player, score));
	}

	/**
	 * Adds the player's score to the global scoreboard.
	 * Will also save and refresh scoreboard.
	 * 
	 * @param playerScore
	 */
	public static void addScore(PlayerScoreHolder playerScore) {
		SCORES.add(playerScore);
		saveScores();
	}
	
	/**
	 * Save scores to saves/CURRENT_WORLD/bubbleburst_scoreboards/golbal.scoreboard file.
	 */
	public static void saveScores() {
		refreshScores();
		sortScores();
		try {
			sbf.createFile(sbf.GLOBAL_SCOREBOARD);

			FileWriter sb = sbf.getFileWriter(sbf.GLOBAL_SCOREBOARD);
			SCORES.forEach(psh -> {
				try {
					sb.write(psh.getPlayerName() + ", " + String.valueOf(psh.getPlayerScore())+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			sb.write("\n");
			sb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
