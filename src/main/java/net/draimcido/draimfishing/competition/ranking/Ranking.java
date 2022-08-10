package net.draimcido.draimfishing.competition.ranking;

import net.draimcido.draimfishing.competition.CompetitionPlayer;

import java.util.Iterator;

public interface Ranking {
    void clear();
    CompetitionPlayer getCompetitionPlayer(String player);
    Iterator<String> getIterator();
    int getSize();
    String getPlayerRank(String player);
    CompetitionPlayer[] getTop3Player();
    void refreshData(String player, float score);
}
