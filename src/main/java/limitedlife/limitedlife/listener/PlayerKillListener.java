package limitedlife.limitedlife.listener;

import limitedlife.limitedlife.managers.BoogeymanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerKillListener implements Listener {

    public PlayerKillListener() {
        //
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (killer != null) {
            // You can add additional logic here based on the kill event
            // For example, check if the killer is the boogeyman, etc.

            if(killer == BoogeymanManager.getInstance().getCurrentBoogeyman()) {
                BoogeymanManager.getInstance().successTask(killer, killer);
            }
        }
    }
}
