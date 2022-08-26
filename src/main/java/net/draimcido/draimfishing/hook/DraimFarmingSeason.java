package net.draimcido.draimfishing.hook;

import net.draimcido.draimfarming.api.DraimFarmingAPI;
import org.bukkit.World;

public class DraimFarmingSeason {

    public static String getSeason(World world){
        return DraimFarmingAPI.getSeason(world.getName());
    }
}
