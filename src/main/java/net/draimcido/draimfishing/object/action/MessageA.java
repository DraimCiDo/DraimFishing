package net.draimcido.draimfishing.object.action;

import net.draimcido.draimfishing.utils.AdventureUtil;
import org.bukkit.entity.Player;

import java.util.List;

public record MessageA(List<String> messages, String loot) implements ActionB{

    @Override
    public void doOn(Player player) {
        messages.forEach(message -> {
            AdventureUtil.playerMessage(player, message.replace("{loot}", loot));
        });
    }
}
