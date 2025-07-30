package net.minebo.cosmetics.task;

import net.minebo.cosmetics.cosmetics.player.CosmeticPlayer;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CosmeticTickTask implements Runnable {

    private final Collection<CosmeticPlayer> cosmeticPlayers;

    public CosmeticTickTask(Collection<CosmeticPlayer> cosmeticPlayers) {
        this.cosmeticPlayers = cosmeticPlayers;
    }

    @Override
    public void run() {
        for (CosmeticPlayer cosmeticPlayer : cosmeticPlayers) {
            for (Cosmetic cosmetic : cosmeticPlayer.getSelectedCosmetics()) {
                Player player = Bukkit.getPlayer(cosmeticPlayer.getUuid());
                if (player != null) {
                    cosmetic.tick(player);
                }
            }
        }
    }
}