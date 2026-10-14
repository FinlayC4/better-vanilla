package me.ducklifee.bettervanilla.listeners;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.papermc.paper.event.player.PlayerItemCooldownEvent;

public class GoatHornTauntListener implements Listener {

    private static final int COOLDOWN_TICKS = 20 * 60; // 1 minute
    private static final int TAUNT_RADIUS = 30; // 30 blocks
    
    @EventHandler
    public void onPlayerItemCooldown(PlayerItemCooldownEvent event) {
        // Check that the goat horn was used
        if (event.getType() != Material.GOAT_HORN) return;

        event.setCooldown(COOLDOWN_TICKS); // 1 minute cooldown

        Player player = event.getPlayer();

        // Creative and spectator players can't be attacked, so don't taunt for them
        if (player.getGameMode().isInvulnerable()) return;
        
        tauntNearbyMonsters(player, TAUNT_RADIUS);
    }

    private static void tauntNearbyMonsters(Player player, int radius) {
        // Monsters within radius that aren't already targeting the player and can see them
        Collection<Monster> nearbyMonsters = player.getWorld().getNearbyEntitiesByType(
            Monster.class, // Using Monster as they attack players and are hostile
            player.getLocation(),
            radius, radius, radius,
            monster -> !player.equals(monster.getTarget()) // Don't retarget monsters on the player
                && monster.hasLineOfSight(player)
        );

        for (Monster monster : nearbyMonsters) {
            monster.setTarget(player);
        }
    }
}
