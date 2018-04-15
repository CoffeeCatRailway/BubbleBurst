package coffeecatteam.bubbleburst.util.handlers.score;

public class PlayerScoreHolder {

	private String playerName;
	private long playerScore;
	
	public PlayerScoreHolder(String playerName, long playerScore) {
		this.playerName = playerName;
		this.playerScore = playerScore;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public long getPlayerScore() {
		return playerScore;
	}
}
