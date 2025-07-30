package net.minebo.cosmetics.cosmetics;

import lombok.AllArgsConstructor;
import net.minebo.cosmetics.Cosmetics;
import org.bukkit.*;
import java.util.*;
import java.util.stream.*;

@AllArgsConstructor
public enum CosmeticType {

    ARMOR("Armor", Material.LEATHER_CHESTPLATE), 
    GADGET("Gadgets", Material.BEACON), 
    PARTICLE("Particles", Material.BLAZE_POWDER);
    
    public String displayName;
    public Material icon;
    
    public List<Cosmetic> getCosmetics() {
        return CosmeticHandler.cosmetics.stream().filter(iCosmetic -> iCosmetic.getCosmeticType() == this).collect(Collectors.toList());
    }

}
