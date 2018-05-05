package me.haileykins.ApocalypticSurvival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class ApocalypticSurvival extends JavaPlugin implements Listener {

    private PlayerScores scores;


    private boolean isGameWorld(World world) {
        return world.getName().equalsIgnoreCase("ApocalypseSurvival");
    }

    private boolean inGameWorld(Player p){
        return isGameWorld(p.getWorld());
    }

    private boolean gameWorldExists(){
        for (World world: Bukkit.getWorlds()) {
            if (isGameWorld(world)) {
                return true;
            }
        }
        return false;
    }

    public void onEnable() {
        scores = new PlayerScores(this);
        scores.loadScores();

        // Register Commands
        //TODO: Register Commands

        // Register Listeners
        getServer().getPluginManager().registerEvents(this, this);

        // Generate World if World Does Not Exist
        if (!gameWorldExists()) {
            getLogger().info("WORLD NOT FOUND, CREATING!");
            Bukkit.createWorld(new WorldCreator("ApocalypseSurvival"));
        }
        else {
            getLogger().info("World Found!");
        }

        // Announce Successful Startup
        getLogger().info("Apocalyptic Survival Starting Up!");
    }

    public void onDisable() {
        getLogger().info("Apocalyptic Survival Shutting Down!");
    }

    public void setupScoreboard(Player player) {

        PlayerScore score = scores.getScore(player);

        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();

        Objective o = b.registerNewObjective("obj1", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.DARK_AQUA + "Apocalypse Stats");

        Score killcount = o.getScore(ChatColor.WHITE + "Kills: " + ChatColor.GOLD + score.killCount);
        killcount.setScore(2);

        Score deathcount = o.getScore(ChatColor.WHITE + "Deaths: " + ChatColor.RED + score.deathCount);
        deathcount.setScore(1);

        player.setScoreboard(b);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        getLogger().info("Player " + p.getName() + " joined");
        if (inGameWorld(p)) {
            getLogger().info("Player " + p.getName() + " in game world");
            setupScoreboard(p);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        getLogger().info("Player " + p.getName() + " to " + p.getWorld().getName()
        );

        if (inGameWorld(p)) {
            setupScoreboard(p);
        } else {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            Player p = event.getEntity().getKiller();
            if (p != null && inGameWorld(p)) {
                scores.addKill(p);
                setupScoreboard(p);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (inGameWorld(p)) {
            scores.addDeath(p);
            setupScoreboard(p);
        }
    }

}
