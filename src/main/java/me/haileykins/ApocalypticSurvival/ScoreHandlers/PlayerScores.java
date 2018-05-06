package me.haileykins.ApocalypticSurvival.ScoreHandlers;

import me.haileykins.ApocalypticSurvival.ScoreHandlers.PlayerScore;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerScores {

    private File playersfile;
    private final String playerfilename = "scores.csv";

    private Plugin plugin;
    private Map<UUID, PlayerScore> scores = new HashMap<>();

    public PlayerScores(Plugin plugin) {
        this.plugin = plugin;

        loadScores();
    }

    private PlayerScore score(UUID id) {
        if (!scores.containsKey(id)) {
            scores.put(id, new PlayerScore(id));
            saveScores();
        }
        return scores.get(id);
    }

    public void saveScores(){
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(playersfile), "utf-8"))) {

                for (PlayerScore score : scores.values()) {
                    writer.write(score.toCSV());
                }
            }
        } catch (Exception ee) {
            plugin.getLogger().log(Level.SEVERE, "Failed to write player file", ee);
            return;
        }
    }

    public void loadScores() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playersfile = new File( plugin.getDataFolder(), playerfilename);

        if (!playersfile.exists()) {
            plugin.getLogger().log(Level.WARNING, "No player score file: {0}", playerfilename);
            return;
        }

        scores.clear();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(playersfile))) {
                for (String line; (line = br.readLine()) != null; ) {
                    if (line.length() > 0 && line.charAt(0) == '#')
                        continue;

                    PlayerScore item = PlayerScore.reparseCSV(line);
                    if (item == null) {
                        plugin.getLogger().severe("Failed to load player: " + line);
                        continue;
                    }
                    scores.put(item.playerId, item);
                }
            }
            plugin.getLogger().info("Loaded " + scores.size() + " players");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void addKill(Player player) {
        score(player.getUniqueId()).killCount++;
        saveScores();
        plugin.getLogger().info("New Score: " + score(player.getUniqueId()).killCount);
    }

    public void addDeath(Player player) {
        score(player.getUniqueId()).deathCount++;
        saveScores();
        plugin.getLogger().info("New Score: " + score(player.getUniqueId()).deathCount);
    }

    public void resetStats(Player player) {
        PlayerScore s = score(player.getUniqueId());
        s.deathCount = 0;
        s.killCount = 0;
        saveScores();
    }

    public PlayerScore getScore(Player player) {
        return score(player.getUniqueId());
    }
}
