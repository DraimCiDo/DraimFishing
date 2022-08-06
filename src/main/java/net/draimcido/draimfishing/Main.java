package net.draimcido.draimfishing;

import net.draimcido.draimfishing.competition.CompetitionSchedule;
import net.draimcido.draimfishing.competition.bossbar.BossBarManager;
import net.draimcido.draimfishing.utils.AdventureManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.draimcido.draimfishing.command.Execute;
import net.draimcido.draimfishing.command.TabComplete;
import net.draimcido.draimfishing.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static JavaPlugin instance;
    public static BukkitAudiences adventure;
    private CompetitionSchedule competitionSchedule;

    @Override
    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(this);
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
