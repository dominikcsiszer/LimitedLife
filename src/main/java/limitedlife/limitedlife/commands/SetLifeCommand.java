package limitedlife.limitedlife.commands;

import limitedlife.limitedlife.managers.PlayerLivesManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLifeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("limitedlifeplugin.limitedlife.setlives")) {
            if(args.length == 2) {
                String targetPlayerName = args[0];
                Player targetPlayer = sender.getServer().getPlayer(targetPlayerName);

                try {
                    int amount = Integer.parseInt(args[1]);
                    if(targetPlayer != null) {
                        if(amount > 0) {
                            PlayerLivesManager.getInstance().setLivesForPlayer(targetPlayer, amount);
                            sender.sendMessage("You set "+ ChatColor.GREEN + amount + ChatColor.WHITE +" life(s) for " + ChatColor.YELLOW + targetPlayerName);
                        } else {
                            sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Amount must be higher than 0.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Player not found.");
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid amount. Please provide a valid integer.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /setlife <playerName> <amount>.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
        }

        return true;
    }
}
