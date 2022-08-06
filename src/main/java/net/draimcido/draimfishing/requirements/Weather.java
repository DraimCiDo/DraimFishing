package net.draimcido.draimfishing.requirements;

import net.draimcido.draimfishing.utils.AdventureManager;
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
        AdventureManager.consoleMessage("<red>[DraimFishing] This message should not appear, the world in which the player is fishing does not exist!</red>");
        return false;
    }
}
