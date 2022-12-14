package net.draimcido.draimfishing.requirements;

import org.bukkit.World;

import java.util.List;

public record Weather(List<String> weathers) implements Requirement {

    public List<String> getWeathers() {
        return this.weathers;
    }

    @Override
    public boolean isConditionMet(FishingCondition fishingCondition) {
        World world = fishingCondition.getLocation().getWorld();
        if (world != null) {
            String currentWeather;
            if (world.isThundering()) {
                currentWeather = "thunder";
            } else if (world.isClearWeather()) {
                currentWeather = "clear";
            } else {
                currentWeather = "rain";
            }
            for (String weather : weathers) {
                if (weather.equalsIgnoreCase(currentWeather)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
