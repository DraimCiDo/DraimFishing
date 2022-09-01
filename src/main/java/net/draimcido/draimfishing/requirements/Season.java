package net.draimcido.draimfishing.requirements;

import net.draimcido.draimfishing.ConfigReader;

import java.util.List;

public record Season(List<String> seasons) implements Requirement {

    public List<String> getSeasons() {
        return this.seasons;
    }

    @Override
    public boolean isConditionMet(FishingCondition fishingCondition) {
        String currentSeason = ConfigReader.Config.season.getSeason(fishingCondition.getLocation().getWorld());
        for (String season : seasons) {
            if (season.equalsIgnoreCase(currentSeason)) {
                return true;
            }
        }
        return false;
    }
}
