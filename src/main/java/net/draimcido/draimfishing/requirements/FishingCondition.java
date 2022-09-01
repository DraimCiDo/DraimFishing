package net.draimcido.draimfishing.requirements;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.hook.PapiHook;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FishingCondition{

    private final Location location;
    private final Player player;
    private HashMap<String, String> papiMap;

    public FishingCondition(Location location, Player player) {
        this.location = location;
        this.player = player;
        if (ConfigReader.Config.papi){
            this.papiMap = new HashMap<>();
            CustomPapi.allPapi.forEach(papi -> {
                this.papiMap.put(papi, PapiHook.parse(player, papi));
            });
        }
    }

    public HashMap<String, String> getPapiMap() {
        return papiMap;
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "FishingCondition{" +
                "location=" + location +
                ", player=" + player +
                ", papiMap=" + papiMap +
                '}';
    }
}
