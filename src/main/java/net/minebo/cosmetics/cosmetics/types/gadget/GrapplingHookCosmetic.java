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
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

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
        Player player = e.getPlayer();
        ItemStack handItem = player.getInventory().getItemInMainHand();

        // Only proceed if correct item is used
        if (!handItem.isSimilar(this.itemStack)) return;

        // Only proceed if reeling in
        if (e.getState() != PlayerFishEvent.State.REEL_IN) return;

        // Pull player toward hook
        Location hookLocation = e.getHook().getLocation();
        Location playerLocation = player.getLocation();
        Vector direction = hookLocation.toVector().subtract(playerLocation.toVector()).normalize().multiply(3);
        direction.setY(0.7); // Upward boost

        player.setVelocity(direction);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 1.0f);
    }
}
