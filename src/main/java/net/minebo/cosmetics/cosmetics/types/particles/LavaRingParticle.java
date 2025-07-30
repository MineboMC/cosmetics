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

public class LavaRingParticle extends Cosmetic
{
    private final Map<UUID, Integer> ringMap;
    
    public LavaRingParticle() {
        this.ringMap = new HashMap<UUID, Integer>();
    }
    
    @Override
    public String getName() {
        return "Lava Ring";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&cLava Ring");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.PARTICLE;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.lavaring");
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Did someone turn the heating up?", ChatColor.WHITE + "Summon a burning hot ring around you!");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.BLAZE_POWDER);
    }
    
    @Override
    public void apply(final Player player) {
        final BukkitRunnable runnable = new BukkitRunnable() {
            private double radius = 1.5;
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
                double y = baseLoc.getY();

                List<Player> nearbyPlayers = PlayerUtil.getNearbyPlayers(player, 50, false);

                for (int i = 0; i < 4; ++i) {
                    Location particleLoc = new Location(
                            player.getWorld(),
                            baseLoc.getX() + x,
                            y,
                            baseLoc.getZ() + z
                    );
                    for (Player p : nearbyPlayers) {
                        p.spawnParticle(
                                Particle.DRIPPING_LAVA,
                                particleLoc.getX(), particleLoc.getY(), particleLoc.getZ(),
                                5, // count
                                0.0, 0.0, 0.0, // offset
                                0.0 // extra, speed
                        );
                    }
                    y += 0.5;
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
