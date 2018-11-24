package coffeecatteam.bubbleburst.util.handlers.score;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardHandler {

    public static ScoreboardHandler INSTANCE = new ScoreboardHandler();

    private Map<UUID, PlayerScoreHolder> playerScoreHolder = new HashMap<>();

    public void save(NBTTagCompound tag) {
        NBTTagList scoreList = new NBTTagList();
        for (UUID uuid : playerScoreHolder.keySet()) {
            NBTTagCompound holderTag = new NBTTagCompound();
            PlayerScoreHolder holder = playerScoreHolder.get(uuid);

            holderTag.setString("uuid", uuid.toString());
            holderTag.setString("playerName", holder.getPlayerName());
            holderTag.setLong("score", holder.getPlayerScore());
            scoreList.appendTag(holderTag);
        }
        tag.setTag("scores", scoreList);
    }

    public void load(NBTTagCompound tag) {
        NBTTagList scoreList = (NBTTagList) tag.getTag("scores");
        for (int i = 0; i < scoreList.tagCount(); i++) {
            NBTTagCompound holderTag = scoreList.getCompoundTagAt(i);
            UUID uuid = UUID.fromString(holderTag.getString("uuid"));
            PlayerScoreHolder holder = new PlayerScoreHolder(holderTag.getString("playerName"), holderTag.getLong("score"));
            playerScoreHolder.put(uuid, holder);
        }
    }

    public Map<UUID, PlayerScoreHolder> getScoreboard() {
        return playerScoreHolder;
    }
}
