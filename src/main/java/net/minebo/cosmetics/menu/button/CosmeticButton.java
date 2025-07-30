package net.minebo.cosmetics.menu.button;

import net.minebo.cobalt.menu.construct.Button;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticHandler;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import net.minebo.cosmetics.cosmetics.player.CosmeticPlayer;
import net.minebo.cosmetics.Cosmetics;
import net.minebo.cosmetics.cosmetics.types.armor.ColorArmorCosmetic;
import net.minebo.cosmetics.cosmetics.types.armor.RainbowArmorCosmetic;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.event.inventory.*;
import java.util.*;

public class CosmeticButton extends Button {

    private final CosmeticType cosmeticType;
    private final Cosmetic cosmetic;
    private final Player menuPlayer;

    public CosmeticButton(CosmeticType cosmeticType, Cosmetic cosmetic, Player menuPlayer) {
        this.cosmeticType = cosmeticType;
        this.cosmetic = cosmetic;
        this.menuPlayer = menuPlayer;

        setName(() -> ChatColor.translateAlternateColorCodes('&', cosmetic.getDisplayName()));
        setLines(() -> getDescription(menuPlayer));

        // Click actions
        addClickAction(ClickType.LEFT, player -> {
            handleClick(player);
        });
        addClickAction(ClickType.RIGHT, player -> {
            handleClick(player);
        });
    }

    private List<String> getDescription(Player player) {
        List<String> loreList = new ArrayList<>();
        loreList.add("");
        loreList.addAll(cosmetic.getDescription());
        loreList.add("");
        final CosmeticPlayer cosmeticPlayer = CosmeticHandler.getPlayer(player);
        loreList.add(cosmetic.hasPermission(player)
                ? (cosmeticPlayer.getSelectedCosmetics().contains(cosmetic)
                ? (ChatColor.RED + "Click to disable this cosmetic!")
                : (ChatColor.GREEN + "Click to enable this cosmetic!"))
                : (ChatColor.RED + "You cannot use this cosmetic!"));
        return loreList;
    }

    @Override
    public ItemStack build() {
        ItemStack item = cosmetic.getIcon();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name.get());
            meta.setLore(getDescription(menuPlayer));
            item.setItemMeta(meta);
        }
        return item;
    }

    private void handleClick(Player player) {
        final CosmeticPlayer cosmeticPlayer = CosmeticHandler.getPlayer(player);

        if (!cosmetic.hasPermission(player)) {
            player.sendMessage(ChatColor.RED + "You cannot use this cosmetic.");
            return;
        }
        final Cosmetic selectedFromCategory = cosmeticPlayer.getCosmeticFromCategory(cosmeticType);
        if (selectedFromCategory == null) {
            if (cosmeticPlayer.isSelected(cosmetic)) {
                cosmetic.remove(player);
            } else {
                cosmeticPlayer.selectCosmetic(cosmetic);
            }
        } else if (selectedFromCategory == cosmetic) {
            cosmetic.remove(player);
        } else {
            selectedFromCategory.remove(player);
            cosmeticPlayer.selectCosmetic(cosmetic);
        }
    }
}