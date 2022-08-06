package net.draimcido.draimfishing.titlebar;

import net.draimcido.draimfishing.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

    private final TimerTask timerTask;
    private final BukkitTask task;
    private final String layout;

    public Timer(Player player, Difficulty difficulty, String layout) {
        this.layout = layout;
        this.timerTask = new TimerTask(player, difficulty, layout);
        this.task = timerTask.runTaskTimerAsynchronously(Main.instance, 0,1);
        timerTask.setTaskID(task.getTaskId());
    }

    public TimerTask getTimerTask(){ return this.timerTask; }
    public int getTaskID (){ return this.task.getTaskId(); }
    public String getLayout(){return this.layout;}
}
