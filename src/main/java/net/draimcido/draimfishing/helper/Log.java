package net.draimcido.draimfishing.helper;

import net.draimcido.draimfishing.Main;
import org.bukkit.Bukkit;

import java.util.logging.Level;

import javax.annotation.Nonnull;

/**
 * Utility for quickly accessing a logger instance without using {@link Bukkit#getLogger()}
 */
public final class Log {

    public static void info(@Nonnull String s) {
        Main.instance.getLogger().info(s);
    }

    public static void warn(@Nonnull String s) {
        Main.instance.getLogger().warning(s);
    }

    public static void severe(@Nonnull String s) {
        Main.instance.getLogger().severe(s);
    }

    public static void warn(@Nonnull String s, Throwable t) {
        Main.instance.getLogger().log(Level.WARNING, s, t);
    }

    public static void severe(@Nonnull String s, Throwable t) {
        Main.instance.getLogger().log(Level.SEVERE, s, t);
    }

    private Log() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

}
