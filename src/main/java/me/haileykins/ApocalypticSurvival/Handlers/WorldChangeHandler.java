package me.haileykins.ApocalypticSurvival.Handlers;

import me.haileykins.ApocalypticSurvival.ApocalypticSurvival;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

@SuppressWarnings("unused")

public class WorldChangeHandler implements Listener {

    private ApocalypticSurvival plugin;

    public WorldChangeHandler(ApocalypticSurvival pl) {
        plugin = pl;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        if (plugin.inGameWorld(p)) {
            plugin.setupScoreboard(p);
        } else {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }
}
