package net.draimcido.draimfishing.competition.bossbar;

import net.kyori.adventure.bossbar.BossBar;

public record BossBarConfig(String text, BossBar.Overlay overlay,
                            BossBar.Color color, int rate) {

    public BossBar.Color getColor() {return color;}
    public int getRate() {return rate;}
    public BossBar.Overlay getOverlay() {return overlay;}
    public String getText() {return text;}
}
