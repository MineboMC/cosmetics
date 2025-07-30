package net.minebo.cosmetics.listeners;

import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.cosmetics.cosmetics.player.CosmeticPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        CosmeticPlayer cosmeticPlayer = new CosmeticPlayer(event.getPlayer().getUniqueId());
        cosmeticPlayer.loadFromRedis(event.getPlayer()); // Load previously selected cosmetics
        CosmeticHandler.cosmeticPlayers.add(cosmeticPlayer);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        CosmeticPlayer cosmeticPlayer = CosmeticHandler.getPlayer(event.getPlayer());
        if (cosmeticPlayer != null) {
            cosmeticPlayer.saveToRedis(); // Save selected cosmetics
            cosmeticPlayer.getSelectedCosmetics().clear();
            CosmeticHandler.cosmeticPlayers.remove(cosmeticPlayer);
        }
    }
}
