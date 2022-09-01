package net.draimcido.draimfishing.object;

public class Modifier {

    private int difficulty;
    private double score;
    private boolean willDouble;
    private boolean isVanilla;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isWillDouble() {
        return willDouble;
    }

    public void setWillDouble(boolean willDouble) {
        this.willDouble = willDouble;
    }

    public boolean isVanilla() {
        return isVanilla;
    }

    public void setVanilla(boolean vanilla) {
        isVanilla = vanilla;
    }
}
