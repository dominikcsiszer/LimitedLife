package limitedlife.limitedlife.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class GameManager {

    public static void setupWorldBorder(String worldName, int borderSize) {
        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            WorldBorder worldBorder = world.getWorldBorder();

            // Set the center of the border to (0, 0)
            worldBorder.setCenter(53, -234);

            // Set the size of the border
            worldBorder.setSize(borderSize);

            // Optionally, you can set other properties like warning distance and damage buffer
            // worldBorder.setWarningDistance(5);
            // worldBorder.setDamageBuffer(1.0);
        } else {
            // Print a warning message if the world is not found
            Bukkit.getLogger().warning("World '" + worldName + "' not found. Make sure the world name is correct.");
        }
    }
}
