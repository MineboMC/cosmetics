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
        CosmeticHandler.cosmeticPlayers.add(new CosmeticPlayer(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        CosmeticPlayer cosmeticPlayer = CosmeticHandler.getPlayer(event.getPlayer());
        cosmeticPlayer.getSelectedCosmetics().clear();
        CosmeticHandler.cosmeticPlayers.remove(cosmeticPlayer);
    }

}
