package net.draimcido.draimfishing.competition.reward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Command implements Reward{

    private final List<String> commands;

    public Command(List<String> commands){
        this.commands = commands;
    }

    @Override
    public void giveReward(Player player) {
        commands.forEach(command -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
        });
    }
}