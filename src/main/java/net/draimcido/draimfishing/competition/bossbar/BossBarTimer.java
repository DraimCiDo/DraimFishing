package net.draimcido.draimfishing.competition.bossbar;

import net.draimcido.draimfishing.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class BossBarTimer {

    private HashMap<Integer, BossBarSender> bossbarCache = new HashMap<>();

    public BossBarTimer(Player player, BossBarConfig bossBarConfig){

        BossBarSender bossbar = new BossBarSender(player, bossBarConfig);
        bossbar.showBossbar();
        BukkitTask task = bossbar.runTaskTimerAsynchronously(Main.instance, 0,1);
        bossbarCache.put(task.getTaskId(), bossbar);

    }

    public void stopTimer(){
        bossbarCache.forEach((key,value)-> {
            value.hideBossbar();
            Bukkit.getScheduler().cancelTask(key);
        });
        bossbarCache = null;
    }
}
