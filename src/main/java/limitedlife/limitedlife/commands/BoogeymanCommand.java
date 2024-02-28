package limitedlife.limitedlife.commands;

import limitedlife.limitedlife.LimitedLifePlugin;
import limitedlife.limitedlife.managers.BoogeymanManager;
import limitedlife.limitedlife.schedulers.BoogeymanSchedule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BoogeymanCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private BoogeymanManager boogeymanManager;

    public BoogeymanCommand() {
        this.plugin = LimitedLifePlugin.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("choose")) {
            if(sender.hasPermission("limitedlifeplugin.boogey.choose")) {
                if(args.length == 1) {
                    BoogeymanManager.getInstance().chooseRandomBoogeyman();
                    sender.sendMessage("Start choosing!");
                } else {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /boogey choose.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
            }
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("fail")) {
            if(sender.hasPermission("limitedlifeplugin.boogey.fail")) {
                if(args.length == 2) {
                    String targetPlayerName = args[1];
                    Player targetPlayer = sender.getServer().getPlayer(targetPlayerName);

                    if(targetPlayer != null ) {
                        BoogeymanManager.getInstance().failTask(sender, targetPlayer);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /boogey fail <playerName>.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
            }
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("success")) {
            if(sender.hasPermission("limitedlifeplugin.boogey.success")) {
                if(args.length == 2) {
                    String targetPlayerName = args[1];
                    Player targetPlayer = sender.getServer().getPlayer(targetPlayerName);

                    if(targetPlayer != null ) {
                        BoogeymanManager.getInstance().successTask(sender, targetPlayer);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /boogey success <playerName>.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
            }
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("set")) {
            if(sender.hasPermission("limitedlifeplugin.boogey.set")) {
                if(args.length == 2) {
                    String targetPlayerName = args[1];
                    Player targetPlayer = sender.getServer().getPlayer(targetPlayerName);

                    if(targetPlayer != null ) {
                        BoogeymanManager.getInstance().setCurrentBoogeyman(targetPlayer);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /boogey set <playerName>.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
            }
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("stop")) {
            if(sender.hasPermission("limitedlifeplugin.boogey.stop")) {
                if(args.length == 1) {
                    if(BoogeymanSchedule.getInstance().isRunning) {
                        BoogeymanSchedule.getInstance().stop();
                    } else {
                        sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "The Boogeyman timer is not running.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /boogey stop.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
            }
        }

        return true;
    }
}
