package limitedlife.limitedlife.managers;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerLivesManager {
    private static PlayerLivesManager instance;
    private Map<Player, Integer> playerLives;

    public PlayerLivesManager() {
        this.playerLives = new HashMap<>();
        addAllOnlinePlayers();
    }

    public static PlayerLivesManager getInstance() {
        if (instance == null) {
            instance = new PlayerLivesManager();
        }
        return instance;
    }

    public static void deleteInstance() {
        instance = null;
    }

    private void addAllOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            assignRandomLives(player);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10f, 10f);
        }
    }

    public void assignRandomLives(Player player) {
        if (!playerLives.containsKey(player)) {
            int randomLives = new Random().nextInt(5) + 2; // You can adjust the range of random lives
            playerLives.put(player, randomLives);
            sendLivesMessage(player);
            player.sendTitle(ChatColor.GREEN + "You have "+ randomLives +" lifes", "", 10, 30, 10);
        }
    }

    public void decreasePlayerLives(Player player) {
        if (playerLives.containsKey(player)) {
            int currentLives = playerLives.get(player);
            if (currentLives > 1) {
                currentLives--;
                playerLives.put(player, currentLives);
                sendLivesMessage(player);
            } else {
                // Player is out of lives, you can handle this as needed
                // For example, kick the player, respawn, or perform any other actions
                Bukkit.getServer().broadcastMessage(player.getName() + " is out of lifes!");
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    public void increasePlayerLives(Player player) {
        if (playerLives.containsKey(player)) {
            int currentLives = playerLives.get(player);
            if (currentLives > 0) {
                currentLives++;
                playerLives.put(player, currentLives);
                sendLivesMessage(player);
            }
        }
    }

    public void givelifeEffect(Player player) {
        playCustomParticleGivingEffect(player.getLocation());
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 10f, 10f);
    }

    public void receivelifeEffect(Player player) {
        playCustomParticleHealingEffect(player.getLocation());
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 10f, 10f);
    }

    private void playCustomParticleHealingEffect(Location location) {
        // Adjust particle effect parameters as needed
        location.getWorld().spawnParticle(Particle.HEART ,location, 30, 1.0, 1.0, 1.0, 0.0);
    }

    private void playCustomParticleGivingEffect(Location location) {
        // Adjust particle effect parameters as needed
        location.getWorld().spawnParticle(Particle.TOTEM, location, 50, 1.0, 1.0, 1.0, 0.0);
    }

    public void setLivesForPlayer(Player player, int amount) {
        if(amount > 0) {
            if (playerLives.containsKey(player)) {
                playerLives.put(player, amount);

                sendLivesMessage(player);
            }
        }
    }

    private void sendLivesMessage(Player player) {
        int lives = this.getPlayerLives(player);

        // Set player name color based on lives
        ChatColor nameColor;
        if (lives >= 4) {
            nameColor = ChatColor.DARK_GREEN;
        } else if (lives == 3) {
            nameColor = ChatColor.GREEN;
        } else if (lives == 2) {
            nameColor = ChatColor.YELLOW;
        } else {
            nameColor = ChatColor.RED;
        }

        setPlayerNameAboveHeadColor(player, nameColor);

        player.sendMessage("You have " + ChatColor.GREEN + "" + lives + "" + ChatColor.WHITE + " life(s) remaining");
    }

    private void setPlayerNameAboveHeadColor(Player player, ChatColor color) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(color.name());
        if (team == null) {
            team = scoreboard.registerNewTeam(color.name());
        }

        team.setColor(color);
        team.addEntry(player.getName());
    }

    // You can use this method to check the remaining lives of a player
    public int getPlayerLives(Player player) {
        return playerLives.getOrDefault(player, 0);
    }
}
