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
import org.bukkit.plugin.*;
import org.bukkit.*;

public class BloodHelixParticle extends Cosmetic
{
    private final Map<UUID, Integer> ringMap;
    
    public BloodHelixParticle() {
        this.ringMap = new HashMap<UUID, Integer>();
    }
    
    @Override
    public String getName() {
        return "Blood Helix";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&4Blood Helix");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.PARTICLE;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.bloodhelix");
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "You are a true demon...", ChatColor.WHITE + "The blood of your enemies surround you!");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.REDSTONE);
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
                this.phi += 0.39269908169872414;
                final Location location = player.getLocation();

                // Red color for REDSTONE/Dust particle
                Particle.DustOptions redDust = new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 0, 0), 1.0F);

                for (double t = 0.0; t <= 6.283185307179586; t += 0.19634954084936207) {
                    for (double i = 0.0; i <= 1.0; ++i) {
                        double x = 0.4 * (6.283185307179586 - t) * 0.5 * Math.cos(t + this.phi + i * Math.PI);
                        double y = 0.5 * t;
                        double z = 0.4 * (6.283185307179586 - t) * 0.5 * Math.sin(t + this.phi + i * Math.PI);

                        location.add(x, y, z);

                        List<Player> nearby = PlayerUtil.getNearbyPlayers(player, 50, false);
                        for (Player p : nearby) {
                            p.spawnParticle(
                                    Particle.DUST,
                                    location.getX(), location.getY(), location.getZ(),
                                    1, // count
                                    0.0, 0.0, 0.0, // offset
                                    0.0, // extra
                                    redDust
                            );
                        }

                        location.subtract(x, y, z);
                    }
                }
            }
        };
        runnable.runTaskTimer(Cosmetics.instance, 0L, 2L);
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
