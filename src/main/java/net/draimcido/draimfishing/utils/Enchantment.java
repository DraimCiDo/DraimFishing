package net.draimcido.draimfishing.utils;

import org.bukkit.NamespacedKey;

public record Enchantment(NamespacedKey key, int level) {

    public int getLevel() {
        return level;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
