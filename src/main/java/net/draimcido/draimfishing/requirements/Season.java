package net.draimcido.draimfishing.requirements;

import me.clip.placeholderapi.PlaceholderAPI;
import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.hook.DraimFarmingSeason;
import net.draimcido.draimfishing.hook.RealisticSeason;
import org.bukkit.ChatColor;

import java.util.List;

public record Season(List<String> seasons) implements Requirement {

    public List<String> getSeasons() {
        return this.seasons;
    }

    @Override
    public boolean isConditionMet(FishingCondition fishingCondition) {
        String currentSeason;
        if (ConfigReader.Config.rsSeason){
            currentSeason = RealisticSeason.getSeason(fishingCondition.getLocation().getWorld());
        }else if(ConfigReader.Config.dfSeason){
            currentSeason = DraimFarmingSeason.getSeason(fishingCondition.getLocation().getWorld());
        }else {
            currentSeason = ChatColor.stripColor(PlaceholderAPI.setPlaceholders(fishingCondition.getPlayer(), ConfigReader.Config.season_papi));
        }
        for (String season : seasons) {
            if (season.equalsIgnoreCase(currentSeason)) {
                return true;
            }
        }
        return false;
    }
}
