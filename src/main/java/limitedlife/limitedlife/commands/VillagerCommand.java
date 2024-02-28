package limitedlife.limitedlife.commands;

import limitedlife.limitedlife.managers.VillagerManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class VillagerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("npc.create")) {
            if(args.length == 1) {
                String npcName = args[0];

                sender.sendMessage("Custom villager created. Name: " + ChatColor.GREEN + npcName);
            } else {
                sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "Invalid usage. /npc <name>.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You do not have permission to use this command.");
        }

        return true;
    }
}
