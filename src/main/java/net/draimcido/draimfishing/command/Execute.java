package net.draimcido.draimfishing.command;

import net.draimcido.draimfishing.Main;
import net.draimcido.draimfishing.competition.CompetitionSchedule;
import net.draimcido.draimfishing.utils.AdventureUtil;
import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Execute implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("draimfishing.admin") || sender.isOp())) {
            AdventureUtil.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.noPerm);
            return true;
        }

        if (args.length < 1){
            lackArgs(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            Bukkit.getScheduler().runTaskAsynchronously(Main.instance, ()-> {
                ConfigReader.Reload();
                if (sender instanceof Player){
                    AdventureUtil.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.reload);
                }else {
                    AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.reload);
                }
            });
            return true;
        }

        if (args[0].equalsIgnoreCase("import")) {
            if (args.length < 2){
                lackArgs(sender);
                return true;
            }
            if (sender instanceof Player player){
                ItemUtil.saveToFile(player.getInventory().getItemInMainHand(), args[1]);
                AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + "Done! File is saved to /DraimFishing/loots/" + args[1] + ".yml");
            }else {
                AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("competition")) {
            if (args.length < 2){
                lackArgs(sender);
                return true;
            }
            if (args[1].equalsIgnoreCase("start")){
                if (args.length < 3){
                    lackArgs(sender);
                    return true;
                }
                if (CompetitionSchedule.startCompetition(args[2])){
                    forceSuccess(sender);
                }else {
                    forceFailure(sender);
                }
            }else if (args[1].equalsIgnoreCase("end")){
                CompetitionSchedule.endCompetition();
                forceEnd(sender);
            }else if (args[1].equalsIgnoreCase("cancel")){
                CompetitionSchedule.cancelCompetition();
                forceCancel(sender);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("items")) {
            if (args.length < 4){
                lackArgs(sender);
                return true;
            }
            if (args[1].equalsIgnoreCase("loot")) {
                if (args[2].equalsIgnoreCase("get")) {
                    if (sender instanceof Player player){
                        if (ConfigReader.LootItem.containsKey(args[3])){
                            if (args.length == 4){
                                ItemUtil.givePlayerLoot(player, args[3], 1);
                                AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                            }else {
                                if (Integer.parseInt(args[4]) < 1){
                                    wrongAmount(sender);
                                    return true;
                                }
                                ItemUtil.givePlayerLoot(player, args[3], Integer.parseInt(args[4]));
                                AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                            }
                        }else if (ConfigReader.OTHERS.containsKey(args[3])){
                            if (args.length == 4){
                                player.getInventory().addItem(ItemUtil.getItemStackFromOtherPlugins(args[3]));
                                AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                            }else {
                                if (Integer.parseInt(args[4]) < 1){
                                    wrongAmount(sender);
                                    return true;
                                }
                                ItemStack itemStack = ItemUtil.getItemStackFromOtherPlugins(args[3]);
                                itemStack.setAmount(Integer.parseInt(args[4]));
                                player.getInventory().addItem(itemStack);
                                AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                            }
                        }else {
                            noItem(sender);
                            return true;
                        }

                    }else {
                        AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    if (ConfigReader.LootItem.containsKey(args[4])){
                        if (args.length == 5){
                            ItemUtil.givePlayerLoot(player, args[4], 1);
                            giveItem(sender, args[3], args[4], 1);
                            return true;
                        }
                        else {
                            if (Integer.parseInt(args[5]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            ItemUtil.givePlayerLoot(player, args[4], Integer.parseInt(args[5]));
                            giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                        }
                    }else if (ConfigReader.OTHERS.containsKey(args[4])){
                        if (args.length == 5){
                            player.getInventory().addItem(ItemUtil.getItemStackFromOtherPlugins(args[4]));
                            giveItem(sender, args[3], args[4], 1);
                            return true;
                        }
                        else {
                            if (Integer.parseInt(args[5]) < 1) {
                                wrongAmount(sender);
                                return true;
                            }
                            ItemStack itemStack = ItemUtil.getItemStackFromOtherPlugins(args[4]);
                            itemStack.setAmount(Integer.parseInt(args[5]));
                            player.getInventory().addItem(itemStack);
                            giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                        }
                    }else {
                        noItem(sender);
                        return true;
                    }
                    return true;
                }
            }
            else if(args[1].equalsIgnoreCase("util")){
                if (args[2].equalsIgnoreCase("get")) {
                    if (sender instanceof Player player){
                        if (!ConfigReader.UtilItem.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            ItemUtil.givePlayerUtil(player, args[3], 1);
                            AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            ItemUtil.givePlayerUtil(player, args[3], Integer.parseInt(args[4]));
                            AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    if (!ConfigReader.UtilItem.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        ItemUtil.givePlayerUtil(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        ItemUtil.givePlayerUtil(player, args[4], Integer.parseInt(args[5]));
                        giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                    }
                    return true;
                }
            }
            else if (args[1].equalsIgnoreCase("rod")){
                if (args[2].equalsIgnoreCase("get")) {
                    if (sender instanceof Player player){
                        if (!ConfigReader.RodItem.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            ItemUtil.givePlayerRod(player, args[3], 1);
                            AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            ItemUtil.givePlayerRod(player, args[3], Integer.parseInt(args[4]));
                            AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    if (!ConfigReader.RodItem.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        ItemUtil.givePlayerRod(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        ItemUtil.givePlayerRod(player, args[4], Integer.parseInt(args[5]));
                        giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                    }
                    return true;
                }
            }
            else if (args[1].equalsIgnoreCase("bait")){
                if (args[2].equalsIgnoreCase("get")) {
                    if (sender instanceof Player player){
                        if (!ConfigReader.BaitItem.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            ItemUtil.givePlayerBait(player, args[3], 1);
                            AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            ItemUtil.givePlayerBait(player, args[3], Integer.parseInt(args[4]));
                            AdventureUtil.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    if (!ConfigReader.BaitItem.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        ItemUtil.givePlayerBait(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        ItemUtil.givePlayerBait(player, args[4], Integer.parseInt(args[5]));
                        giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                    }
                    return true;
                }
            }
        }
        return true;
    }

    private void lackArgs(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.lackArgs);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.lackArgs);
        }
    }

    private void notOnline(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.notOnline);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.notOnline);
        }
    }

    private void noItem(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.notExist);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.notExist);
        }
    }

    private void giveItem(CommandSender sender, String name, String item, int amount){
        String string = ConfigReader.Message.prefix + ConfigReader.Message.giveItem.replace("{Amount}", String.valueOf(amount)).replace("{Player}",name).replace("{Item}",item);
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender, string);
        }else {
            AdventureUtil.consoleMessage(string);
        }
    }

    private void wrongAmount(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.wrongAmount);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.wrongAmount);
        }
    }

    private void forceSuccess(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.forceSuccess);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.forceSuccess);
        }
    }

    private void forceFailure(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.forceFailure);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.forceFailure);
        }
    }

    private void forceEnd(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.forceEnd);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.forceEnd);
        }
    }

    private void forceCancel(CommandSender sender){
        if (sender instanceof Player){
            AdventureUtil.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.forceCancel);
        }else {
            AdventureUtil.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.forceCancel);
        }
    }
}
