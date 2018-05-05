package me.haileykins.ApocalypticSurvival;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerScores {

    private Plugin plugin;
    private Map<UUID, PlayerScore> scores = new HashMap<>();

    public PlayerScores(Plugin plugin) {
        this.plugin = plugin;
    }

    private PlayerScore score(UUID id) {
        if (!scores.containsKey(id)) {
            scores.put(id, new PlayerScore());
        }
        return scores.get(id);
    }

    public void saveScores(){
        // TODO: Save this in a file somewhere
    }

    public void loadScores() {
        // TODO: Load the player scores from the file
    }

    public void addKill(Player player) {
        score(player.getUniqueId()).killCount++;
        saveScores();
    }

    public void addDeath(Player player) {
        score(player.getUniqueId()).deathCount++;
        saveScores();
    }

    public void reset(Player player) {
        PlayerScore s = score(player.getUniqueId());
        s.deathCount = 0;
        s.killCount = 0;
        saveScores();
    }

    public PlayerScore getScore(Player player) {
        return score(player.getUniqueId());
    }

}
