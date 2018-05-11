package me.haileykins.ApocalypticSurvival.Commands;

import me.haileykins.ApocalypticSurvival.ScoreHandlers.PlayerScores;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandReset implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Player> players = Bukkit.getServer().matchPlayer(args[0]);
        if (players.size() != 1) {
            sender.sendMessage("Player not found.");
        } else {
            Player player = players.get(0);
            PlayerScores.resetStats(player);
        }
        return true;
    }
}
