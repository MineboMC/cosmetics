package net.minebo.cosmetics;

import net.minebo.cobalt.menu.MenuHandler;
import net.minebo.cobalt.redis.RedisDatabase;
import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.cosmetics.cosmetics.player.CosmeticPlayer;
import net.minebo.cosmetics.listeners.ProfileListener;
import net.minebo.cosmetics.task.CosmeticTickTask;
import org.bukkit.plugin.java.*;
import org.bukkit.*;
import redis.clients.jedis.JedisPool;

public class Cosmetics extends JavaPlugin {

    public static Cosmetics instance;

    public static RedisDatabase database;
    public static JedisPool jedisPool;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        database = (getConfig().getBoolean("redis.use-auth") ? new RedisDatabase("cosmetics", getConfig().getString("redis.host"), getConfig().getInt("redis.port"), getConfig().getString("redis.username"), getConfig().getString("redis.password"), getConfig().getInt("redis.db")) : new RedisDatabase("cosmetics", getConfig().getString("redis.host"), getConfig().getInt("redis.port"), getConfig().getInt("redis.db")));
        jedisPool = RedisDatabase.getJedisPool("cosmetics");

        MenuHandler.init(this);

        CosmeticHandler.init();

        Bukkit.getScheduler().runTaskTimer(this, new CosmeticTickTask(CosmeticHandler.cosmeticPlayers), 0L, 1L);
        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
    }

    @Override
    public void onDisable() {
        CosmeticHandler.cosmeticPlayers.forEach(CosmeticPlayer::saveToRedis);
    }
}
