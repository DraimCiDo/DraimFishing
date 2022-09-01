package net.draimcido.draimfishing.requirements;

import net.draimcido.draimfishing.ConfigReader;

public record SkillLevel(int level) implements Requirement{
    @Override
    public boolean isConditionMet(FishingCondition fishingCondition) {
        return level <= ConfigReader.Config.skillXP.getLevel(fishingCondition.getPlayer());
    }
}
