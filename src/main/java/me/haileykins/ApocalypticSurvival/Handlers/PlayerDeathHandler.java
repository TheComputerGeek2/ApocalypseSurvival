package me.haileykins.ApocalypticSurvival.Handlers;

import me.haileykins.ApocalypticSurvival.ApocalypticSurvival;
import me.haileykins.ApocalypticSurvival.ScoreHandlers.PlayerScores;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@SuppressWarnings("unused")

public class PlayerDeathHandler implements Listener {

    private ApocalypticSurvival plugin;

    public PlayerDeathHandler(ApocalypticSurvival pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (plugin.inGameWorld(p)) {
            PlayerScores.addDeath(p);
            plugin.setupScoreboard(p);
        }
    }
}
