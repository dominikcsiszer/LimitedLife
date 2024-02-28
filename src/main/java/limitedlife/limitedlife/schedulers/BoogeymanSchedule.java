package limitedlife.limitedlife.schedulers;

import limitedlife.limitedlife.LimitedLifePlugin;
import limitedlife.limitedlife.managers.BoogeymanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BoogeymanSchedule {
    private static BoogeymanSchedule instance;
    private final JavaPlugin plugin;
    private int taskId5;
    private int taskId4;

    public boolean isRunning = false;

    public BoogeymanSchedule() {
        this.plugin = LimitedLifePlugin.getInstance();
    }

    public static BoogeymanSchedule getInstance() {
        if (instance == null) {
            instance = new BoogeymanSchedule();
        }
        return instance;
    }

    public void start() {
        isRunning = true;
        Bukkit.getServer().broadcastMessage(ChatColor.RED + "The Boogeyman is being chosen in 5 minutes");

        // Start a 4-minute timer to inform players
        taskId4 = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "The Boogeyman is being chosen in 1 minute");
            }
        }.runTaskLater(plugin, 4800L).getTaskId();

        // Start a 5-minute timer to inform players
        taskId5 =  new BukkitRunnable() {
            @Override
            public void run() {
                BoogeymanManager.getInstance().chooseRandomBoogeyman();
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "The Boogeyman is about to be chosen.");
                isRunning = false;
            }
        }.runTaskLater(plugin, 6000L).getTaskId(); // 5 * 60 * 20
    }

    public void stop() {
        isRunning = false;
        // Cancel the scheduled tasks when stopping
        Bukkit.getScheduler().cancelTask(taskId5);
        Bukkit.getScheduler().cancelTask(taskId4);

        Bukkit.getServer().broadcastMessage(ChatColor.RED + "The Boogeyman timer stopped");
    }
}
