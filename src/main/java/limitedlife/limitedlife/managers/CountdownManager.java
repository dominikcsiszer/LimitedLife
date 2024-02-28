package limitedlife.limitedlife.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class CountdownManager {

    private final Plugin plugin;
    private boolean countdownRunning;
    private int countdownSeconds;
    private BukkitTask countdownTask;
    private Consumer<Player> countdownCallback; // Callback function

    public CountdownManager(Plugin plugin) {
        this.plugin = plugin;
        this.countdownRunning = false;
        this.countdownSeconds = 3; // Set your desired countdown time
        this.countdownCallback = null;
    }

    public void startCountdown(Consumer<Player> callback) {
        if (!countdownRunning) {
            countdownRunning = true;
            countdownCallback = callback; // Set the callback function

            countdownTask = new BukkitRunnable() {
                @Override
                public void run() {
                    countdown();
                }
            }.runTaskTimer(plugin, 20L, 20L);
        } else {
            // Countdown is already running
            // You can send a message to the player indicating that the countdown is already in progress
        }
    }

    private void countdown() {
        if (countdownSeconds > 0) {
            // Broadcast the countdown title to all online players
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 10f, 10f);
                sendTitle(player, ChatColor.GREEN + "" + countdownSeconds, "");
            }
            countdownSeconds--;
        } else {
            // Countdown has reached zero, execute the callback function
            if (countdownCallback != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    countdownCallback.accept(player);
                }
            }

            // Reset the countdown time for the next countdown
            countdownSeconds = 3; // Set your desired countdown time
            stopCountdown();
        }
    }

    private void stopCountdown() {
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownTask = null;
            countdownRunning = false;
        }
    }

    private void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 10, 30, 10);
    }
}
