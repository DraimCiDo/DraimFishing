package net.draimcido.draimfishing.command;

import net.draimcido.draimfishing.AdventureManager;
import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.item.Bait;
import net.draimcido.draimfishing.item.Loot;
import net.draimcido.draimfishing.item.Rod;
import net.draimcido.draimfishing.item.Util;
import net.draimcido.draimfishing.utils.SaveItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

public class Execute implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender.hasPermission("draimfishing.admin") || sender.isOp())) {
            AdventureManager.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.noPerm);
            return true;
        }

        if (args.length < 1){
            lackArgs(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ConfigReader.Reload();
            if (sender instanceof Player){
                AdventureManager.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.reload);
            }else {
                AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.reload);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("export")) {
            if (args.length < 2){
                lackArgs(sender);
                return true;
            }
            if (sender instanceof Player player){
                SaveItem.saveToFile(player.getInventory().getItemInMainHand(), args[1]);
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
                    //Длина параметра проверки [0]предмет [1]лут [2]получение [3]xxx [4] (кол-во)
                    if (sender instanceof Player player){
                        //Существует ли он в кэше?
                        if (!ConfigReader.LOOTITEM.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            Loot.givePlayerLoot(player, args[3], 1);
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            Loot.givePlayerLoot(player, args[3], Integer.parseInt(args[4]));
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    //Длина параметра проверки  [0]items [1]loot [2]give [3]player [4]xxx [5](amount)
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    //Находится ли игрок в сети
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    //Существует ли он в кэше?
                    if (!ConfigReader.LOOTITEM.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        Loot.givePlayerLoot(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        Loot.givePlayerLoot(player, args[4], Integer.parseInt(args[5]));
                        giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                    }
                    return true;
                }
            }
            /*
            Выдача предметов
             */
            else if(args[1].equalsIgnoreCase("util")){
                if (args[2].equalsIgnoreCase("get")) {
                    //Длина параметра проверки  [0]items [1]util [2]get [3]xxx [4](amount)
                    if (sender instanceof Player player){
                        //Существует ли он в кэше?
                        if (!ConfigReader.UTIL.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            Util.givePlayerUtil(player, args[3], 1);
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            Util.givePlayerUtil(player, args[3], Integer.parseInt(args[4]));
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    //Длина параметра проверки [0]items [1]util [2]give [3]player [4]xxx [5](amount)
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    //Находится ли игрок в сети
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    //Существует ли он в кэше?
                    if (!ConfigReader.UTIL.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        Util.givePlayerUtil(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        Util.givePlayerUtil(player, args[4], Integer.parseInt(args[5]));
                        giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                    }
                    return true;
                }
            }
            else if (args[1].equalsIgnoreCase("rod")){
                if (args[2].equalsIgnoreCase("get")) {
                    //Продолжительность проверки параметров [0]items [1]rod [2]get [3]xxx [4](amount)
                    if (sender instanceof Player player){
                        //Существует ли он в кэше?
                        if (!ConfigReader.ROD.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            Rod.givePlayerRod(player, args[3], 1);
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            Rod.givePlayerRod(player, args[3], Integer.parseInt(args[4]));
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    //Продолжительность проверки параметров[0]items [1]rod [2]give [3]player [4]xxx [5](amount)
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    //Находится ли игрок в сети
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    //Существует ли он в кэше?
                    if (!ConfigReader.ROD.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        Rod.givePlayerRod(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        Rod.givePlayerRod(player, args[4], Integer.parseInt(args[5]));
                        giveItem(sender, args[3], args[4], Integer.parseInt(args[5]));
                    }
                    return true;
                }
            }
            else if (args[1].equalsIgnoreCase("bait")){
                if (args[2].equalsIgnoreCase("get")) {
                    //Продолжительность проверки параметров [0]items [1]bait [2]get [3]xxx [4](amount)
                    if (sender instanceof Player player){
                        //Существует ли он в кэше?
                        if (!ConfigReader.BAIT.containsKey(args[3])){
                            noItem(sender);
                            return true;
                        }
                        if (args.length == 4){
                            Bait.givePlayerBait(player, args[3], 1);
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", "1").replace("{Item}",args[3]));
                        }else {
                            if (Integer.parseInt(args[4]) < 1){
                                wrongAmount(sender);
                                return true;
                            }
                            Bait.givePlayerBait(player, args[3], Integer.parseInt(args[4]));
                            AdventureManager.playerMessage(player, ConfigReader.Message.prefix + ConfigReader.Message.getItem.replace("{Amount}", args[4]).replace("{Item}",args[3]));
                        }
                    }else {
                        AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.noConsole);
                    }
                    return true;
                }
                if (args[2].equalsIgnoreCase("give")) {
                    //Продолжительность проверки параметров [0]items [1]bait [2]give [3]player [4]xxx [5](amount)
                    if (args.length < 5){
                        lackArgs(sender);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[3]);
                    //Находится ли игрок в сети
                    if (player == null){
                        notOnline(sender);
                        return true;
                    }
                    //Существует ли он в кэше?
                    if (!ConfigReader.BAIT.containsKey(args[4])){
                        noItem(sender);
                        return true;
                    }
                    if (args.length == 5){
                        Bait.givePlayerBait(player, args[4], 1);
                        giveItem(sender, args[3], args[4], 1);
                    }else {
                        if (Integer.parseInt(args[5]) < 1){
                            wrongAmount(sender);
                            return true;
                        }
                        Bait.givePlayerBait(player, args[4], Integer.parseInt(args[5]));
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
            AdventureManager.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.lackArgs);
        }else {
            AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.lackArgs);
        }
    }

    private void notOnline(CommandSender sender){
        if (sender instanceof Player){
            AdventureManager.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.notOnline);
        }else {
            AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.notOnline);
        }
    }

    private void noItem(CommandSender sender){
        if (sender instanceof Player){
            AdventureManager.playerMessage((Player) sender,ConfigReader.Message.prefix + ConfigReader.Message.notExist);
        }else {
            AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.notExist);
        }
    }

    private void giveItem(CommandSender sender, String name, String item, int amount){
        String string = ConfigReader.Message.prefix + ConfigReader.Message.giveItem.replace("{Amount}", String.valueOf(amount)).replace("{Player}",name).replace("{Item}",item);
        if (sender instanceof Player){
            AdventureManager.playerMessage((Player) sender, string);
        }else {
            AdventureManager.consoleMessage(string);
        }
    }

    private void wrongAmount(CommandSender sender){
        if (sender instanceof Player){
            AdventureManager.playerMessage((Player) sender, ConfigReader.Message.prefix + ConfigReader.Message.wrongAmount);
        }else {
            AdventureManager.consoleMessage(ConfigReader.Message.prefix + ConfigReader.Message.wrongAmount);
        }
    }
}
