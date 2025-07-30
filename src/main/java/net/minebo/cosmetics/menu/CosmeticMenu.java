package net.minebo.cosmetics.menu;

import net.minebo.cobalt.menu.construct.Button;
import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cobalt.menu.construct.Menu;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import net.minebo.cosmetics.menu.button.CosmeticButton;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.inventory.ClickType;

import java.util.*;

public class CosmeticMenu {

    public static Menu getCategoryMenu() {
        Menu menu = new Menu().setTitle("Cosmetics")
                .setSize(9);

        int position = 2;
        for (CosmeticType type : CosmeticType.values()) {
            menu.setButton(position, new CategoryButton(type));
            position += 2;
        }

        menu.fillEmpty(Material.GRAY_STAINED_GLASS_PANE, true);
        return menu;
    }

    public static Menu getCosmeticMenu(Player player, CosmeticType cosmeticType) {
        Menu menu = new Menu().setTitle(cosmeticType.displayName)
                .setSize((cosmeticType == CosmeticType.PARTICLE) ? 18 : 27)
                .setAutoUpdate(true)
                .setUpdateAfterClick(true);

        // Place actual cosmetic buttons first
        int position = (cosmeticType == CosmeticType.ARMOR) ? 0 :
                (cosmeticType == CosmeticType.GADGET) ? 12 : 1;
        for (Cosmetic cosmetic : cosmeticType.getCosmetics()) {
            if (cosmetic.hasPermission(player) || !cosmetic.noPermissionHide()) {
                menu.setButton(position, new CosmeticButton(cosmeticType, cosmetic, player));
                if(position == 7 && cosmeticType == CosmeticType.PARTICLE) {
                    position += 1;
                }
                position += (cosmeticType == CosmeticType.ARMOR) ? 1 : 2;
            }
        }

        // Now fill empty slots
        menu.fillEmpty(Material.GRAY_STAINED_GLASS_PANE, true);

        return menu;
    }

    // Button for Category selection
    public static class CategoryButton extends Button {
        public CategoryButton(CosmeticType type) {
            setName(() -> ChatColor.BLUE + type.displayName);
            setMaterial(type.icon);
            setLines(() -> Collections.singletonList(ChatColor.GRAY + "View all the " + type.displayName + " cosmetics!"));
            addClickAction(ClickType.LEFT, player -> {
                player.closeInventory();
                getCosmeticMenu(player, type).openMenu(player);
            });
        }
    }
}