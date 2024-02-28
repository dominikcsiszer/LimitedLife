package limitedlife.limitedlife.managers;

import limitedlife.limitedlife.LimitedLifePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoogeymanManager {
    private static Plugin plugin;
    private static BoogeymanManager instance;
    private Player currentBoogeyman;
    private CountdownManager countdownManager;

    private boolean isTaskDone = false;

    public BoogeymanManager() {
        this.currentBoogeyman = null;
        this.countdownManager = new CountdownManager(LimitedLifePlugin.getInstance());
        this.plugin = LimitedLifePlugin.getInstance();
        setTaskDone(false);
    }


    public static BoogeymanManager getInstance() {
        if (instance == null) {
            instance = new BoogeymanManager();
        }
        return instance;
    }

    public Player getCurrentBoogeyman() {
        return currentBoogeyman;
    }

    public void setCurrentBoogeyman(Player player) {
        currentBoogeyman = player;
        displayCountdownTitles(player);
    }

    public void chooseRandomBoogeyman() {
        countdownManager.startCountdown(this::startBoogeymanTitle);
    }

    private void startBoogeymanTitle(Player player) {
        // Choose a random boogeyman
        currentBoogeyman = getRandomBoogeyman();
        Bukkit.getLogger().info("Current Boogeyman: " + currentBoogeyman);
        // Display countdown titles
        displayCountdownTitles(player);
    }

    private Player getRandomBoogeyman() {
        setTaskDone(false);
        List<Player> eligiblePlayers = getEligiblePlayers();
        if (!eligiblePlayers.isEmpty()) {
            return eligiblePlayers.get(new Random().nextInt(eligiblePlayers.size()));
        }
        return null;
    }

    private List<Player> getEligiblePlayers() {
        List<Player> eligiblePlayers = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (isPlayerInTeams(player, "DARK_GREEN", "GREEN", "YELLOW")) {
                eligiblePlayers.add(player);
            }
        }

        return eligiblePlayers;
    }

    private boolean isPlayerInTeams(Player player, String... teamNames) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (String teamName : teamNames) {
            Team team = scoreboard.getTeam(teamName);
            if (team != null && team.hasEntry(player.getName())) {
                return true;
            }
        }

        return false;
    }

    private void displayCountdownTitles(Player player) {
        player.sendTitle(ChatColor.YELLOW + "You are...", "", 3, 40, 3);

        Bukkit.getScheduler().runTaskLater(LimitedLifePlugin.getInstance(), () -> {
            if (currentBoogeyman != null && currentBoogeyman.equals(player)) {
                player.sendTitle(ChatColor.RED + "The BoogeyMan", "", 10, 40, 10);
                player.sendMessage(ChatColor.GRAY + "You are the boogeyman. You must by any means necessary kill a "+ ChatColor.GREEN +"green"+ ChatColor.GRAY +" or "+ ChatColor.YELLOW +"yellow"+ ChatColor.GRAY +" name by direct action to be cures of the curse. If you fail, next session you will becom a "+ ChatColor.RED +"red name"+ ChatColor.GRAY +". All loyalties and firendships are removed while you are the boogeyman.");
            } else {
                player.sendTitle(ChatColor.GREEN + "NOT The BoogeyMan", "", 10, 40, 10);
            }
        }, 60L);
    }

    public void setTaskDone(boolean isDone) {
        isTaskDone = isDone;
    }

    public void failTask(CommandSender commandSender, Player player) {
        if(player == currentBoogeyman) {
            player.sendTitle(ChatColor.RED + "You have failed.", "", 10, 40, 10);
            player.sendMessage(ChatColor.GRAY + "You failed to kill a green or yellow name last session as the boogeyman. As punishment, you have dropped to your"+ ChatColor.RED +" Last Life"+ ChatColor.GRAY +". All alliances are severed and you are now hostile to all players. You may team with others on their Last Life if you wish.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10f, 10f);

            PlayerLivesManager.getInstance().setLivesForPlayer(player, 1);
            setTaskDone(true);
        } else {
            commandSender.sendMessage("The player not a Boogeyman.");
        }
    }

    public void successTask(CommandSender commandSender, Player player) {
        if(player == currentBoogeyman) {
            player.sendTitle(ChatColor.GREEN + "You are cured!", "", 10, 40, 10);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10f, 10f);

            currentBoogeyman = null;
            setTaskDone(true);
        } else {
            commandSender.sendMessage("The player not a Boogeyman.");
        }
    }
}
