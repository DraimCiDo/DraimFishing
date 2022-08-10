package net.draimcido.draimfishing.item;

import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bait implements Item{

    private String name;
    private List<String> lore;
    private Map<String,Object> nbt;
    private HashMap<String, Double> weightMQ;
    private HashMap<String, Integer> weightPM;
    private double time;
    private double scoreModifier;
    private double doubleLoot;
    private int difficulty;
    private final String material;
    private List<net.draimcido.draimfishing.utils.Enchantment> enchantment;
    private List<ItemFlag> itemFlags;
    private int custommodeldata;
    private boolean unbreakable;

    public Bait(String material) {
        this.material = material;
    }

    public void setName(String name) {this.name = name;}
    public void setItemFlags(List<ItemFlag> itemFlags) {this.itemFlags = itemFlags;}
    public void setDifficulty(int difficulty) {this.difficulty = difficulty;}
    public void setNbt(Map<String,Object> nbt) {this.nbt = nbt;}
    public void setLore(List<String> lore) {this.lore = lore;}
    public void setTime(double time) {this.time = time;}
    public void setWeightMQ(HashMap<String, Double> weightMQ) {this.weightMQ = weightMQ;}
    public void setWeightPM(HashMap<String, Integer> weightPM) {this.weightPM = weightPM;}
    public void setDoubleLoot(double doubleLoot) {this.doubleLoot = doubleLoot;}
    public void setEnchantment(List<net.draimcido.draimfishing.utils.Enchantment> enchantment) {this.enchantment = enchantment;}
    public void setCustommodeldata(int custommodeldata){this.custommodeldata = custommodeldata;}
    public void setUnbreakable(boolean unbreakable){this.unbreakable = unbreakable;}
    public void setScoreModifier(double scoreModifier) {this.scoreModifier = scoreModifier;}

    public double getDoubleLoot() {return this.doubleLoot;}
    public int getDifficulty() {return difficulty;}
    public double getTime() {return time;}
    public HashMap<String, Double> getWeightMQ() {return weightMQ;}
    public HashMap<String, Integer> getWeightPM() {return weightPM;}
    public double getScoreModifier() {return scoreModifier;}

    @Override
    public boolean isUnbreakable() {return this.unbreakable;}
    @Override
    public int getCustomModelData() {return this.custommodeldata;}
    @Override
    public List<String> getLore() {return lore;}
    @Override
    public Map<String,Object> getNbt() {return nbt;}
    @Override
    public String getMaterial() {return this.material;}
    @Override
    public List<net.draimcido.draimfishing.utils.Enchantment> getEnchantments() {return this.enchantment;}
    @Override
    public List<ItemFlag> getItemFlags() {return this.itemFlags;}
    @Override
    public String getName() {return this.name;}
}
