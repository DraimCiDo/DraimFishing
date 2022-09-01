package net.draimcido.draimfishing.object;

public record MobVector(double horizontal, double vertical) {

    public double getHorizontal() {
        return this.horizontal;
    }

    public double getVertical() {
        return this.vertical;
    }
}
