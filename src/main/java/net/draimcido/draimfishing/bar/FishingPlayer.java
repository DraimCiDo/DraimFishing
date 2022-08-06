package net.draimcido.draimfishing.bar;

import net.draimcido.draimfishing.timer.Timer;

public record FishingPlayer(Long fishingTime, Timer timer) {

    public Long getFishingTime() {
        return this.fishingTime;
    }

    public Timer getTimer() {
        return this.timer;
    }
}
