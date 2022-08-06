package net.draimcido.draimfishing.command;

import net.draimcido.draimfishing.ConfigReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender.hasPermission("draimfishing.admin") || sender.isOp())) {
            return null;
        }
        if (args.length == 1){
            return Arrays.asList("competiotion","reload","items","export");
        }
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("items")){
                return Arrays.asList("loot","bait","rod","util");
            }
            if (args[0].equalsIgnoreCase("export")){
                return List.of("FileName");
            }
            if (args[0].equalsIgnoreCase("competition")) {
                return List.of("start","end","cancel");
            }
        }
        if (args.length == 3){
            if (args[0].equalsIgnoreCase("items")){
                if (args[1].equalsIgnoreCase("loot") || args[1].equalsIgnoreCase("util") || args[1].equalsIgnoreCase("rod") || args[1].equalsIgnoreCase("bait")){
                    return Arrays.asList("get","give");
                }
            }
            if (args[0].equalsIgnoreCase("competition")) {
                if (args[1].equalsIgnoreCase("start")) {
                    return competitions();
                }
            }
        }
        if (args.length == 4){
            if (args[0].equalsIgnoreCase("items")){
                if (args[1].equalsIgnoreCase("loot")){
                    if (args[2].equalsIgnoreCase("give")){
                        return online_players();
                    }
                    if (args[2].equalsIgnoreCase("get")){
                        return loots();
                    }
                }else if (args[1].equalsIgnoreCase("util")){
                    if (args[2].equalsIgnoreCase("give")){
                        return online_players();
                    }
                    if (args[2].equalsIgnoreCase("get")){
                        return utils();
                    }
                }else if (args[1].equalsIgnoreCase("rod")){
                    if (args[2].equalsIgnoreCase("give")){
                        return online_players();
                    }
                    if (args[2].equalsIgnoreCase("get")){
                        return rods();
                    }
                }else if (args[1].equalsIgnoreCase("bait")){
                    if (args[2].equalsIgnoreCase("give")){
                        return online_players();
                    }
                    if (args[2].equalsIgnoreCase("get")){
                        return baits();
                    }
                }
            }
        }
        if (args.length == 5){
            if (args[0].equalsIgnoreCase("items")){
                if (args[1].equalsIgnoreCase("loot")){
                    if (args[2].equalsIgnoreCase("give")){
                        return loots();
                    }
                }else if (args[1].equalsIgnoreCase("util")){
                    if (args[2].equalsIgnoreCase("give")){
                        return utils();
                    }
                }else if (args[1].equalsIgnoreCase("rod")){
                    if (args[2].equalsIgnoreCase("give")){
                        return rods();
                    }
                }
                else if (args[1].equalsIgnoreCase("bait")){
                    if (args[2].equalsIgnoreCase("give")){
                        return baits();
                    }
                }
            }
        }
        return null;
    }

    private List<String> online_players(){
        List<String> online = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach((player -> online.add(player.getName())));
        return online;
    }

    private List<String> loots(){
        return new ArrayList<>(ConfigReader.LOOTITEM.keySet());
    }
    private List<String> utils(){
        return new ArrayList<>(ConfigReader.UTIL.keySet());
    }
    private List<String> rods() {
        return new ArrayList<>(ConfigReader.ROD.keySet());
    }
    private List<String> baits() {
        return new ArrayList<>(ConfigReader.BAIT.keySet());
    }
    private List<String> competitions() {
        return new ArrayList<>(ConfigReader.CompetitionsCommand.keySet());
    }
}
