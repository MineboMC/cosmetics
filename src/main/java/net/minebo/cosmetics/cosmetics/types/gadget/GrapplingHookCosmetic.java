package net.minebo.cosmetics.cosmetics.types.gadget;

import net.minebo.cobalt.util.ItemBuilder;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class GrapplingHookCosmetic extends Cosmetic
{
    public ItemStack itemStack;
    
    public GrapplingHookCosmetic() {
        this.itemStack = new ItemBuilder(Material.FISHING_ROD).setName(ChatColor.GOLD + "Grappling Hook").setUnbreakable(true).build();
    }
    
    @Override
    public String getName() {
        return "Grappling Hook";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&6Grappling Hook");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.GADGET;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.grapple");
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Fling yourself around the map with", ChatColor.WHITE + "our patented " + ChatColor.GOLD + "Grapple Hook 2000" + ChatColor.WHITE + ".");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.FISHING_ROD).build();
    }
    
    @Override
    public void apply(final Player player) {
        player.getInventory().setItem(3, this.itemStack);
        player.updateInventory();
    }
    
    @Override
    public void tick(final Player player) {
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public void remove(final Player player) {
        this.unselectCosmetic(player);
        player.getInventory().clear(3);
        player.updateInventory();
    }
    
    @EventHandler
    public void onGrappleHookUse(final PlayerFishEvent e) {
        if (!e.getPlayer().getItemInHand().isSimilar(this.itemStack)) {
            return;
        }
        if (!e.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)) {
            return;
        }
        e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(5.0));
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 1.0f);
        e.getPlayer().updateInventory();
    }
}
