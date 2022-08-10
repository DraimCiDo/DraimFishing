package net.draimcido.draimfishing.competition;

import net.draimcido.draimfishing.competition.bossbar.BossBarConfig;
import net.draimcido.draimfishing.competition.reward.Reward;

import java.util.HashMap;
import java.util.List;

public class CompetitionConfig {

    private int duration;
    private int minPlayers;
    private List<String> startMessage;
    private List<String> endMessage;
    private Goal goal;
    private BossBarConfig bossBarConfig;
    private final boolean enableBossBar;
    private HashMap<String, List<Reward>> rewards;

    public CompetitionConfig(boolean enableBossBar){this.enableBossBar = enableBossBar;}

    public void setDuration(int duration) {this.duration = duration;}
    public void setBossBarConfig(BossBarConfig bossBarConfig) {this.bossBarConfig = bossBarConfig;}
    public void setGoal(Goal goal) {this.goal = goal;}
    public void setEndMessage(List<String> endMessage) {this.endMessage = endMessage;}
    public void setStartMessage(List<String> startMessage) {this.startMessage = startMessage;}
    public void setMinPlayers(int minPlayers) {this.minPlayers = minPlayers;}
    public HashMap<String, List<Reward>> getRewards() {return rewards;}

    public Goal getGoal() {return goal;}
    public int getMinPlayers() {return minPlayers;}
    public int getDuration() {return duration;}
    public BossBarConfig getBossBarConfig() {return bossBarConfig;}
    public boolean isEnableBossBar() {return enableBossBar;}
    public List<String> getEndMessage() {return endMessage;}
    public List<String> getStartMessage() {return startMessage;}
    public void setRewards(HashMap<String, List<Reward>> rewards) {this.rewards = rewards;}
}
