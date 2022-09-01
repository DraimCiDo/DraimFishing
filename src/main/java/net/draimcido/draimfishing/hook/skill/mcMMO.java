package net.draimcido.draimfishing.hook.skill;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import org.bukkit.entity.Player;

public class mcMMO implements SkillXP{

    @Override
    public void addXp(Player player, double amount) {
        ExperienceAPI.addRawXP(player, "FISHING", (float) amount, "UNKNOWN");
    }

    @Override
    public int getLevel(Player player) {
        return ExperienceAPI.getLevel(player, PrimarySkillType.FISHING);
    }
}
