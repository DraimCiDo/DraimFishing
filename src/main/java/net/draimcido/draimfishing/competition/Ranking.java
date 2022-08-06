package net.draimcido.draimfishing.competition;

import java.util.TreeSet;

public class Ranking {

    private final TreeSet<CompetitionPlayer> competitionPlayers = new TreeSet<>();

    public void addPlayer(String player, float score) {
        CompetitionPlayer competitionPlayer = new CompetitionPlayer(player, score);
        competitionPlayers.add(competitionPlayer);
    }

    public void addPlayer(CompetitionPlayer competitionPlayer) {
        competitionPlayers.add(competitionPlayer);
    }

    public void removePlayer(CompetitionPlayer competitionPlayer) {
        competitionPlayers.removeIf(e -> e == competitionPlayer);
    }

    public void clear() {
        competitionPlayers.clear();
    }

    public boolean contains(CompetitionPlayer competitionPlayer) {
        return competitionPlayers.contains(competitionPlayer);
    }

    public CompetitionPlayer getCompetitionPlayer(String player) {
        for (CompetitionPlayer competitionPlayer : competitionPlayers) {
            if (competitionPlayer.getPlayer().equals(player)) {
                return competitionPlayer;
            }
        }
        return null;
    }

    public String getPlayerRank(String player) {
        int index = 1;
        for (CompetitionPlayer competitionPlayer : competitionPlayers) {
            if (competitionPlayer.getPlayer().equals(player)) {
                return String.valueOf(index);
            }else {
                index++;
            }
        }
        return null;
    }

    public CompetitionPlayer getFirst() {
        return competitionPlayers.first();
    }

    public String getPlayerAt(int i) {
        int count = 1;
        for (CompetitionPlayer competitionPlayer : competitionPlayers) {
            if (count == i) {
                return competitionPlayer.getPlayer();
            }
            count++;
        }
        return null;
    }

    public float getScoreAt(int i) {
        int count = 1;
        for (CompetitionPlayer competitionPlayer : competitionPlayers) {
            if (count == i) {
                return competitionPlayer.getScore();
            }
            count++;
        }
        return -1.0f;
    }
}
