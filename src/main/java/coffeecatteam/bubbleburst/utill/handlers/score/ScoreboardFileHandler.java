package coffeecatteam.bubbleburst.utill.handlers.score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import coffeecatteam.bubbleburst.Reference;

public class ScoreboardFileHandler {
	
	// Scoreboards
	public static final String GLOBAL_SCOREBOARD = "global.scoreboard";

	/**
	 * Save path for scoreboards
	 */
	public static final String SAVE_PATH = "mods/" + Reference.MODID + "/";
	
	public static void init() {
		File scoreboardFolder = new File(SAVE_PATH);
		if (!scoreboardFolder.exists())
			scoreboardFolder.mkdirs();
	}

	// Get and create files
	public File getFile(String filename) {
		String path = SAVE_PATH + filename;
		File file = new File(path);
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
