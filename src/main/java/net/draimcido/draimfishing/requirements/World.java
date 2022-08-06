package net.draimcido.draimfishing.requirements;

import net.draimcido.draimfishing.utils.AdventureManager;

import java.util.List;

public record World(List<String> worlds) implements Requirement {

    public List<String> getWorlds() {
        return this.worlds;
    }

    @Override
    public boolean isConditionMet(FishingCondition fishingCondition) {
        org.bukkit.World world = fishingCondition.getLocation().getWorld();
        if (world != null) {
            return worlds.contains(world.getName());
        }
        AdventureManager.consoleMessage("<red>[DraimFishing] This message should not appear, the world in which the player is fishing does not exist!</red>");
        return false;
    }
}
