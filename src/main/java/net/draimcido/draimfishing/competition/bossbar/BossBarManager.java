package net.draimcido.draimfishing.competition.bossbar;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.competition.CompetitionSchedule;
import net.draimcido.draimfishing.utils.AdventureManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class BossBarManager implements Listener {

    public static HashMap<Player, BossBarTimer> cache = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (CompetitionSchedule.competition != null && CompetitionSchedule.competition.isGoingOn()){
            if (CompetitionSchedule.competition.getRanking().getCompetitionPlayer(player.getName()) != null && cache.get(player) == null){
                BossBarTimer timerTask = new BossBarTimer(player, CompetitionSchedule.competition.getBossBarConfig());
                cache.put(player, timerTask);
            }else {
                AdventureManager.playerMessage(player, ConfigReader.Message.competitionOn);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        BossBarTimer timerTask = cache.get(player);
        if (timerTask != null){
            timerTask.stopTimer();
            cache.remove(player);
        }
    }

    public static void stopAllTimer(){
        cache.forEach(((player, timerTask) -> {
            timerTask.stopTimer();
        }));
        cache.clear();
    }

    public static void joinCompetition(Player player){
        if (cache.get(player) == null) {
            BossBarTimer timerTask = new BossBarTimer(player, CompetitionSchedule.competition.getBossBarConfig());
            cache.put(player, timerTask);
            if (CompetitionSchedule.competition.getJoinCommand() != null){
                CompetitionSchedule.competition.getJoinCommand().forEach(command -> {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
                });
            }
        }
    }
}
