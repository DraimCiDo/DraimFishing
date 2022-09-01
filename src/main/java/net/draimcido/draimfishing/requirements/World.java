package net.draimcido.draimfishing.requirements;

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
        return false;
    }
}
