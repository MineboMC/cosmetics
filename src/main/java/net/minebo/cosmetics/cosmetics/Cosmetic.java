package net.minebo.cosmetics.cosmetics;

import net.minebo.cosmetics.Cosmetics;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import java.util.*;
import java.util.stream.*;
import org.bukkit.entity.*;

public abstract class Cosmetic implements Listener
{
    public abstract String getName();
    
    public abstract String getDisplayName();
    
    public abstract CosmeticType getCosmeticType();
    
    public abstract boolean hasPermission(final Player p0);
    
    public abstract List<String> getDescription();
    
    public abstract ItemStack getIcon();
    
    public abstract void apply(final Player p0);
    
    public abstract void tick(final Player p0);
    
    public abstract void remove(final Player p0);
    
    public abstract boolean noPermissionHide();
    
    public void unselectCosmetic(final Player player, final boolean sendMessage) {
        if (sendMessage && player != null) {
            player.sendMessage(ChatColor.YELLOW + "You have unselected the " + ChatColor.translateAlternateColorCodes('&', this.getDisplayName()) + ChatColor.YELLOW + " cosmetic.");
        }

        CosmeticHandler.getPlayer(player).getSelectedCosmetics().remove(this);
    }
    
    public void unselectCosmetic(final Player player) {
        this.unselectCosmetic(player, true);
    }
    
    public boolean hasSelected(final Player player) {
        return CosmeticHandler.getPlayer(player).isSelected(this);
    }
    
    public List<Player> getNearbyPlayers(final Player player, final int radius, final boolean ignoreVis) {
        final List<Player> playerList = new ArrayList<Player>(Collections.singletonList(player));
        playerList.addAll(player.getNearbyEntities(radius, radius, radius).stream().filter(entity -> entity instanceof Player && (ignoreVis || (player.canSee((Player) entity)))).map(entity -> (Player)entity).collect(Collectors.toList()));
        return playerList;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Cosmetic && ((Cosmetic)obj).getName().equals(this.getName()) && ((Cosmetic)obj).getCosmeticType() == this.getCosmeticType();
    }
}
