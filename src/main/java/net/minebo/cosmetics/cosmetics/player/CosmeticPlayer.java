package net.minebo.cosmetics.cosmetics.player;

import java.util.*;
import java.util.stream.*;

import net.minebo.cosmetics.Cosmetics;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import org.bukkit.entity.*;
import org.bukkit.*;

public class CosmeticPlayer {

    private final UUID uuid;
    private final List<Cosmetic> selectedCosmetics;

    public CosmeticPlayer(final UUID uuid) {
        this.selectedCosmetics = new ArrayList<Cosmetic>();
        this.uuid = uuid;
    }

    public boolean isSelected(final Cosmetic cosmetic) {
        return this.selectedCosmetics.contains(cosmetic);
    }

    public void selectCosmetic(final Cosmetic cosmetic) {
        this.getPlayer().sendMessage(ChatColor.YELLOW + "You have selected the " + ChatColor.translateAlternateColorCodes('&', cosmetic.getDisplayName()) + ChatColor.YELLOW + " cosmetic.");
        cosmetic.apply(this.getPlayer());
        this.selectedCosmetics.add(cosmetic);
    }

    public void selectCosmeticNoMessage(final Cosmetic cosmetic) {
        cosmetic.apply(this.getPlayer());
        this.selectedCosmetics.add(cosmetic);
    }

    public List<Cosmetic> getAvailableCosmetics() {
        return CosmeticHandler.cosmetics.stream().filter(cosmetic -> cosmetic.hasPermission(this.getPlayer())).collect(Collectors.toList());
    }

    public Cosmetic getCosmeticFromCategory(final CosmeticType cosmeticType) {
        return this.getSelectedCosmetics().stream().filter(cosmetic -> cosmetic.getCosmeticType() == cosmeticType).findFirst().orElse(null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public List<Cosmetic> getSelectedCosmetics() {
        return this.selectedCosmetics;
    }

    public void saveToRedis() {
        for (CosmeticType type : CosmeticType.values()) {
            Cosmetic cosmetic = getCosmeticFromCategory(type);
            String key = "cosmetic." + uuid + ".selected." + type.name().toLowerCase();
            if(type == CosmeticType.GADGET) return;
            if (cosmetic != null) {
                Cosmetics.database.setData(Cosmetics.jedisPool, key, cosmetic.getName());
            } else {
                // If no cosmetic is selected for this type, delete the key!
                Cosmetics.database.runRedisCommand(Cosmetics.jedisPool, (r) -> r.del(key));
            }
        }
    }

    public void loadFromRedis(Player player) {
        for (CosmeticType type : CosmeticType.values()) {
            String key = "cosmetic." + uuid + ".selected." + type.name().toLowerCase();

            if(type == CosmeticType.GADGET) return;

            if (Cosmetics.database.ifExists(Cosmetics.jedisPool, key)) {
                String cosmeticName = Cosmetics.database.getData(Cosmetics.jedisPool, key);
                Optional<Cosmetic> match = CosmeticHandler.cosmetics.stream()
                        .filter(c -> c.getCosmeticType() == type && c.getName().equalsIgnoreCase(cosmeticName))
                        .findFirst();

                match.ifPresent(this::selectCosmeticNoMessage);
            }
        }
    }

}
