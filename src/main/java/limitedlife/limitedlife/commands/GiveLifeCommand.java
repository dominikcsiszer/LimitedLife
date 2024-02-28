package limitedlife.limitedlife.commands;

import limitedlife.limitedlife.managers.PlayerLivesManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveLifeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 1) {
                String targetPlayerName = args[0];
                Player targetPlayer = sender.getServer().getPlayer(targetPlayerName);

                if(targetPlayer == ((Player) sender).getPlayer()) {
                    sender.sendMessage("You cant give a life for yourself.");
                }

                if(targetPlayer != null) {
                    if(PlayerLivesManager.getInstance().getPlayerLives(((Player) sender).getPlayer()) <= 1) {
                        sender.sendMessage("You cant give your last life.");
                    } else {
                        sender.sendMessage("You give a life for " + ChatColor.YELLOW + "" + targetPlayer.getName());
                        targetPlayer.sendMessage("You received a life from " + ChatColor.YELLOW + "" + sender.getName());
                        targetPlayer.sendTitle("You received a life", "", 10, 40, 10);

                        // Set the lifes
                        PlayerLivesManager.getInstance().increasePlayerLives(targetPlayer);
                        PlayerLivesManager.getInstance().decreasePlayerLives(((Player) sender).getPlayer());
                        // Effects
                        PlayerLivesManager.getInstance().receivelifeEffect(targetPlayer);
                        PlayerLivesManager.getInstance().givelifeEffect(((Player) sender).getPlayer());
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Player not found.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage: /givelife <playerName>");
            }
        }
        return true;
    }
}
