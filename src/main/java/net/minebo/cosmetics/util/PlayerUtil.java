package net.minebo.cosmetics.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {

    /**
     * Returns a list of Players near the given center player within a radius.
     * @param center The player at the center of the search.
     * @param radius The radius to search for nearby players.
     * @param includeSelf Whether to include the center player in the result.
     * @return List of nearby players.
     */
    public static List<Player> getNearbyPlayers(Player center, double radius, boolean includeSelf) {
        List<Player> nearbyPlayers = new ArrayList<>();
        World world = center.getWorld();
        Location loc = center.getLocation();

        for (Entity entity : world.getNearbyEntities(loc, radius, radius, radius)) {
            if (entity instanceof Player) {
                nearbyPlayers.add((Player) entity);
            }
        }
        if (includeSelf) {
            nearbyPlayers.add(center);
        }
        return nearbyPlayers;
    }
}