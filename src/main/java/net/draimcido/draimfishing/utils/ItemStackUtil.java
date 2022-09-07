package net.draimcido.draimfishing.utils;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.draimcido.draimfishing.object.Item;
import net.draimcido.draimfishing.object.LeveledEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class ItemStackUtil {

    public static ItemStack getFromItem(Item item){
        ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial().toUpperCase()));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (item.getCustomModelData() != 0){
            itemMeta.setCustomModelData(item.getCustomModelData());
        }
        if (item.isUnbreakable()){
            itemMeta.setUnbreakable(true);
        }
        if (item.getItemFlags() != null){
            item.getItemFlags().forEach(itemMeta::addItemFlags);
        }
        if (item.getEnchantment() != null) {
            if (itemStack.getType() == Material.ENCHANTED_BOOK){
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta)itemMeta;
                item.getEnchantment().forEach(enchantment -> meta.addStoredEnchant(Objects.requireNonNull(Enchantment.getByKey(enchantment.getKey())),enchantment.getLevel(),true));
                itemStack.setItemMeta(meta);
            }else {
                item.getEnchantment().forEach(enchantment -> itemMeta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(enchantment.getKey())),enchantment.getLevel(),true));
                itemStack.setItemMeta(itemMeta);
            }
        }
        else {
            itemStack.setItemMeta(itemMeta);
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        if (item.getName() != null){
            NBTCompound display = nbtItem.addCompound("display");
            String name  = item.getName();
            if (name.contains("&") || name.contains("§")){
                name = name.replaceAll("&","§");
                display.setString("Name", GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(name)));
            }else {
                display.setString("Name", GsonComponentSerializer.gson().serialize(MiniMessage.miniMessage().deserialize("<!i>" + name)));
            }
        }
        if(item.getLore() != null){
            NBTCompound display = nbtItem.addCompound("display");
            List<String> lore = display.getStringList("Lore");
            item.getLore().forEach(line -> {
                if (line.contains("&") || line.contains("§")){
                    line = line.replaceAll("&","§");
                    lore.add(GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(line)));
                }else {
                    lore.add(GsonComponentSerializer.gson().serialize(MiniMessage.miniMessage().deserialize("<!i>" + line)));
                }
            });
        }
        if (item.getNbt() != null){
            NBTUtil.setTags(item.getNbt(), nbtItem);
        }
        return nbtItem.getItem();
    }

    public static void addRandomEnchants(ItemStack itemStack, List<LeveledEnchantment> enchantments){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemStack.getType() == Material.ENCHANTED_BOOK){
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)itemMeta;
            enchantments.forEach(enchantment -> {
                if (enchantment.getChance() > Math.random()){
                    meta.addStoredEnchant(Objects.requireNonNull(Enchantment.getByKey(enchantment.getKey())),enchantment.getLevel(),true);
                }
            });
            itemStack.setItemMeta(meta);
        }
        else {
            enchantments.forEach(enchantment -> {
                if (enchantment.getChance() > Math.random()){
                    itemMeta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(enchantment.getKey())),enchantment.getLevel(),true);
                }
            });
            itemStack.setItemMeta(itemMeta);
        }
    }

    public static void addRandomDamage(ItemStack itemStack){
        if (itemStack.getItemMeta() instanceof Damageable damageable){
            damageable.setDamage((int) (itemStack.getType().getMaxDurability() * Math.random()));
            itemStack.setItemMeta(damageable);
        }
    }

    public static void addOwner(ItemStack itemStack, String name){
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("M_Owner", name);
        itemStack.setItemMeta(nbtItem.getItem().getItemMeta());
    }
}
