package net.draimcido.draimfishing.listener;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class MMOItemsListener implements Listener {

    private final HashMap<Player, Long> coolDown = new HashMap<>();

    @EventHandler
    public void onFish(PlayerFishEvent event){

        if (event.getState() == PlayerFishEvent.State.FISHING){

            Player player = event.getPlayer();

            long time = System.currentTimeMillis();
            if (time - (coolDown.getOrDefault(player, time - 5000)) < 5000) {
                return;
            }
            coolDown.put(player, time);

            PlayerInventory inventory = player.getInventory();

            ItemStack mainHand = inventory.getItemInMainHand();
            if(mainHand.getType() == Material.FISHING_ROD){
                NBTItem nbtItem = new NBTItem(mainHand);
                if (nbtItem.getCompound("DraimFishing") == null) {
                    if (!nbtItem.getString("MMOITEMS_ITEM_ID").equals("")){
                        NBTCompound nbtCompound = nbtItem.addCompound("DraimFishing");
                        nbtCompound.setString("type","rod");
                        nbtCompound.setString("id",nbtItem.getString("MMOITEMS_ITEM_ID"));
                        mainHand.setItemMeta(nbtItem.getItem().getItemMeta());
                    }
                }
            }

            ItemStack offHand = inventory.getItemInOffHand();
            if(offHand.getType() == Material.FISHING_ROD){
                NBTItem nbtItem = new NBTItem(offHand);
                if (nbtItem.getCompound("DraimFishing") == null) {
                    if (!nbtItem.getString("MMOITEMS_ITEM_ID").equals("")){
                        NBTCompound nbtCompound = nbtItem.addCompound("DraimFishing");
                        nbtCompound.setString("type", "rod");
                        nbtCompound.setString("id", nbtItem.getString("MMOITEMS_ITEM_ID"));
                        offHand.setItemMeta(nbtItem.getItem().getItemMeta());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        coolDown.remove(event.getPlayer());
    }
}
