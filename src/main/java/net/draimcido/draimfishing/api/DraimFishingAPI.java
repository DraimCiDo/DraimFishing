package net.draimcido.draimfishing.api;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.object.loot.Loot;
import org.jetbrains.annotations.Nullable;

public class DraimFishingAPI {

    @Nullable
    public static Loot getLoot(String key){
        return ConfigReader.LOOT.get(key);
    }
}
