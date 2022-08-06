package net.draimcido.draimfishing.titlebar;

public record FishingPlayer(Long fishingTime, Timer timer) {

    public Long getFishingTime() {
        return this.fishingTime;
    }

    public Timer getTimer() {
        return this.timer;
    }

}
