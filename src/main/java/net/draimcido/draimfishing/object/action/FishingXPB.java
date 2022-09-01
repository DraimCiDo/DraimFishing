package net.draimcido.draimfishing.object.action;

import net.draimcido.draimfishing.ConfigReader;
import org.bukkit.entity.Player;

public record FishingXPB(int amount) implements ActionB {

    @Override
    public void doOn(Player player) {
        if (ConfigReader.Config.skillXP != null){
            ConfigReader.Config.skillXP.addXp(player, amount);
        }
    }
}
