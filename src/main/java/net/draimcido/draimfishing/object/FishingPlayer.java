package net.draimcido.draimfishing.object;

import net.draimcido.draimfishing.titlebar.Timer;

public record FishingPlayer(Long fishingTime, Timer timer) {

    public Long getFishingTime() {
        return this.fishingTime;
    }

    public Timer getTimer() {
        return this.timer;
    }
}
