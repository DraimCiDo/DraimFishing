package net.draimcido.draimfishing.object;

import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Map;

public class Item {

    private final String material;
    private String name;
    private List<String> lore;
    private List<ItemFlag> itemFlags;
    private int customModelData;
    private boolean unbreakable;
    private List<LeveledEnchantment> enchantment;
    private Map<String, Object> nbt;

    public Item(String material) {
        this.material = material;
    }

    public Map<String, Object> getNbt() {
        return nbt;
    }

    public void setNbt(Map<String, Object> nbt) {
        this.nbt = nbt;
    }

    public String getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(List<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public List<LeveledEnchantment> getEnchantment() {
        return enchantment;
    }

    public void setEnchantment(List<LeveledEnchantment> enchantment) {
        this.enchantment = enchantment;
    }
}
