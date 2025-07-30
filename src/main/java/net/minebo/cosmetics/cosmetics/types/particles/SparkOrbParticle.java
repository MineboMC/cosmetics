package net.minebo.cosmetics.cosmetics.types.particles;

import net.minebo.cosmetics.cosmetics.Cosmetic;
import net.minebo.cosmetics.cosmetics.CosmeticType;
import net.minebo.cosmetics.Cosmetics;
import net.minebo.cosmetics.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.*;
import java.util.*;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.*;
import org.bukkit.*;

public class SparkOrbParticle extends Cosmetic
{
    private final Map<UUID, Integer> ringMap;
    
    public SparkOrbParticle() {
        this.ringMap = new HashMap<UUID, Integer>();
    }
    
    @Override
    public String getName() {
        return "Spark Orb";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&aSpark Orb");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.PARTICLE;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.sparkorb");
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Electricity flows through and around you!", ChatColor.WHITE + "Shock others with your powerful orb.");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.FIREWORK_ROCKET);
    }
    
    @Override
    public void apply(final Player player) {
        final BukkitRunnable runnable = new BukkitRunnable() {
            double phi = 0.0;

            public void run() {
                if (player == null || !player.isOnline()) {
                    this.cancel();
                    return;
                }
                final Location loc = player.getLocation();
                this.phi += 0.3141592653589793;

                List<Player> nearbyPlayers = PlayerUtil.getNearbyPlayers(player, 50, false);

                for (double theta = 0.0; theta <= 6.283185307179586; theta += 0.07853981633974483) {
                    final double r = 1.5;
                    final double x = r * Math.cos(theta) * Math.sin(this.phi);
                    final double y = r * Math.cos(this.phi) + 1.5;
                    final double z = r * Math.sin(theta) * Math.sin(this.phi);

                    loc.add(x, y, z);

                    for (Player p : nearbyPlayers) {
                        p.spawnParticle(
                                Particle.CRIT,
                                loc.getX(), loc.getY(), loc.getZ(),
                                1, // count
                                0.0, 0.0, 0.0, // offset
                                1.0 // extra (speed)
                        );
                    }

                    loc.subtract(x, y, z);
                }
            }
        };
        runnable.runTaskTimer(Cosmetics.instance, 0L, 6L);
        this.ringMap.put(player.getUniqueId(), runnable.getTaskId());
    }
    
    @Override
    public void tick(final Player player) {
    }
    
    @Override
    public void remove(final Player player) {
        this.unselectCosmetic(player);
        Bukkit.getScheduler().cancelTask(this.ringMap.remove(player.getUniqueId()));
    }
}
