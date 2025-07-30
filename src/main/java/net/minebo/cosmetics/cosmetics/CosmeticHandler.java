package net.minebo.cosmetics.cosmetics;

import net.minebo.cosmetics.cosmetics.player.CosmeticPlayer;
import net.minebo.cosmetics.cosmetics.types.armor.ColorArmorCosmetic;
import net.minebo.cosmetics.cosmetics.types.armor.RainbowArmorCosmetic;
import net.minebo.cosmetics.cosmetics.types.gadget.GrapplingHookCosmetic;
import net.minebo.cosmetics.cosmetics.types.gadget.PortalGunCosmetic;
import net.minebo.cosmetics.cosmetics.types.particles.*;
import net.minebo.cosmetics.Cosmetics;
import net.minebo.cosmetics.menu.CosmeticMenu;
import org.bukkit.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CosmeticHandler {

    public static List<Cosmetic> cosmetics = new ArrayList<>();
    public static List<CosmeticPlayer> cosmeticPlayers =  new ArrayList<>();
    public static RainbowArmorCosmetic rainbowArmorCosmetic = new RainbowArmorCosmetic();
    
    public static void init() {
        cosmetics.add(new BloodHelixParticle());
        cosmetics.add(new ButterflyWingsParticle());
        cosmetics.add(new HeartHaloParticle());
        cosmetics.add(new LavaRingParticle());
        cosmetics.add(new RainbowHelixParticle());
        cosmetics.add(new SparkOrbParticle());
        cosmetics.add(new VillagerRingParticle());
        cosmetics.add(new WaterRingParticle());
        cosmetics.add(rainbowArmorCosmetic);
        cosmetics.add(new ColorArmorCosmetic("Owner", "&4Owner", "cosmetic.owner", Color.RED));
        cosmetics.add(new ColorArmorCosmetic("Manager", "&4Manager", "cosmetic.manager", Color.RED));
        cosmetics.add(new ColorArmorCosmetic("Developer", "&bDeveloper", "cosmetic.developer", Color.AQUA));
        cosmetics.add(new ColorArmorCosmetic("Admin", "&cAdmin", "cosmetic.admin", Color.RED));
        cosmetics.add(new ColorArmorCosmetic("Mod", "&3Mod", "cosmetic.mod", Color.TEAL));
        cosmetics.add(new ColorArmorCosmetic("Trainee", "&eTrainee", "cosmetic.trainee", Color.YELLOW));
        cosmetics.add(new ColorArmorCosmetic("Partner", "&6&oPartner", "cosmetic.partner", Color.fromRGB(255, 215, 0)));
        cosmetics.add(new ColorArmorCosmetic("Media", "&dMedia", "cosmetic.media", Color.FUCHSIA));
        cosmetics.add(new ColorArmorCosmetic("Sentinel", "&bSentinel", "cosmetic.sentinel", Color.AQUA));
        cosmetics.add(new ColorArmorCosmetic("Bishop", "&dBishop", "cosmetic.bishop", Color.PURPLE));
        cosmetics.add(new ColorArmorCosmetic("Squire", "&aSquire", "cosmetic.squire", Color.GREEN));
        cosmetics.add(new GrapplingHookCosmetic());
        cosmetics.add(new PortalGunCosmetic());
        cosmetics.forEach(c -> Bukkit.getPluginManager().registerEvents(c, Cosmetics.instance));
    }

    public static void openCosmeticMenu(Player player) {
        CosmeticMenu.getCategoryMenu().openMenu(player);
    }

    public static CosmeticPlayer getPlayer(final UUID uuid) {
        return cosmeticPlayers.stream().filter(cosmeticPlayer -> cosmeticPlayer.getUuid().equals(uuid)).findFirst().orElse(null);
    }
    
    public static CosmeticPlayer getPlayer(final Player player) {
        return getPlayer(player.getUniqueId());
    }

}
