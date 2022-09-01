package net.draimcido.draimfishing.object.loot;

import net.draimcido.draimfishing.object.MobVector;

public class Mob extends Loot{

    final String mmID;
    int mmLevel;
    MobVector mobVector;

    public Mob(String key, String mmID) {
        super(key);
        this.mmID = mmID;
    }

    public String getMmID() {
        return mmID;
    }

    public int getMmLevel() {
        return mmLevel;
    }

    public void setMmLevel(int mmLevel) {
        this.mmLevel = mmLevel;
    }

    public MobVector getMobVector() {
        return mobVector;
    }

    public void setMobVector(MobVector mobVector) {
        this.mobVector = mobVector;
    }
}
