package net.draimcido.draimfishing.titlebar;

public record Difficulty(int timer, int speed) {

    public int getTimer() {
        return this.timer;
    }

    public int getSpeed() {
        return this.speed;
    }

}
