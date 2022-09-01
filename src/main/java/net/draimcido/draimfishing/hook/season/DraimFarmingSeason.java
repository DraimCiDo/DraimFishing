package net.draimcido.draimfishing.hook.season;

import net.draimcido.draimfarming.api.DraimFarmingAPI;
import org.bukkit.World;

public class DraimFarmingSeason implements SeasonInterface{
    public String getSeason(World world){
        return DraimFarmingAPI.getSeason(world.getName());
    }
}
