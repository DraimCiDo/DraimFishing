package net.draimcido.draimfishing.titlebar;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.Main;
import net.draimcido.draimfishing.utils.AdventureManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import static net.draimcido.draimfishing.listener.PlayerListener.fishingPlayers;

public class TimerTask extends BukkitRunnable {

    private final Player player;
    private final Difficulty difficulty;
    private int taskID;
    private int progress;
    private int internalTimer;
    private final int size;
    private boolean face;
    private BukkitScheduler bukkitScheduler;

    private final String start;
    private final String bar;
    private final String pointer;
    private final String offset;
    private final String end;
    private final String pointerOffset;
    private final String title;

    public TimerTask(Player player, Difficulty difficulty, String layout){
        this.player = player;
        this.difficulty = difficulty;
        this.progress = 0;
        this.internalTimer = 0;
        this.face = true;
        this.bukkitScheduler = Bukkit.getScheduler();
        Layout layoutUtil = ConfigReader.LAYOUT.get(layout);
        this.start = layoutUtil.getStart();
        this.bar = layoutUtil.getBar();
        this.pointer = layoutUtil.getPointer();
        this.offset = layoutUtil.getOffset();
        this.end = layoutUtil.getEnd();
        this.pointerOffset = layoutUtil.getPointerOffset();
        this.title = layoutUtil.getTitle();
        this.size = layoutUtil.getSize();
    }

    public int getProgress() { return this.progress; }
    public void setTaskID(int taskID){
        this.taskID = taskID;
    }

    @Override
    public void run() {
        if (fishingPlayers.get(player) == null){
            bukkitScheduler.cancelTask(taskID);
            return;
        }

        if (System.currentTimeMillis() > fishingPlayers.get(player).getFishingTime()){
            fishingPlayers.remove(player);
            bukkitScheduler.cancelTask(taskID);
            return;
        }
        int timer = difficulty.getTimer() - 1;
        int speed = difficulty.getSpeed();
        if (progress <= speed - 1){
            face = true;
        }else if(progress >= size - speed + 1){
            face = false;
        }
        if (internalTimer < timer){
            internalTimer++;
            return;
        }else {
            if (face){
                internalTimer -= timer;
                progress += speed;
            }else {
                internalTimer -= timer;
                progress -= speed;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(start + bar + pointerOffset);
        for (int index = 0; index <= size; index++){
            if (index == progress){
                stringBuilder.append(pointer);
            }else {
                stringBuilder.append(offset);
            }
        }
        stringBuilder.append(end);
        AdventureManager.playerTitle(player, title, stringBuilder.toString(),0,300,0);
        PlayerInventory playerInventory = player.getInventory();
        if (playerInventory.getItemInMainHand().getType() != Material.FISHING_ROD && playerInventory.getItemInOffHand().getType() != Material.FISHING_ROD){
            fishingPlayers.remove(player);
            bukkitScheduler.cancelTask(taskID);
            bukkitScheduler.runTask(Main.instance, ()-> {
                player.removePotionEffect(PotionEffectType.SLOW);
            });
        }
    }
}
