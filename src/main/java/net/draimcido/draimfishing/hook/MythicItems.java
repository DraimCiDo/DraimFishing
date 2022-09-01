package net.draimcido.draimfishing.hook;

import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.inventory.ItemStack;

public class MythicItems {

    public static ItemStack getItemStack(String name){
        return MythicBukkit.inst().getItemManager().getItemStack(name);
    }
}
