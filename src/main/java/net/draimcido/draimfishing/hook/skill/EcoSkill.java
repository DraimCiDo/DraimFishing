package net.draimcido.draimfishing.hook.skill;

import com.willfp.ecoskills.api.EcoSkillsAPI;
import com.willfp.ecoskills.skills.Skills;
import org.bukkit.entity.Player;

public class EcoSkill implements SkillXP {

    @Override
    public void addXp(Player player, double amount) {
        EcoSkillsAPI.getInstance().giveSkillExperience(player, Skills.FISHING, amount);
    }

    @Override
    public int getLevel(Player player) {
        return EcoSkillsAPI.getInstance().getSkillLevel(player, Skills.FISHING);
    }
}
