package limitedlife.limitedlife;

import limitedlife.limitedlife.commands.*;
import limitedlife.limitedlife.listener.*;
import limitedlife.limitedlife.managers.VillagerManager;
import limitedlife.limitedlife.quests.QuestVillager;
import limitedlife.limitedlife.schedulers.NightStartScheduler;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import limitedlife.limitedlife.managers.GameManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class LimitedLifePlugin extends JavaPlugin {
    private static LimitedLifePlugin pluginInstance;
    private VillagerManager villagerManager;

    @Override
    public void onEnable() {
        pluginInstance = this;

        // Plugin startup logic
        Bukkit.getLogger().info("Limited Life started!");

        //Commands
        this.getCommand("limitedife").setExecutor(new LimitedLifeCommand(this));
        this.getCommand("givelife").setExecutor(new GiveLifeCommand());
        this.getCommand("setlife").setExecutor(new SetLifeCommand());
        this.getCommand("boogey").setExecutor(new BoogeymanCommand());
        this.getCommand("npc").setExecutor(new VillagerCommand());

        //World Border
        GameManager.setupWorldBorder("world", 24 * 16);

        //Register events
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        //getServer().getPluginManager().registerEvents(new MorningStartListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKillListener(), this);
        Bukkit.getPluginManager().registerEvents(new EnchantingTableListener(), this);
        getServer().getPluginManager().registerEvents(new QuestVillager(), this);

        World world = Bukkit.getWorld("world");
        if (world != null) {
            long timeNight = 13000; // Adjust this value based on your server's day/night cycle
            long subtractTicks = 5 * 60 * 20; // 5 minutes * 60 seconds/minute * 20 ticks/second
            timeNight -= subtractTicks;
            timeNight = (timeNight + 24000) % 24000;
            Bukkit.getLogger().info("timeNight: " + timeNight);
            new NightStartScheduler(this, world, timeNight).runTaskTimer(this, 0L, 1L);
        } else {
            getLogger().warning("World not found!");
        }

        // Create QuestVillagers
        this.villagerManager = new VillagerManager();

        // Spawn quest villagers for all online players
        getServer().getOnlinePlayers().forEach(this.villagerManager::spawnVillagers);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Shutting down");
        clearTeams();
    }
    private void clearTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }


    public static LimitedLifePlugin getInstance() {
        return pluginInstance;
    }
}
