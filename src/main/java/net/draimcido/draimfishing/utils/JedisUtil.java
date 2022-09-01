package net.draimcido.draimfishing.utils;

import net.draimcido.draimfishing.helper.Log;
import org.bukkit.configuration.file.YamlConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

public class JedisUtil {

    private static JedisPool jedisPool;

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void initializeRedis(YamlConfiguration configuration){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setNumTestsPerEvictionRun(-1);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(configuration.getInt("redis.MinEvictableIdleTimeMillis",1800000));
        jedisPoolConfig.setMaxTotal(configuration.getInt("redis.MaxTotal",8));
        jedisPoolConfig.setMaxIdle(configuration.getInt("redis.MaxIdle",8));
        jedisPoolConfig.setMinIdle(configuration.getInt("redis.MinIdle",1));
        jedisPoolConfig.setMaxWaitMillis(configuration.getInt("redis.MaxWaitMillis",30000));

        jedisPool = new JedisPool(jedisPoolConfig, configuration.getString("redis.host","localhost"), configuration.getInt("redis.port",6379));

        AdventureUtil.consoleMessage("<gradient:#a8ff78:#78ffd6>[DraimFishing] </gradient><color:#E1FFFF>Redis Enabled!");

        List<Jedis> minIdleJedisList = new ArrayList<>(jedisPoolConfig.getMinIdle());
        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis;
            try {
                jedis = jedisPool.getResource();
                minIdleJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                Log.warn(e.getMessage());
            }
        }

        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis;
            try {
                jedis = minIdleJedisList.get(i);
                jedis.close();
            } catch (Exception e) {
                Log.warn(e.getMessage());
            }
        }
    }
}
