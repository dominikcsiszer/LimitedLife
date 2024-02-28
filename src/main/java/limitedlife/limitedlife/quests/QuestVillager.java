package limitedlife.limitedlife.quests;

import limitedlife.limitedlife.LimitedLifePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuestVillager implements Listener {
    private Villager villager;
    private String[] story;

    public QuestVillager() {
        this.villager = null;
        this.story = null;
    }

    public void createVillager(String customName, List<String> story, String worldName, double x, double y, double z) {
        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            Location location = new Location(world, x, y, z);
            this.villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);

            villager.setAI(false);

            // set the name
            villager.setCustomName(ChatColor.DARK_GREEN + customName);
            villager.setCustomNameVisible(true);

            // Set the villager's equipment (optional)
            villager.getEquipment().setItemInMainHand(new ItemStack(org.bukkit.Material.DIAMOND_SWORD));

            // Set the story
            this.story = story.toArray(new String[0]);
        } else {
            Bukkit.getLogger().warning("World '" + worldName + "' not found.");
        }
    }

    public void createVillager(Player player) {
        if (villager != null) {
            // get location
            Location playerLocation = player.getLocation();
            Location npcLocation = new Location(player.getWorld(), playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
            villager.teleport(npcLocation);
        }
    }

    @EventHandler
    public void onPlayerRightClickVillager(PlayerInteractEntityEvent event) {
        if (villager != null && villager.getType() == EntityType.VILLAGER && villager.getCustomName() != null) {
            if (event.getRightClicked().equals(villager)) {
                // Cancel the event to prevent normal interaction with the Villager
                event.setCancelled(true);

                // Send the story to the player with a delay between messages
                sendStory(event.getPlayer());
            }
        }
    }

    public void sendStory(Player player) {
        if (villager != null && story != null) {
            new BukkitRunnable() {
                int index = 0;

                @Override
                public void run() {
                    if (index < story.length) {
                        player.sendMessage(ChatColor.DARK_GREEN + villager.getCustomName() + ": " + ChatColor.GREEN + story[index]);
                        index++;
                    } else {
                        this.cancel(); // Stop the task when the story is finished
                    }
                }
            }.runTaskTimer(LimitedLifePlugin.getInstance(), 0L, 60L); // Delay between messages: 60 ticks (3 seconds)
        }
    }
}
