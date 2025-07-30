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

public class HeartHaloParticle extends Cosmetic
{
    private final Map<UUID, Integer> ringMap;
    
    public HeartHaloParticle() {
        this.ringMap = new HashMap<UUID, Integer>();
    }
    
    @Override
    public String getName() {
        return "Heart Halo";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&6Heart Halo");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.PARTICLE;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.hearthalo");
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Fall in love with the hearts", ChatColor.WHITE + "that follows your head!");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.GOLDEN_APPLE);
    }

    @Override
    public void apply(final Player player) {
        final BukkitRunnable runnable = new BukkitRunnable() {
            private double radius = 0.5;
            private double angle = Math.PI;

            public void run() {
                if (player == null || !player.isOnline()) {
                    this.cancel();
                    return;
                }
                final double x = this.radius * Math.sin(this.angle);
                final double z = this.radius * Math.cos(this.angle);
                this.angle -= 0.1;

                final Location baseLoc = player.getLocation();
                final Location particleLoc = new Location(
                        player.getWorld(),
                        baseLoc.getX() + x,
                        baseLoc.getY() + 2.0, // 2 blocks above player
                        baseLoc.getZ() + z
                );

                List<Player> nearbyPlayers = PlayerUtil.getNearbyPlayers(player, 50, false);

                for (Player p : nearbyPlayers) {
                    p.spawnParticle(
                            Particle.HEART,
                            particleLoc.getX(), particleLoc.getY(), particleLoc.getZ(),
                            1, // count
                            0.0, 0.0, 0.0, // offset
                            1.0 // extra, speed
                    );
                }
            }
        };
        runnable.runTaskTimer(Cosmetics.instance, 0L, 1L);
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
