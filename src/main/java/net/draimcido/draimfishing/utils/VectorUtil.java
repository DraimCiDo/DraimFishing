package net.draimcido.draimfishing.utils;

public record VectorUtil(double horizontal, double vertical) {

    public double getHorizontal() {
        return this.horizontal;
    }

    public double getVertical() {
        return this.vertical;
    }
}
