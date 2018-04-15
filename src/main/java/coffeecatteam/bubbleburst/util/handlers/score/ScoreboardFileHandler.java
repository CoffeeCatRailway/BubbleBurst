package coffeecatteam.bubbleburst.util.handlers.score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.mrcrayfish.device.event.EmailEvents;
import com.mrcrayfish.device.programs.email.EmailManager;
import com.mrcrayfish.device.programs.email.object.Email;

import coffeecatteam.bubbleburst.BubbleBurst;
import coffeecatteam.bubbleburst.Reference;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.server.FMLServerHandler;

public class ScoreboardFileHandler {
	
	// Scoreboards
	public static final String GLOBAL_SCOREBOARD = "global.scoreboard";

	/**
	 * Save path for scoreboards
	 */
	//public static final String SAVE_PATH = getSavePath(); // Old SAVE_PATH: "mods/" + Reference.MODID + "/"
	
	public static void init() {
		File scoreboardFolder = getFile(GLOBAL_SCOREBOARD);
		if (!scoreboardFolder.exists())
			scoreboardFolder.mkdirs();
	}
	
//	public static String getSavePath() {
//		String worldName = FMLCommonHandler.instance().getMinecraftServerInstance().getWorldName();
//		return "saves/" + worldName + "/bubbleburst_scoreboards/";
//	}

	// Get and create files
	public static File getFile(String filename) {
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), filename);
		BubbleBurst.logger.info("Loaded scoreboard [" + filename + "]!");
		return file;
	}

	public File createFile(String filename) throws IOException {
		
		File file = getFile(filename);
		if (!file.exists())
			file.createNewFile();
		return file;
	}

	public FileWriter getFileWriter(String filename) throws IOException {
		File file = getFile(filename);
		if (!file.exists())
			throw new IOException("File [" + file.getPath() + "] does not exist!");

		return new FileWriter(file);
	}
}
