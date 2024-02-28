package limitedlife.limitedlife.managers;

import limitedlife.limitedlife.quests.QuestVillager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VillagerManager {
    private List<QuestVillager> questVillagers;

    public VillagerManager() {
        this.questVillagers = new ArrayList<>();
        loadVillagersFromConfig();
    }

    private void loadVillagersFromConfig() {
        File configFile = new File("plugins/LimitedLife/questvillagers.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        if (config.contains("villagers")) {
            ConfigurationSection villagersSection = config.getConfigurationSection("villagers");
            for (String key : villagersSection.getKeys(false)) {
                ConfigurationSection villagerSection = villagersSection.getConfigurationSection(key);

                String customName = villagerSection.getString("customName");
                List<String> story = villagerSection.getStringList("story");
                String worldName = villagerSection.getString("location.world");
                double x = villagerSection.getDouble("location.x");
                double y = villagerSection.getDouble("location.y");
                double z = villagerSection.getDouble("location.z");

                QuestVillager questVillager = new QuestVillager();
                questVillager.createVillager(customName, story, worldName, x, y, z);
                questVillagers.add(questVillager);
            }
        }
    }

    public void spawnVillagers(Player player) {
        for (QuestVillager questVillager : questVillagers) {
            questVillager.createVillager(player);
        }
    }
}
