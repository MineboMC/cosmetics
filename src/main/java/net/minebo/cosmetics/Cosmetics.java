package net.minebo.cosmetics;

import net.minebo.cobalt.menu.MenuHandler;
import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.cosmetics.listeners.ProfileListener;
import net.minebo.cosmetics.task.CosmeticTickTask;
import org.bukkit.plugin.java.*;
import org.bukkit.*;

public class Cosmetics extends JavaPlugin {

    public static Cosmetics instance;

    @Override
    public void onEnable() {
        instance = this;

        MenuHandler.init(this);

        CosmeticHandler.init();

        Bukkit.getScheduler().runTaskTimer(this, new CosmeticTickTask(CosmeticHandler.cosmeticPlayers), 0L, 1L);
        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
    }

    @Override
    public void onDisable() {
        CosmeticHandler.cosmeticPlayers.forEach(cosmeticPlayer -> cosmeticPlayer.getSelectedCosmetics().forEach(cosmetic -> cosmetic.remove(cosmeticPlayer.getPlayer())));
    }
}
