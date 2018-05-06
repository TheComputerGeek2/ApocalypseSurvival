package me.haileykins.ApocalypticSurvival.Handlers;

import me.haileykins.ApocalypticSurvival.ApocalypticSurvival;
import me.haileykins.ApocalypticSurvival.ScoreHandlers.PlayerScores;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@SuppressWarnings("unused")

public class ZombieDeathHandler implements Listener {

    private ApocalypticSurvival plugin;
    private PlayerScores scores;

    public ZombieDeathHandler(ApocalypticSurvival pl, PlayerScores ps) {
        plugin = pl;
        scores = ps;
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            Player p = event.getEntity().getKiller();
            if (p != null && plugin.inGameWorld(p)) { ;
                scores.addKill(p);
                plugin.setupScoreboard(p);
            }
        }
    }

}