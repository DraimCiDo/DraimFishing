package net.draimcido.draimfishing.helper;

import net.draimcido.draimfishing.Main;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * Utility for quickly accessing a logger instance without using {@link Bukkit#getLogger()}
 */
public final class Log {

    public static void info(@NotNull String s) {
        Main.instance.getLogger().info(s);
    }

    public static void warn(@NotNull String s) {
        Main.instance.getLogger().warning(s);
    }

    public static void severe(@NotNull String s) {
        Main.instance.getLogger().severe(s);
    }

    public static void warn(@NotNull String s, Throwable t) {
        Main.instance.getLogger().log(Level.WARNING, s, t);
    }

    public static void severe(@NotNull String s, Throwable t) {
        Main.instance.getLogger().log(Level.SEVERE, s, t);
    }

    private Log() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

}
