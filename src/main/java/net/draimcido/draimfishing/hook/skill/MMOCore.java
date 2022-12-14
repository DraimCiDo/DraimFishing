package net.draimcido.draimfishing.hook.skill;

import net.Indyuce.mmocore.experience.EXPSource;
import net.Indyuce.mmocore.experience.Profession;
import org.bukkit.entity.Player;

public class MMOCore implements SkillXP{

    @Override
    public void addXp(Player player, double amount) {
        Profession profession = net.Indyuce.mmocore.MMOCore.plugin.professionManager.get("fishing");
        profession.giveExperience(net.Indyuce.mmocore.MMOCore.plugin.dataProvider.getDataManager().get(player), amount, null , EXPSource.OTHER);
    }

    @Override
    public int getLevel(Player player) {
        return net.Indyuce.mmocore.MMOCore.plugin.dataProvider.getDataManager().get(player).getLevel();
    }
}

