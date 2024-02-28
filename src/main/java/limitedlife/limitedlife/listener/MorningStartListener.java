package limitedlife.limitedlife.listener;

import limitedlife.limitedlife.LimitedLifePlugin;
import limitedlife.limitedlife.managers.BoogeymanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MorningStartListener implements Listener {

    private final JavaPlugin plugin;

    public MorningStartListener() {
        this.plugin = LimitedLifePlugin.getInstance();
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        Bukkit.getLogger().info("TimeSkipEvent fired!");
        // Check if it's the start of a new day (morning)
        Bukkit.getLogger().info("Skip reason: " + event.getSkipReason());
        if (event.getSkipReason() == TimeSkipEvent.SkipReason.COMMAND) {
            // Start the 10-minute timer for choosing the boogeyman
            Bukkit.getLogger().info("Event called!");
            Player currentBoogeyman = BoogeymanManager.getInstance().getCurrentBoogeyman();
            Bukkit.getLogger().info("Current Boogeyman: " + currentBoogeyman);
            if(currentBoogeyman != null) {
                BoogeymanManager.getInstance().failTask(currentBoogeyman, currentBoogeyman);
            }
            startBoogeymanTimer();
        }
    }

    // Method to start the 10-minute timer for choosing the boogeyman
    private void startBoogeymanTimer() {
        Bukkit.getServer().broadcastMessage(ChatColor.RED + "The Boogeyman is being chosen in 5 minutes");
        // Start a 5-minute timer to inform players
        new BukkitRunnable() {
            @Override
            public void run() {
                BoogeymanManager.getInstance().chooseRandomBoogeyman();
            }
        }.runTaskLater(plugin, 6000L);

        // Start a 4-minute timer to inform players
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "The Boogeyman is being chosen in 1 minute");
            }
        }.runTaskLater(plugin, 2400L);
    }
}


