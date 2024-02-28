package limitedlife.limitedlife.listener;

import limitedlife.limitedlife.managers.PlayerLivesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        PlayerLivesManager.getInstance().decreasePlayerLives(player);
    }
}
