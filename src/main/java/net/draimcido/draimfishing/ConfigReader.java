package net.draimcido.draimfishing;

import net.draimcido.draimfishing.bar.Difficulty;
import net.draimcido.draimfishing.bar.Layout;
import net.draimcido.draimfishing.item.Bait;
import net.draimcido.draimfishing.item.Loot;
import net.draimcido.draimfishing.item.Rod;
import net.draimcido.draimfishing.item.Util;
import net.draimcido.draimfishing.requirements.*;
import net.draimcido.draimfishing.utils.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class ConfigReader{

    public static HashMap<String, Loot> LOOT = new HashMap<>();
    public static HashMap<String, ItemStack> LOOTITEM = new HashMap<>();
    public static HashMap<String, Util> UTIL = new HashMap<>();
    public static HashMap<String, ItemStack> UTILITEM = new HashMap<>();
    public static HashMap<String, Rod> ROD = new HashMap<>();
    public static HashMap<String, ItemStack> RODITEM = new HashMap<>();
    public static HashMap<String, Bait> BAIT = new HashMap<>();
    public static HashMap<String, ItemStack> BAITITEM = new HashMap<>();
    public static HashMap<String, Layout> LAYOUT = new HashMap<>();

    private static YamlConfiguration getConfig(String configName) {
        File file = new File(Main.instance.getDataFolder(), configName);
        if (!file.exists()) {
            Main.instance.saveResource(configName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void Reload() {
        Config.loadConfig();
        Message.loadMessage();
        Title.loadTitle();
        loadLoot();
        loadUtil();
        loadRod();
        loadBait();
    }

    public static class Config {

        public static boolean wg;
        public static boolean mm;
        public static boolean papi;
        public static boolean season;
        public static boolean vanillaDrop;
        public static boolean needOpenWater;
        public static boolean needSpecialRod;
        public static String season_papi;
        public static String lang;
        public static int fishFinderCoolDown;
        public static double timeMultiply;

        public static void loadConfig() {

            Main.instance.saveDefaultConfig();
            Main.instance.reloadConfig();
            FileConfiguration config = Main.instance.getConfig();

            wg = config.getBoolean("config.integrations.WorldGuard");
            if (wg){
                if (Bukkit.getPluginManager().getPlugin("WorldGuard") == null){
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279>Не удалось инициализировать WorldGuard!</gradient>");
                    wg = false;
                }else {
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><color:#00BFFF>WorldGuard <color:#E1FFFF>подвязался!");
                }
            }
            mm = config.getBoolean("config.integrations.MythicMobs");
            if (mm){
                if (Bukkit.getPluginManager().getPlugin("MythicMobs") == null){
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279>Не удалось инициализировать MythicMobs!</gradient>");
                    mm = false;
                }else {
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><color:#00BFFF>MythicMobs <color:#E1FFFF>подвязался!");
                }
            }
            papi = config.getBoolean("config.integrations.PlaceholderAPI");
            if (papi){
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null){
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279>Не удалось инициализировать PlaceholderAPI!</gradient>");
                    papi = false;
                }else {
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><color:#00BFFF>PlaceholderAPI <color:#E1FFFF>подвязался!");
                }
            }
            season = config.getBoolean("config.season.enable");
            if (!papi && season) {
                season = false;
            }

            if (season) {
                season_papi = config.getString("config.season.papi");
            }else {
                season_papi = null;
            }

            vanillaDrop = config.getBoolean("config.vanilla-loot-when-no-custom-fish");
            needOpenWater = config.getBoolean("config.need-open-water");
            needSpecialRod = config.getBoolean("config.need-special-rod");
            fishFinderCoolDown = config.getInt("config.fishfinder-cooldown");
            timeMultiply = config.getDouble("config.time-multiply");
            lang = config.getString("config.lang","ru");

            LAYOUT.clear();
            Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("config.success-rate")).getKeys(false);
            keys.forEach(key -> {
                int range = config.getInt("config.success-rate." + key + ".range");
                Set<String> rates = Objects.requireNonNull(config.getConfigurationSection("config.success-rate." + key + ".layout")).getKeys(false);
                double[] successRate = new double[rates.size()];
                for(int i = 0; i < rates.size(); i++){
                    successRate[i] = config.getDouble("config.success-rate." + key + ".layout." +(i + 1));
                }
                int size = rates.size()*range -1;
                Layout layout = new Layout(key, range, successRate, size);
                layout.setTitle(config.getString("config.success-rate." + key + ".title"," "));
                layout.setBar(config.getString("config.success-rate." + key + ".subtitle.bar","뀃"));
                layout.setEnd(config.getString("config.success-rate." + key + ".subtitle.end","</font>"));
                layout.setStart(config.getString("config.success-rate." + key + ".subtitle.start","<font:draimfishing:default>"));
                layout.setPointer(config.getString("config.success-rate." + key + ".subtitle.pointer","뀄"));
                layout.setPointerOffset(config.getString("config.success-rate." + key + ".subtitle.pointer_offset","뀂"));
                layout.setOffset(config.getString("config.success-rate." + key + ".subtitle.offset","뀁"));
                LAYOUT.put(key, layout);
            });
        }
    }

    public static class Message {
        public static String prefix;
        public static String reload;
        public static String escape;
        public static String noPerm;
        public static String notExist;
        public static String noConsole;
        public static String wrongAmount;
        public static String lackArgs;
        public static String notOnline;
        public static String giveItem;
        public static String getItem;
        public static String coolDown;
        public static String possibleLoots;
        public static String splitChar;
        public static String noLoot;
        public static String notOpenWater;
        public static void loadMessage() {
            YamlConfiguration config = getConfig("messages/messages_" + Config.lang +".yml");
            prefix = config.getString("messages.prefix");
            reload = config.getString("messages.reload");
            escape = config.getString("messages.escape");
            noPerm = config.getString("messages.no-perm");
            notExist = config.getString("messages.not-exist");
            noConsole = config.getString("messages.no-console");
            wrongAmount = config.getString("messages.wrong-amount");
            lackArgs = config.getString("messages.lack-args");
            notOnline = config.getString("messages.not-online");
            giveItem = config.getString("messages.give-item");
            getItem = config.getString("messages.get-item");
            coolDown = config.getString("messages.cooldown");
            possibleLoots = config.getString("messages.possible-loots");
            splitChar = config.getString("messages.split-char");
            noLoot = config.getString("messages.no-loot");
            notOpenWater = config.getString("messages.not-open-water");
        }
    }

    public static class Title {
        public static List<String> success_title;
        public static List<String> success_subtitle;
        public static int success_in;
        public static int success_out;
        public static int success_stay;
        public static List<String> failure_title;
        public static List<String> failure_subtitle;
        public static int failure_in;
        public static int failure_out;
        public static int failure_stay;
        public static void loadTitle() {
            YamlConfiguration config = getConfig("titles.yml");
            success_title = config.getStringList("titles.success.title");
            success_subtitle = config.getStringList("titles.success.subtitle");
            success_in = config.getInt("titles.success.fade.in")*50;
            success_out = config.getInt("titles.success.fade.out")*50;
            success_stay = config.getInt("titles.success.fade.stay")*50;
            failure_title = config.getStringList("titles.failure.title");
            failure_subtitle = config.getStringList("titles.failure.subtitle");
            failure_in = config.getInt("titles.failure.fade.in")*50;
            failure_out = config.getInt("titles.failure.fade.out")*50;
            failure_stay = config.getInt("titles.failure.fade.stay")*50;
        }
    }

    public static void loadLoot() {

        LOOT.clear();
        LOOTITEM.clear();

        YamlConfiguration config = getConfig("loots.yml");
        Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("items")).getKeys(false);
        keys.forEach(key -> {

            Difficulty difficulty;
            if (config.contains("items." + key + ".difficulty")) {
                String[] split = StringUtils.split(config.getString("items." + key + ".difficulty"), "-");
                assert split != null;
                if (Integer.parseInt(split[1]) <= 0 || Integer.parseInt(split[0]) <= 0){
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! " + key + " имеет неправильный формат сложности!</gradient");
                    return;
                }else {
                    difficulty = new Difficulty(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                }
            } else {
                difficulty = new Difficulty(1, 1);
            }
            int weight;
            if (config.contains("items." + key + ".weight")) {
                weight = config.getInt("items." + key + ".weight");
            } else {
                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Не правильное заничение weight " + key + " !</gradient>");
                return;
            }
            int time;
            if (config.contains("items." + key + ".time")) {
                time = config.getInt("items." + key + ".time");
                if (time <= 0){
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! " + key + " параметр time должен быть положительным!</gradient>");
                    return;
                }
            } else {
                time = 10000;
            }

            Loot loot = new Loot(key, difficulty, weight, time);

            if (config.contains("items." + key + ".material")) {
                loot.setMaterial(config.getString("items." + key + ".material"));
            } else {
                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Нету предметов для" + key + " !</gradient>");
                return;
            }
            if (config.contains("items." + key + ".display.lore"))
                loot.setLore(config.getStringList("items." + key + ".display.lore"));
            if (config.contains("items." + key + ".display.name"))
                loot.setName(config.getString("items." + key + ".display.name"));
            if (config.contains("items." + key + ".enchantments")) {
                ArrayList<Enchantment> arrayList = new ArrayList<>();
                config.getStringList("items." + key + ".enchantments").forEach(enchant -> {
                    String[] split = StringUtils.split(enchant, "/");
                    NamespacedKey namespacedKey = NamespacedKey.fromString(split[0]);
                    arrayList.add(new Enchantment(namespacedKey, Integer.parseInt(split[1])));
                });
                loot.setEnchantment(arrayList);
            }
            if (config.contains("items." + key + ".item_flags")) {
                ArrayList<ItemFlag> arrayList = new ArrayList<>();
                config.getStringList("items." + key + ".item_flags").forEach(flag -> {
                    arrayList.add(ItemFlag.valueOf(flag));
                });
                loot.setItemFlags(arrayList);
            }
            if (config.contains("items." + key + ".nbt"))
                loot.setNbt((Map<String, Object>) config.getMapList("items." + key + ".nbt").get(0));
            if (config.contains("items." + key + ".custom-model-data"))
                loot.setCustommodeldata(config.getInt("items." + key + ".custom-model-data"));
            if (config.contains("items."+ key +".nick")){
                loot.setNick(config.getString("items."+key+".nick"));
            }else {
                loot.setNick(loot.getName());
            }
            loot.setUnbreakable(config.getBoolean("items." + key + ".unbreakable",false));

            if (config.contains("items." + key + ".action.message"))
                loot.setMsg(config.getString("items." + key + ".action.message"));
            if (config.contains("items." + key + ".action.command"))
                loot.setCommands(config.getStringList("items." + key + ".action.command"));
            if (config.contains("items." + key + ".action.exp"))
                loot.setExp(config.getInt("items." + key + ".action.exp"));
            if (config.contains("items." + key + ".layout"))
                loot.setLayout(config.getString("items." + key + ".layout"));
            if (config.contains("items." + key + ".group"))
                loot.setGroup(config.getString("items." + key + ".group"));
            if (config.contains("items." + key + ".show-in-fishfinder")){
                loot.setShowInFinder(config.getBoolean("items." + key + ".show-in-fishfinder"));
            }else {
                loot.setShowInFinder(true);
            }
            if (config.contains("items." + key + ".requirements")){
                List<Requirement> requirements = new ArrayList<>();
                Objects.requireNonNull(config.getConfigurationSection("items." + key + ".requirements")).getKeys(false).forEach(requirement -> {
                    switch (requirement){
                        case "weather" -> requirements.add(new Weather(config.getStringList("items." + key + ".requirements.weather")));
                        case "ypos" -> requirements.add(new YPos(config.getStringList("items." + key + ".requirements.ypos")));
                        case "season" -> {
                            if (Config.season){
                                requirements.add(new Season(config.getStringList("items." + key + ".requirements.season")));
                            }else {
                                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Пожалуйста активируй seasons в config.yml!</gradient>");
                            }
                        }
                        case "world" -> requirements.add(new World(config.getStringList("items." + key + ".requirements.world")));
                        case "biome" -> requirements.add(new Biome(config.getStringList("items." + key + ".requirements.biome")));
                        case "permission" -> requirements.add(new Permission(config.getString("items." + key + ".requirements.permission")));
                        case "region" -> {
                            if (Config.wg){
                                requirements.add(new Region(config.getStringList("items." + key + ".requirements.regions")));
                            }else {
                                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Пожалуйста активируйте интеграцию WorldGuard!</gradient>");
                            }
                        }
                        case "time" -> requirements.add(new Time(config.getStringList("items." + key + ".requirements.time")));
                    }
                });
                loot.setRequirements(requirements);
            }
            LOOT.put(key, loot);
            LOOTITEM.put(key, NBTUtil.addIdentifier(ItemStackGenerator.fromItem(loot), "loot", key));
        });

        if (config.contains("mobs") && Config.mm){
            Set<String> mobs = Objects.requireNonNull(config.getConfigurationSection("mobs")).getKeys(false);
            mobs.forEach(key -> {

                String name;
                if (config.contains("mobs." + key + ".name")) {
                    name = config.getString("mobs." + key + ".name");
                } else {
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Имя моба не усатновлено " + key + " !</gradient>");
                    return;
                }
                Difficulty difficulty;
                if (config.contains("mobs." + key + ".difficulty")) {
                    String[] split = StringUtils.split(config.getString("mobs." + key + ".difficulty"), "-");
                    assert split != null;
                    if (Integer.parseInt(split[1]) <= 0 || Integer.parseInt(split[0]) <= 0){
                        AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! " + key + " имеет неправильный формат сложности! </gradient>");
                        return;
                    }else {
                        difficulty = new Difficulty(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                    }
                } else {
                    difficulty = new Difficulty(1, 1);
                }
                int weight;
                if (config.contains("mobs." + key + ".weight")) {
                    weight = config.getInt("mobs." + key + ".weight");
                } else {
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Не установлен параметр weight для " + key + " !</gradient>");
                    return;
                }
                int time;
                if (config.contains("mobs." + key + ".time")) {
                    time = config.getInt("mobs." + key + ".time");
                    if (time <= 0){
                        AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! " + key + " параметр времени должен быть положительным!</gradient>");
                        return;
                    }
                } else {
                    time = 10000;
                }
                Loot loot = new Loot(key, difficulty, weight, time);
                loot.setNick(name);
                if (config.contains("mobs." + key + ".mythicmobsID")) {
                    loot.setMm(config.getString("mobs." + key + ".mythicmobsID"));
                } else {
                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Не установлен MythicMobs ид для " + key + " !</gradient>");
                    return;
                }
                if (config.contains("mobs." + key + ".vector.horizontal") && config.contains("mobs." + key + ".vector.vertical")) {
                    loot.setVectorUtil(new VectorUtil(config.getDouble("mobs." + key + ".vector.horizontal"), config.getDouble("mobs." + key + ".vector.vertical")));
                } else {
                    loot.setVectorUtil(new VectorUtil(1.1, 1.3));
                }

                if (config.contains("mobs." + key + ".level"))
                    loot.setMmLevel(config.getInt("mobs." + key + ".level", 0));
                if (config.contains("mobs." + key + ".action.message"))
                    loot.setMsg(config.getString("mobs." + key + ".action.message"));
                if (config.contains("mobs." + key + ".action.command"))
                    loot.setCommands(config.getStringList("mobs." + key + ".action.command"));
                if (config.contains("mobs." + key + ".action.exp"))
                    loot.setExp(config.getInt("mobs." + key + ".action.exp"));
                if (config.contains("mobs." + key + ".layout"))
                    loot.setLayout(config.getString("mobs." + key + "layout"));
                if (config.contains("mobs." + key + ".group"))
                    loot.setGroup(config.getString("mobs." + key + ".group"));
                if (config.contains("mobs." + key + ".show-in-fishfinder")){
                    loot.setShowInFinder(config.getBoolean("mobs." + key + ".show-in-fishfinder"));
                }else {
                    loot.setShowInFinder(true);
                }
                if (config.contains("mobs." + key + ".requirements")){
                    List<Requirement> requirements = new ArrayList<>();
                    Objects.requireNonNull(config.getConfigurationSection("mobs." + key + ".requirements")).getKeys(false).forEach(requirement -> {
                        switch (requirement){
                            case "weather" -> requirements.add(new Weather(config.getStringList("mobs." + key + ".requirements.weather")));
                            case "ypos" -> requirements.add(new YPos(config.getStringList("mobs." + key + ".requirements.ypos")));
                            case "season" -> {
                                if (Config.season){
                                    requirements.add(new Season(config.getStringList("mobs." + key + ".requirements.season")));
                                }else {
                                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Пожалуйста включите параметр season в config.yml!</gradient>");
                                }
                            }
                            case "world" -> requirements.add(new World(config.getStringList("mobs." + key + ".requirements.world")));
                            case "biome" -> requirements.add(new Biome(config.getStringList("mobs." + key + ".requirements.biome")));
                            case "permission" -> requirements.add(new Permission(config.getString("mobs." + key + ".requirements.permission")));
                            case "region" -> {
                                if (Config.wg){
                                    requirements.add(new Region(config.getStringList("mobs." + key + ".requirements.regions")));
                                }else {
                                    AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Пожалуйста включите интеграцию WorldGuard!</gradient>");
                                }
                            }
                            case "time" -> requirements.add(new Time(config.getStringList("mobs." + key + ".requirements.time")));
                        }
                    });
                    loot.setRequirements(requirements);
                }
                LOOT.put(key, loot);
            });
            if (keys.size() != LOOTITEM.size() || mobs.size() != LOOT.size()- LOOTITEM.size()) {
                AdventureManager.consoleMessage("<red>[DraimFishing] loots.yml exists error!</red>");
            } else {
                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><white>" + keys.size() + " <color:#E1FFFF>лута было загружено!");
                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><white>" + mobs.size() + " <color:#E1FFFF>мобов было загружено!");
            }
            return;
        }
        if (keys.size() != LOOTITEM.size()){
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! loots.yml не найден!</gradient>");
        } else {
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><white>" + keys.size() + " <color:#E1FFFF>лута было загружено!");
        }
    }

    public static void loadUtil() {

        UTIL.clear();
        UTILITEM.clear();

        YamlConfiguration config = getConfig("utils.yml");
        Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("utils")).getKeys(false);
        keys.forEach(key -> {

            String material;
            if (config.contains("utils." + key + ".material")) {
                material = config.getString("utils." + key + ".material");
            } else {
                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Предмет не существует для " + key + " !</gradient>");
                return;
            }

            Util utilInstance = new Util(material);
            if (config.contains("utils." + key + ".custom-model-data"))
                utilInstance.setCustommodeldata(config.getInt("utils." + key + ".custom-model-data"));
            if (config.contains("utils." + key + ".display.name"))
                utilInstance.setName(config.getString("utils." + key + ".display.name"));
            if (config.contains("utils." + key + ".display.lore"))
                utilInstance.setLore(config.getStringList("utils." + key + ".display.lore"));
            if (config.contains("utils." + key + ".nbt"))
                utilInstance.setNbt((Map<String, Object>) config.getMapList("utils." + key + ".nbt").get(0));
            utilInstance.setUnbreakable(config.getBoolean("utils." + key + ".unbreakable",false));
            if (config.contains("utils." + key + ".enchantments")) {
                ArrayList<Enchantment> arrayList = new ArrayList<>();
                config.getStringList("utils." + key + ".enchantments").forEach(enchant -> {
                    String[] split = StringUtils.split(enchant, "/");
                    NamespacedKey namespacedKey = NamespacedKey.fromString(split[0]);
                    arrayList.add(new Enchantment(namespacedKey, Integer.parseInt(split[1])));
                });
                utilInstance.setEnchantment(arrayList);
            }
            if (config.contains("utils." + key + ".item_flags")) {
                ArrayList<ItemFlag> arrayList = new ArrayList<>();
                config.getStringList("utils." + key + ".item_flags").forEach(flag -> {
                    arrayList.add(ItemFlag.valueOf(flag));
                });
                utilInstance.setItemFlags(arrayList);
            }

            UTIL.put(key, utilInstance);
            UTILITEM.put(key, NBTUtil.addIdentifier(ItemStackGenerator.fromItem(utilInstance), "util", key));
        });
        if (keys.size() != UTILITEM.size()){
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! utils.yml не был найден!</gradient>");
        } else {
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><white>" + keys.size() + " <color:#E1FFFF>утилит было загружено!");
        }
    }

    public static void loadRod() {

        ROD.clear();
        RODITEM.clear();

        YamlConfiguration config = getConfig("rods.yml");
        Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("rods")).getKeys(false);

        keys.forEach(key -> {
            Rod rodInstance = new Rod();
            if (config.contains("rods." + key + ".display.name"))
                rodInstance.setName(config.getString("rods." + key + ".display.name"));
            if (config.contains("rods." + key + ".display.lore"))
                rodInstance.setLore(config.getStringList("rods." + key + ".display.lore"));
            if (config.contains("rods." + key + ".nbt"))
                rodInstance.setNbt((Map<String, Object>)(config.getMapList("rods." + key + ".nbt").get(0)));
            if (config.contains("rods." + key + ".custom-model-data"))
                rodInstance.setCustommodeldata(config.getInt("rods." + key + ".custom-model-data"));
            rodInstance.setUnbreakable(config.getBoolean("rods." + key + ".unbreakable",false));
            if (config.contains("rods." + key + ".enchantments")) {
                ArrayList<Enchantment> arrayList = new ArrayList<>();
                config.getStringList("rods." + key + ".enchantments").forEach(enchant -> {
                    String[] split = StringUtils.split(enchant, "/");
                    NamespacedKey namespacedKey = NamespacedKey.fromString(split[0]);
                    arrayList.add(new Enchantment(namespacedKey, Integer.parseInt(split[1])));
                });
                rodInstance.setEnchantment(arrayList);
            }
            if (config.contains("rods." + key + ".item_flags")) {
                ArrayList<ItemFlag> arrayList = new ArrayList<>();
                config.getStringList("rods." + key + ".item_flags").forEach(flag -> {
                    arrayList.add(ItemFlag.valueOf(flag));
                });
                rodInstance.setItemFlags(arrayList);
            }
            if (config.contains("rods." + key + ".modifier")){
                config.getConfigurationSection("rods." + key + ".modifier").getKeys(false).forEach(modifier -> {
                    switch (modifier){
                        case "weight-PM" -> {
                            HashMap<String, Integer> pm = new HashMap<>();
                            config.getConfigurationSection("rods." + key + ".modifier.weight-PM").getValues(false).forEach((group, value) -> {
                                pm.put(group, (Integer) value);
                            });
                            rodInstance.setWeightPM(pm);
                        }
                        case "weight-MQ" -> {
                            HashMap<String, Double> mq = new HashMap<>();
                            config.getConfigurationSection("rods." + key + ".modifier.weight-MQ").getValues(false).forEach((group, value) -> {
                                mq.put(group, (Double) value);
                            });
                            rodInstance.setWeightMQ(mq);
                        }
                        case "time" -> rodInstance.setTime(config.getDouble("rods." + key + ".modifier.time"));
                        case "difficulty" -> rodInstance.setDifficulty(config.getInt("rods." + key + ".modifier.difficulty"));
                        case "double-loot" -> rodInstance.setDoubleLoot(config.getDouble("rods." + key + ".modifier.double-loot"));
                    }
                });
            }
            ROD.put(key, rodInstance);
            RODITEM.put(key, NBTUtil.addIdentifier(ItemStackGenerator.fromItem(rodInstance), "rod", key));
        });

        if (keys.size() != RODITEM.size()){
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! rods.yml не был найден!</gradient>");
        } else {
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><white>" + keys.size() + " <color:#E1FFFF>удочек было загружено!");
        }
    }

    public static void loadBait(){

        BAITITEM.clear();
        BAIT.clear();

        YamlConfiguration config = getConfig("baits.yml");
        Set<String> keys = config.getConfigurationSection("baits").getKeys(false);

        keys.forEach(key -> {
            String material;
            if (config.contains("baits." + key + ".material")) {
                material = config.getString("baits." + key + ".material");
            } else {
                AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! Не указан матерьял для " + key + " !</gradient>");
                return;
            }
            Bait baitInstance = new Bait(material);
            if (config.contains("baits." + key + ".display.lore"))
                baitInstance.setLore(config.getStringList("baits." + key + ".display.lore"));
            if (config.contains("baits." + key + ".display.name"))
                baitInstance.setName(config.getString("baits." + key + ".display.name"));
            if (config.contains("baits." + key + ".custom-model-data"))
                baitInstance.setCustommodeldata(config.getInt("baits." + key + ".custom-model-data"));
            if (config.contains("baits." + key + ".nbt")) {
                baitInstance.setNbt((Map<String, Object>) config.getMapList("baits." + key + ".nbt").get(0));
            }
            baitInstance.setUnbreakable(config.getBoolean("baits." + key + ".unbreakable",false));
            if (config.contains("baits." + key + ".enchantments")) {
                ArrayList<Enchantment> arrayList = new ArrayList<>();
                config.getStringList("baits." + key + ".enchantments").forEach(enchant -> {
                    String[] split = StringUtils.split(enchant, "/");
                    NamespacedKey namespacedKey = NamespacedKey.fromString(split[0]);
                    arrayList.add(new Enchantment(namespacedKey, Integer.parseInt(split[1])));
                });
                baitInstance.setEnchantment(arrayList);
            }
            if (config.contains("baits." + key + ".item_flags")) {
                ArrayList<ItemFlag> arrayList = new ArrayList<>();
                config.getStringList("baits." + key + ".item_flags").forEach(flag -> {
                    arrayList.add(ItemFlag.valueOf(flag));
                });
                baitInstance.setItemFlags(arrayList);
            }
            if (config.contains("baits." + key + ".modifier")){
                config.getConfigurationSection("baits." + key + ".modifier").getKeys(false).forEach(modifier -> {
                    switch (modifier){
                        case "weight-PM" -> {
                            HashMap<String, Integer> pm = new HashMap<>();
                            config.getConfigurationSection("baits." + key + ".modifier.weight-PM").getValues(false).forEach((group, value) -> {
                                pm.put(group, (Integer) value);
                            });
                            baitInstance.setWeightPM(pm);
                        }
                        case "weight-MQ" -> {
                            HashMap<String, Double> mq = new HashMap<>();
                            config.getConfigurationSection("baits." + key + ".modifier.weight-MQ").getValues(false).forEach((group, value) -> {
                                mq.put(group, (Double) value);
                            });
                            baitInstance.setWeightMQ(mq);
                        }
                        case "time" -> baitInstance.setTime(config.getDouble("baits." + key + ".modifier.time"));
                        case "difficulty" -> baitInstance.setDifficulty(config.getInt("baits." + key + ".modifier.difficulty"));
                        case "double-loot" -> baitInstance.setDoubleLoot(config.getDouble("baits." + key + ".modifier.double-loot"));
                    }
                });
            }
            BAIT.put(key, baitInstance);
            BAITITEM.put(key, NBTUtil.addIdentifier(ItemStackGenerator.fromItem(baitInstance), "bait", key));
        });

        if (keys.size() != BAITITEM.size()){
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><gradient:#D38312:#A83279> Ошибка! baits.yml не был найден!</gradient>");
        } else {
            AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[DraimFishing] </gradient><white>" + keys.size() + " <color:#E1FFFF>приманок было загружено!");
        }
    }
}
