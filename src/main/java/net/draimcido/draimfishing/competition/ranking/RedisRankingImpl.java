package net.draimcido.draimfishing.competition.ranking;

import net.draimcido.draimfishing.competition.CompetitionPlayer;
import net.draimcido.draimfishing.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.Iterator;
import java.util.List;

public class RedisRankingImpl implements Ranking{

    public void addPlayer(CompetitionPlayer competitionPlayer) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.zadd("cf_competition", competitionPlayer.getScore(), competitionPlayer.getPlayer());
        jedis.close();
    }

    public void removePlayer(CompetitionPlayer competitionPlayer) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.zrem("cf_competition", competitionPlayer.getPlayer());
        jedis.close();
    }

    @Override
    public void clear() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.zremrangeByRank("cf_competition",0,-1);
        jedis.close();
    }

    @Override
    public CompetitionPlayer getCompetitionPlayer(String player) {
        Jedis jedis = JedisUtil.getJedis();
        Double score = jedis.zscore("cf_competition", player);
        jedis.close();
        if (score == 0) return null;
        return new CompetitionPlayer(player, Float.parseFloat(score.toString()));
    }

    @Override
    public Iterator<String> getIterator() {
        Jedis jedis = JedisUtil.getJedis();
        List<String> players = jedis.zrevrange("cf_competition", 0, -1);
        jedis.close();
        return players.iterator();
    }

    @Override
    public int getSize() {
        Jedis jedis = JedisUtil.getJedis();
        long size = jedis.zcard("cf_competition");
        jedis.close();
        return (int) size;
    }

    @Override
    public String getPlayerRank(String player) {
        Jedis jedis = JedisUtil.getJedis();
        Long rank = jedis.zrevrank("cf_competition", player);
        jedis.close();
        if(rank == null){
            return null;
        }
        return String.valueOf(rank + 1);
    }

    @Override
    public CompetitionPlayer[] getTop3Player() {
        CompetitionPlayer[] competitionPlayers = new CompetitionPlayer[3];
        Jedis jedis = JedisUtil.getJedis();
        List<Tuple> players = jedis.zrevrangeWithScores("cf_competition", 0, -1);
        jedis.close();
        int index = 1;
        for (Tuple tuple : players){
            if (index == 1){
                competitionPlayers[0] = new CompetitionPlayer(tuple.getElement(), (float) tuple.getScore());
            }
            if (index == 2){
                competitionPlayers[1] = new CompetitionPlayer(tuple.getElement(), (float) tuple.getScore());
            }
            if (index == 3){
                competitionPlayers[2] = new CompetitionPlayer(tuple.getElement(), (float) tuple.getScore());
                return competitionPlayers;
            }
            index++;
        }
        return competitionPlayers;
    }

    @Override
    public void refreshData(String player, float score) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.zincrby("cf_competition", score, player);
        jedis.close();
    }
}
