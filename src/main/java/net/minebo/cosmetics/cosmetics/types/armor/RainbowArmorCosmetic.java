package net.minebo.cosmetics.cosmetics.types.armor;

import net.minebo.cobalt.util.ItemBuilder;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import net.minebo.cosmetics.Cosmetics;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class RainbowArmorCosmetic extends Cosmetic
{
    private static List<Color> colorList;
    static int lastSelected;
    
    public RainbowArmorCosmetic() {
        Bukkit.getScheduler().runTaskTimer(Cosmetics.instance, () -> {
            if (RainbowArmorCosmetic.lastSelected >= RainbowArmorCosmetic.colorList.size() - 1) {
                RainbowArmorCosmetic.lastSelected = 0;
            }
            else {
                ++RainbowArmorCosmetic.lastSelected;
            }
        }, 0L, 1L);
    }
    
    @Override
    public String getName() {
        return "Rainbow Armor";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&cR&ea&ai&bn&db&co&ew &cA&er&am&bo&dr");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.ARMOR;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.rainbowarmor");
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Stand out from the crowd and look", ChatColor.WHITE + "snazzy with your rainbow armor!");
    }
    
    @Override
    public ItemStack getIcon() {
        return getColorArmor(Material.LEATHER_CHESTPLATE, getColor());
    }
    
    @Override
    public void apply(final Player player) {
        final Color color = getColor();
        final ItemStack helmet = this.getColorArmor(Material.LEATHER_HELMET, color);
        final ItemStack chestplate = this.getColorArmor(Material.LEATHER_CHESTPLATE, color);
        final ItemStack leggings = this.getColorArmor(Material.LEATHER_LEGGINGS, color);
        final ItemStack boots = this.getColorArmor(Material.LEATHER_BOOTS, color);
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
        final Color color = getColor();
        final ItemStack helmet = this.getColorArmor(Material.LEATHER_HELMET, color);
        final ItemStack chestplate = this.getColorArmor(Material.LEATHER_CHESTPLATE, color);
        final ItemStack leggings = this.getColorArmor(Material.LEATHER_LEGGINGS, color);
        final ItemStack boots = this.getColorArmor(Material.LEATHER_BOOTS, color);
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
    
    public static Color getColor() {
        return RainbowArmorCosmetic.colorList.get(RainbowArmorCosmetic.lastSelected);
    }
    
    public ItemStack getColorArmor(final Material m, final Color c) {
        final ItemStack i = new ItemStack(m, 1);
        final LeatherArmorMeta meta = (LeatherArmorMeta)i.getItemMeta();
        meta.setColor(c);
        i.setItemMeta((ItemMeta)meta);
        return i;
    }
    
    static {
        RainbowArmorCosmetic.colorList = Arrays.asList(Color.fromRGB(255, 0, 0), Color.fromRGB(255, 68, 0), Color.fromRGB(255, 111, 0), Color.fromRGB(255, 171, 0), Color.fromRGB(255, 255, 0), Color.fromRGB(188, 255, 0), Color.fromRGB(128, 255, 0), Color.fromRGB(43, 255, 0), Color.fromRGB(0, 255, 9), Color.fromRGB(0, 255, 51), Color.fromRGB(0, 255, 111), Color.fromRGB(0, 255, 162), Color.fromRGB(0, 255, 230), Color.fromRGB(0, 239, 255), Color.fromRGB(0, 196, 255), Color.fromRGB(0, 173, 255), Color.fromRGB(0, 162, 255), Color.fromRGB(0, 137, 255), Color.fromRGB(0, 100, 255), Color.fromRGB(0, 77, 255), Color.fromRGB(0, 34, 255), Color.fromRGB(17, 0, 255), Color.fromRGB(37, 0, 255), Color.fromRGB(68, 0, 255), Color.fromRGB(89, 0, 255), Color.fromRGB(102, 0, 255), Color.fromRGB(124, 0, 255), Color.fromRGB(154, 0, 255), Color.fromRGB(222, 0, 255), Color.fromRGB(255, 0, 247), Color.fromRGB(255, 0, 247), Color.fromRGB(255, 0, 179), Color.fromRGB(255, 0, 128));
        RainbowArmorCosmetic.lastSelected = 1;
    }
}
