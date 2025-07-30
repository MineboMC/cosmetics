package net.minebo.cosmetics.cosmetics.player;

import java.util.*;
import java.util.stream.*;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import org.bukkit.entity.*;
import org.bukkit.*;

public class CosmeticPlayer
{
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
}
