package net.draimcido.draimfishing.timer;

import net.draimcido.draimfishing.Main;
import net.draimcido.draimfishing.bar.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Timer {

    private final TimerTask timerTask;
    private final BukkitTask task;
    private final String layout;

    public Timer(Player player, Difficulty difficulty, String layout) {
        this.layout = layout;
        this.timerTask = new TimerTask(player, difficulty, layout);
        this.task = timerTask.runTaskTimerAsynchronously(Main.instance, 1,1);
        timerTask.setTaskID(task.getTaskId());
    }

    public TimerTask getTimerTask(){ return this.timerTask; }
    public int getTaskID (){ return this.task.getTaskId(); }
    public String getLayout(){return this.layout;}
}
