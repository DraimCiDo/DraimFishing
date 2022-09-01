package net.draimcido.draimfishing.competition;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalTime;

public class CompetitionSchedule {

    public static Competition competition;
    public static boolean hasBossBar;
    private int doubleCheckTime;
    private int checkTaskID;

    public CompetitionSchedule(){
        hasBossBar = false;
    }

    public static boolean startCompetition(String competitionName){
        CompetitionConfig competitionConfig = ConfigReader.CompetitionsC.get(competitionName);
        if (competitionConfig == null) return false;
        if (competition != null && competition.isGoingOn()){
            competition.end();
        }
        competition = new Competition(competitionConfig);
        competition.begin(true);
        hasBossBar = competitionConfig.isEnableBossBar();
        return true;
    }

    public static void endCompetition(){
        if (competition != null){
            competition.end();
        }
    }

    public static void cancelCompetition(){
        if (competition != null){
            competition.cancel();
        }
    }

    public void startCompetition(CompetitionConfig competitionConfig){
        if (competition != null && competition.isGoingOn()){
            competition.end();
        }
        competition = new Competition(competitionConfig);
        competition.begin(false);
        hasBossBar = competitionConfig.isEnableBossBar();
    }

    public void checkTime() {
        BukkitTask checkTimeTask = new BukkitRunnable(){
            public void run(){
                if (isANewMinute()){
                    CompetitionConfig competitionConfig = ConfigReader.CompetitionsT.get(getCurrentTime());
                    if (competitionConfig != null){
                        startCompetition(competitionConfig);
                    }
                }
            }
        }.runTaskTimer(Main.instance, (60- LocalTime.now().getSecond())*20, 1200);
        checkTaskID = checkTimeTask.getTaskId();
    }

    public void stopCheck(){
        Bukkit.getScheduler().cancelTask(checkTaskID);
    }

    public String getCurrentTime() {
        return LocalTime.now().getHour() + ":" + String.format("%02d", LocalTime.now().getMinute());
    }

    private boolean isANewMinute() {
        int minute = LocalTime.now().getMinute();
        if (doubleCheckTime != minute) {
            doubleCheckTime = minute;
            return true;
        }else {
            return false;
        }
    }
}
