package me.haileykins.ApocalypticSurvival.Handlers;

import me.haileykins.ApocalypticSurvival.ApocalypticSurvival;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("unused")

public class PlayerJoinHandler implements Listener {

    private ApocalypticSurvival plugin;

    public PlayerJoinHandler(ApocalypticSurvival pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (plugin.inGameWorld(p)) {
            plugin.setupScoreboard(p);
        }
    }
}
