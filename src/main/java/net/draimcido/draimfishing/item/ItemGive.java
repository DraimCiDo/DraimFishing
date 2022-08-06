package net.draimcido.draimfishing.item;

import net.draimcido.draimfishing.ConfigReader;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemGive {

    public static void givePlayerLoot(Player player, String lootKey, int amount){
        ItemStack itemStack = ConfigReader.LOOTITEM.get(lootKey);
        if (itemStack == null) return;
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }

    public static void givePlayerRod(Player player, String rodKey, int amount){
        ItemStack itemStack = ConfigReader.RODITEM.get(rodKey);
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }

    public static void givePlayerBait(Player player, String baitKey, int amount){
        ItemStack itemStack = ConfigReader.BAITITEM.get(baitKey);
        if (itemStack == null) return;
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }

    public static void givePlayerUtil(Player player, String utilKey, int amount){
        ItemStack itemStack = ConfigReader.UTILITEM.get(utilKey);
        if (itemStack == null) return;
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }
}
