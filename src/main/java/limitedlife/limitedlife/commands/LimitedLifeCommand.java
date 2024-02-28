package limitedlife.limitedlife.commands;

import limitedlife.limitedlife.LimitedLifePlugin;
import limitedlife.limitedlife.managers.BoogeymanManager;
import limitedlife.limitedlife.managers.CountdownManager;
import limitedlife.limitedlife.managers.PlayerLivesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class LimitedLifeCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private boolean gameStarted = false;
    private CountdownManager countdownManager;
    private BoogeymanManager boogeymanManager;

    public LimitedLifeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("start")) {
            if(commandSender.hasPermission("limitedlifeplugin.limitedlife.start")) {
                if (gameStarted) {
                    commandSender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "The game has already started.");
                    return true;
                }
                countdownManager = new CountdownManager(plugin);

                countdownManager.startCountdown(player -> {
                    startGame(player);
                });

                gameStarted = true;
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
                return true;
            }

        }

        if(args.length > 0 && args[0].equalsIgnoreCase("stop")) {
            if(commandSender.hasPermission("limitedlifeplugin.limitedlife.stop")) {
                Bukkit.getLogger().info("Game stopped!");
                if (!gameStarted) {
                    commandSender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "The game not started yet.");
                    return true;
                }

                stopGame();
                commandSender.sendMessage("Game has been stopped.");

                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
                return true;
            }
        }

        if(args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if(commandSender.hasPermission("limitedlifeplugin.limitedlife.stop")) {
                Plugin plugin = LimitedLifePlugin.getInstance();
                commandSender.sendMessage(ChatColor.RED + "LimitedLife Reloading!");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                plugin.getServer().getPluginManager().enablePlugin(plugin);
                commandSender.sendMessage(ChatColor.GREEN + "LimitedLife Reloaded!");
                return true;
            } else {
                commandSender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
                return true;
            }
        }

        commandSender.sendMessage("Invalid usage.");
        return false;
    }

    private void startGame(Player p) {
        PlayerLivesManager.getInstance();
    }

    private void stopGame() {
        gameStarted = false;
        PlayerLivesManager.deleteInstance();
        clearTeams();
    }

    private void clearTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }
}
