package limitedlife.limitedlife.schedulers;

import limitedlife.limitedlife.LimitedLifePlugin;
import limitedlife.limitedlife.managers.BoogeymanManager;
import limitedlife.limitedlife.schedulers.BoogeymanSchedule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NightStartScheduler extends BukkitRunnable {

    private final LimitedLifePlugin plugin;
    private final World world;
    private final long timeNight;

    public NightStartScheduler(LimitedLifePlugin plugin, World world, long timeNight) {
        this.plugin = plugin;
        this.world = world;
        this.timeNight = timeNight;
    }

    @Override
    public void run() {
        if (world.getTime() == timeNight) {
            Bukkit.getLogger().info("Night has started!");

            // Fail the task for the current boogeyman if there is one
            Player currentBoogeyman = BoogeymanManager.getInstance().getCurrentBoogeyman();
            if (currentBoogeyman != null) {
                BoogeymanManager.getInstance().failTask(currentBoogeyman, currentBoogeyman);
            }

            // Start the 10-minute timer for choosing the boogeyman
            startBoogeymanTimer();
        }
    }

    private void startBoogeymanTimer() {
        BoogeymanSchedule.getInstance().start();
    }
}
