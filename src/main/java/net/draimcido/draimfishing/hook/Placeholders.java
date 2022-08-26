package net.draimcido.draimfishing.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.competition.Competition;
import net.draimcido.draimfishing.competition.CompetitionSchedule;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "competition";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DraimGooSe";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("timeleft")){
            if (CompetitionSchedule.competition != null && CompetitionSchedule.competition.isGoingOn()){
                return String.valueOf(Competition.remainingTime);
            }else {
                return "0";
            }
        }
        if (params.equalsIgnoreCase("rank")){
            if (CompetitionSchedule.competition != null && CompetitionSchedule.competition.isGoingOn()){
                return Optional.ofNullable(CompetitionSchedule.competition.getRanking().getPlayerRank(player.getName())).orElse(ConfigReader.Message.noRank);
            }else {
                return ConfigReader.Message.noRank;
            }
        }
        return null;
    }
}
