package net.draimcido.draimfishing.object;

import org.bukkit.NamespacedKey;

public class LeveledEnchantment {

    private final NamespacedKey key;
    private final int level;
    private double chance;

    public LeveledEnchantment(NamespacedKey key, int level){
        this.key = key;
        this.level = level;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getLevel() {
        return level;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public double getChance() {
        return chance;
    }
}
