package net.minebo.cosmetics.cosmetics.types.armor;

import net.minebo.cobalt.util.ItemBuilder;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class ColorArmorCosmetic extends Cosmetic
{
    String name;
    String displayname;
    String permission;
    Color color;
    
    public ColorArmorCosmetic(final String name, final String displayname, final String permission, final Color color) {
        this.name = name;
        this.displayname = displayname;
        this.permission = permission;
        this.color = color;
    }
    
    @Override
    public String getName() {
        return this.name + " Armor";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', this.displayname + " Armor");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.ARMOR;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission(this.permission);
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "This armor cosmetic comes with the " + ChatColor.translateAlternateColorCodes('&', this.displayname) + ChatColor.WHITE + " rank!");
    }
    
    @Override
    public ItemStack getIcon() {
        return getColorArmor(Material.LEATHER_CHESTPLATE, color);
    }
    
    @Override
    public void apply(final Player player) {
        final ItemStack helmet = this.getColorArmor(Material.LEATHER_HELMET, this.color);
        final ItemStack chestplate = this.getColorArmor(Material.LEATHER_CHESTPLATE, this.color);
        final ItemStack leggings = this.getColorArmor(Material.LEATHER_LEGGINGS, this.color);
        final ItemStack boots = this.getColorArmor(Material.LEATHER_BOOTS, this.color);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
    }
    
    @Override
    public void tick(final Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        final ItemStack helmet = this.getColorArmor(Material.LEATHER_HELMET, this.color);
        final ItemStack chestplate = this.getColorArmor(Material.LEATHER_CHESTPLATE, this.color);
        final ItemStack leggings = this.getColorArmor(Material.LEATHER_LEGGINGS, this.color);
        final ItemStack boots = this.getColorArmor(Material.LEATHER_BOOTS, this.color);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public void remove(final Player player) {
        this.unselectCosmetic(player);
        player.getInventory().setArmorContents(null);
    }
    
    public ItemStack getColorArmor(final Material m, final Color c) {
        final ItemStack i = new ItemStack(m, 1);
        final LeatherArmorMeta meta = (LeatherArmorMeta)i.getItemMeta();
        meta.setColor(c);
        i.setItemMeta(meta);
        return i;
    }
}
