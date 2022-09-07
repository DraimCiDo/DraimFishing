package net.draimcido.draimfishing.object.action;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.utils.AdventureUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;

public record XPB(int amount) implements ActionB {

    @Override
    public void doOn(Player player) {
        if (ConfigReader.Config.isSpigot) player.giveExp(amount);
        else player.giveExp(amount, false);
        AdventureUtil.playerSound(player, Sound.Source.PLAYER, Key.key("minecraft:entity.experience_orb.pickup"));
    }
}
