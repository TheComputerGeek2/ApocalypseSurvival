package me.haileykins.ApocalypticSurvival;

import me.haileykins.ApocalypticSurvival.Commands.CommandReset;
import me.haileykins.ApocalypticSurvival.Handlers.PlayerDeathHandler;
import me.haileykins.ApocalypticSurvival.Handlers.PlayerJoinHandler;
import me.haileykins.ApocalypticSurvival.Handlers.WorldChangeHandler;
import me.haileykins.ApocalypticSurvival.Handlers.ZombieDeathHandler;
import me.haileykins.ApocalypticSurvival.ScoreHandlers.PlayerScore;
import me.haileykins.ApocalypticSurvival.ScoreHandlers.PlayerScores;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

@SuppressWarnings("unused")

public class ApocalypticSurvival extends JavaPlugin implements Listener {
	
    private boolean isGameWorld(World world) {
        return world.getName().equalsIgnoreCase("ApocalypseSurvival");

    }
    public boolean inGameWorld(Player p){
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
        PlayerScores.initialize(this);
        PlayerScores.loadScores();

        // Register Commands
        getCommand("asreset").setExecutor(new CommandReset());

        // Register Listeners
        getServer().getPluginManager().registerEvents(new ZombieDeathHandler(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathHandler(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinHandler(this), this);
        getServer().getPluginManager().registerEvents(new WorldChangeHandler(this), this);

        // Generate World if World Does Not Exist
        if (!gameWorldExists()) {
            getLogger().info("WORLD NOT FOUND, CREATING!");
            WorldCreator wc = new WorldCreator("ApocalypseSurvival");
            World world = wc.createWorld();
            world.setDifficulty(Difficulty.HARD);
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

        PlayerScore score = PlayerScores.getScore(player);

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
}
