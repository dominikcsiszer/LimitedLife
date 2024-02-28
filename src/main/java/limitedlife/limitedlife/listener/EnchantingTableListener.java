package limitedlife.limitedlife.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantingTableListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (event.getRecipe().getResult().getType() == Material.ENCHANTING_TABLE) {
            // Cancel the crafting event for enchanted table
            event.getWhoClicked().sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.WHITE + "You can't craft an Enchanting Table!");
            event.setCancelled(true);
        }
    }
}
