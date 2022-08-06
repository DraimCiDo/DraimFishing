package net.draimcido.draimfishing.competition.reward;

import net.draimcido.draimfishing.utils.AdventureManager;
import org.bukkit.entity.Player;

import java.util.List;

public class Message implements Reward{

    private final List<String> messages;

    public Message(List<String> messages){
        this.messages = messages;
    }

    @Override
    public void giveReward(Player player) {
        if (!player.isOnline()) return;
        messages.forEach(message -> {
            AdventureManager.playerMessage(player, message);
        });
    }
}
