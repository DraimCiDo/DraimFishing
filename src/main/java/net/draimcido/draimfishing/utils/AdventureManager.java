package net.draimcido.draimfishing.utils;

import net.draimcido.draimfishing.Main;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;

public class AdventureManager {

    public static void consoleMessage(String s) {
        Audience au = Main.adventure.sender(Bukkit.getConsoleSender());
        Component parsed = Main.miniMessage.deserialize(s);
        au.sendMessage(parsed);
    }

    public static void playerMessage(Player player, String s) {
        Audience au = Main.adventure.player(player);
        Component parsed = Main.miniMessage.deserialize(s);
        au.sendMessage(parsed);
    }

    public static void playerTitle(Player p, String s1, String s2, int in, int duration, int out) {
        Audience au = Main.adventure.player(p);
        Title.Times times = Title.Times.times(Duration.ofMillis(in), Duration.ofMillis(duration), Duration.ofMillis(out));
        Title title = Title.title(Main.miniMessage.deserialize(s1), Main.miniMessage.deserialize(s2), times);
        au.showTitle(title);
    }

    public static void playerActionBar(Player p, String s) {
        Audience au = Main.adventure.player(p);
        au.sendActionBar(Main.miniMessage.deserialize(s));
    }

    public static void playerSound(Player p, Sound.Source source, Key key) {
        Sound sound = Sound.sound(key, source, 1, 1);
        Audience au = Main.adventure.player(p);
        au.playSound(sound);
    }
}
