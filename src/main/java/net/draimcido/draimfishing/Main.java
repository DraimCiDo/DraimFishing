package net.draimcido.draimfishing;

import net.draimcido.draimfishing.competition.CompetitionSchedule;
import net.draimcido.draimfishing.competition.bossbar.BossBarManager;
import net.draimcido.draimfishing.helper.LibraryLoader;
import net.draimcido.draimfishing.hook.Placeholders;
import net.draimcido.draimfishing.listener.*;
import net.draimcido.draimfishing.utils.AdventureUtil;
import net.draimcido.draimfishing.utils.ConfigUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.draimcido.draimfishing.command.Execute;
import net.draimcido.draimfishing.command.TabComplete;
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
        Bukkit.getPluginManager().registerEvents(new FishListener(),this);

        AdventureUtil.consoleMessage("<gradient:#a8ff78:#78ffd6>[DraimFishing] </gradient>Running on <white>" + Bukkit.getVersion());

        Bukkit.getScheduler().runTaskAsynchronously(this, ()-> {

            ConfigReader.Reload();

            if (ConfigReader.Config.competition){
                competitionSchedule = new CompetitionSchedule();
                competitionSchedule.checkTime();
                Bukkit.getScheduler().runTask(this, () -> Bukkit.getPluginManager().registerEvents(new BossBarManager(), this));
            }

            if (ConfigReader.Config.papi){
                placeholders = new Placeholders();
                Bukkit.getScheduler().runTask(this, () -> {
                    placeholders.register();
                    Bukkit.getPluginManager().registerEvents(new PapiUnregister(), this);
                });
            }

            if (ConfigReader.Config.preventPick)
                Bukkit.getScheduler().runTask(this, () -> Bukkit.getPluginManager().registerEvents(new PickUpListener(),this));
            if (!Objects.equals(ConfigReader.Config.version, "8"))
                ConfigUtil.update();
            if (ConfigReader.Config.convertMMOItems)
                Bukkit.getScheduler().runTask(this, () -> Bukkit.getPluginManager().registerEvents(new MMOItemsListener(), this));
            if (ConfigReader.Config.disableJobXp)
                Bukkit.getScheduler().runTask(this, () -> Bukkit.getPluginManager().registerEvents(new JobsListener(), this));

            ConfigReader.tryEnableJedis();

            AdventureUtil.consoleMessage("<gradient:#a8ff78:#78ffd6>[DraimFishing] </gradient><color:#E1FFFF>Plugin Enabled!");
        });
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
