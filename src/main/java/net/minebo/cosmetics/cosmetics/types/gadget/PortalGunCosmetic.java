package net.minebo.cosmetics.cosmetics.types.gadget;

import net.minebo.cobalt.util.ItemBuilder;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class PortalGunCosmetic extends Cosmetic
{
    public ItemStack itemStack;
    
    public PortalGunCosmetic() {
        this.itemStack = new ItemBuilder(Material.BOW).setName(ChatColor.LIGHT_PURPLE + "Portal Gun").setUnbreakable(true).addEnchantment(Enchantment.INFINITY, 1).build();
    }
    
    @Override
    public String getName() {
        return "Portal Gun";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&dPortal Gun");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.GADGET;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.bow");
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Teleport yourself around the map with", ChatColor.WHITE + "our patented " + ChatColor.LIGHT_PURPLE + "Portal Gun" + ChatColor.WHITE + ".");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.BOW).build();
    }
    
    @Override
    public void apply(final Player player) {
        player.getInventory().setItem(3, this.itemStack);
        player.getInventory().setItem(9, new ItemBuilder(Material.ARROW).build());
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
        player.getInventory().clear(9);
        player.updateInventory();
    }
    
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow) || !(e.getEntity().getShooter() instanceof Player)) {
            return;
        }
        final Player shooter = (Player)e.getEntity().getShooter();
        final Location loc = e.getEntity().getLocation();
        loc.setPitch(shooter.getLocation().getPitch());
        loc.setYaw(shooter.getLocation().getYaw());
        shooter.teleport(loc);
        e.getEntity().remove();
        shooter.playSound(shooter.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        shooter.updateInventory();
    }
}
