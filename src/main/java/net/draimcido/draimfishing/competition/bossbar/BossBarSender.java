package net.draimcido.draimfishing.competition.bossbar;

import net.draimcido.draimfishing.ConfigReader;
import net.draimcido.draimfishing.Main;
import net.draimcido.draimfishing.competition.Competition;
import net.draimcido.draimfishing.competition.CompetitionSchedule;
import net.draimcido.draimfishing.hook.PapiHook;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class BossBarSender extends BukkitRunnable {

    private final Player player;
    private final Audience audience;
    private BossBar bossBar;
    private int timer;
    private final BossBarConfig bossbarConfig;
    private final BossBar.Color color;
    private final BossBar.Overlay overlay;

    public BossBarSender(Player player, BossBarConfig bossbarConfig) {
        this.player = player;
        this.bossbarConfig = bossbarConfig;
        this.audience = Main.adventure.player(player);
        this.timer = 0;
        this.color = bossbarConfig.getColor();
        this.overlay = bossbarConfig.getOverlay();
    }

    public void hideBossbar() {
        audience.hideBossBar(bossBar);
    }

    public void showBossbar() {
        String text;
        if (ConfigReader.Config.papi) {
            text = PapiHook.parse(player, bossbarConfig.getText());
        } else {
            text = bossbarConfig.getText();
        }
        bossBar = BossBar.bossBar(
                MiniMessage.miniMessage().deserialize(text.replace("{time}", String.valueOf(Competition.remainingTime))
                        .replace("{rank}", Optional.ofNullable(CompetitionSchedule.competition.getRanking().getPlayerRank(player.getName())).orElse(ConfigReader.Message.noRank))
                        .replace("{minute}", String.format("%02d", Competition.remainingTime / 60))
                        .replace("{second}", String.format("%02d", Competition.remainingTime % 60))
                        .replace("{point}", String.format("%.1f", Optional.ofNullable(CompetitionSchedule.competition.getRanking().getCompetitionPlayer(player.getName())).orElse(Competition.emptyPlayer).getScore()))),
                Competition.progress,
                color,
                overlay);
        audience.showBossBar(bossBar);
    }

    @Override
    public void run() {
        if (timer < bossbarConfig.getRate()) {
            timer++;
        } else {
            String text;
            if (ConfigReader.Config.papi) {
                text = PapiHook.parse(player, bossbarConfig.getText());
            } else {
                text = bossbarConfig.getText();
            }
            bossBar.name(
                    MiniMessage.miniMessage().deserialize(text.replace("{time}", String.valueOf(Competition.remainingTime))
                            .replace("{rank}", Optional.ofNullable(CompetitionSchedule.competition.getRanking().getPlayerRank(player.getName())).orElse(ConfigReader.Message.noRank))
                            .replace("{minute}", String.format("%02d", Competition.remainingTime / 60))
                            .replace("{second}", String.format("%02d", Competition.remainingTime % 60))
                            .replace("{point}", String.format("%.1f", Optional.ofNullable(CompetitionSchedule.competition.getRanking().getCompetitionPlayer(player.getName())).orElse(Competition.emptyPlayer).getScore()))));
            bossBar.progress(Competition.progress);
            timer = 0;
        }
    }
}
