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
import org.bukkit.util.Vector;

public class ButterflyWingsParticle extends Cosmetic
{
    private final Map<UUID, Integer> ringMap;
    
    public ButterflyWingsParticle() {
        this.ringMap = new HashMap<UUID, Integer>();
    }
    
    @Override
    public String getName() {
        return "Butterfly Wings";
    }
    
    @Override
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', "&bButterfly Wings");
    }
    
    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.PARTICLE;
    }
    
    @Override
    public boolean hasPermission(final Player player) {
        return player.hasPermission("cosmetic.butterflywings");
    }
    
    @Override
    public boolean noPermissionHide() {
        return false;
    }
    
    @Override
    public List<String> getDescription() {
        return Arrays.asList(ChatColor.WHITE + "Spread your wings and fly high", ChatColor.WHITE + "into the sky with your magnificent wings!");
    }
    
    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.EGG);
    }

    @Override
    public void apply(final Player player) {
        final BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                if (player == null || !player.isOnline()) {
                    this.cancel();
                    return;
                }
                final Location loc = player.getEyeLocation().subtract(0.0, 0.3, 0.0);
                loc.setPitch(0.0f);
                loc.setYaw(player.getEyeLocation().getYaw());
                final Vector v1 = loc.getDirection().normalize().multiply(-0.3);
                v1.setY(0);
                loc.add(v1);

                // Blue color for wings (customize as needed)
                Particle.DustOptions blueDust = new Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 1.0F);

                List<Player> nearbyPlayers = PlayerUtil.getNearbyPlayers(player, 50, false);

                for (double i = -10.0; i < 6.2; i += 0.2) {
                    final double var = Math.sin(i / 12.0);
                    final double v2 = Math.exp(Math.cos(i)) - 2.0 * Math.cos(4.0 * i) - Math.pow(var, 5.0);
                    final double x = Math.sin(i) * v2 / 2.0;
                    final double z = Math.cos(i) * v2 / 2.0;
                    final Vector v3 = new Vector(-x, 0.0, -z);

                    rotateAroundAxisX(v3, (loc.getPitch() + 90.0f) * 0.017453292f);
                    rotateAroundAxisY(v3, -loc.getYaw() * 0.017453292f);

                    Location particleLoc = loc.clone().add(v3);

                    for (Player p : nearbyPlayers) {
                        p.spawnParticle(
                                Particle.DUST,
                                particleLoc.getX(), particleLoc.getY(), particleLoc.getZ(),
                                1, // count
                                0.0, 0.0, 0.0, // offset
                                0.0, // extra
                                blueDust
                        );
                    }
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
        final Integer taskID = this.ringMap.remove(player.getUniqueId());
        if (taskID != null) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
    
    private void rotateAroundAxisX(final Vector v, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;
        v.setY(y).setZ(z);
    }
    
    private void rotateAroundAxisY(final Vector v, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos + v.getZ() * sin;
        final double z = v.getX() * -sin + v.getZ() * cos;
        v.setX(x).setZ(z);
    }
}
