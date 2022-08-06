package net.draimcido.draimfishing.item;

import net.draimcido.draimfishing.utils.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Map;

public interface Item {
    String getMaterial();
    List<Enchantment> getEnchantments();
    List<ItemFlag> getItemFlags();
    String getName();
    List<String> getLore();
    Map<String,Object> getNbt();
    int getCustomModelData();
    boolean isUnbreakable();
}
