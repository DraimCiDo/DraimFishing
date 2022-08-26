package net.draimcido.draimfishing;

import net.draimcido.draimfishing.competition.CompetitionSchedule;
import net.draimcido.draimfishing.competition.bossbar.BossBarManager;
import net.draimcido.draimfishing.helper.LibraryLoader;
import net.draimcido.draimfishing.hook.Placeholders;
import net.draimcido.draimfishing.listener.MMOItemsConverter;
import net.draimcido.draimfishing.listener.PapiReload;
import net.draimcido.draimfishing.utils.AdventureManager;
import net.draimcido.draimfishing.utils.UpdateConfig;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.draimcido.draimfishing.command.Execute;
import net.draimcido.draimfishing.command.TabComplete;
import net.draimcido.draimfishing.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static JavaPlugin instance;
    public static BukkitAudiences adventure;
    public static MiniMessage miniMessage;
    private CompetitionSchedule competitionSchedule;
    public static Placeholders placeholders;

    @Override
    public void onLoad() {
        instance = this;
        LibraryLoader.load("redis.clients","jedis","4.2.3","https://repo.maven.apache.org/maven2/");
        LibraryLoader.load("org.apache.commons","commons-pool2","2.11.1","https://repo.maven.apache.org/maven2/");
        LibraryLoader.load("dev.dejvokep","boosted-yaml","1.3","https://repo.maven.apache.org/maven2/");
    }

    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);
        miniMessage = MiniMessage.miniMessage();
        Objects.requireNonNull(Bukkit.getPluginCommand("draimfishing")).setExecutor(new Execute());
        Objects.requireNonNull(Bukkit.getPluginCommand("draimfishing")).setTabCompleter(new TabComplete());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><color:#E1FFFF>Запуск плагина на " + Bukkit.getVersion());
        ConfigReader.Reload();
        if (ConfigReader.Config.competition) {
            competitionSchedule = new CompetitionSchedule();
            competitionSchedule.checkTime();
            Bukkit.getPluginManager().registerEvents(new BossBarManager(), this);
        }
        if (ConfigReader.Config.convertMMOItems){
            Bukkit.getPluginManager().registerEvents(new MMOItemsConverter(), this);
        }
        if (ConfigReader.Config.papi){
            placeholders = new Placeholders();
            placeholders.register();
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><color:#00BFFF>PlaceholderAPI <color:#E1FFFF>привязался!");
            Bukkit.getPluginManager().registerEvents(new PapiReload(), this);
        }
        ConfigReader.tryEnableJedis();
        if (!Objects.equals(ConfigReader.Config.version, "3")){
            UpdateConfig.update();
        }
        AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><color:#E1FFFF>Плагин запущен!");
    }

    @Override
    public void onDisable() {
        if (competitionSchedule != null) {
            competitionSchedule.stopCheck();
            competitionSchedule = null;
        }
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        if (instance != null) {
            instance = null;
        }
    }
}
