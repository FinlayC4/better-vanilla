package me.ducklifee.bettervanilla;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import net.kyori.adventure.text.Component;

public class CoordinatesHud {

    // Materials that when held in hand, provide the coords
    private static final Set<Material> COORDS_PROVIDERS = Set.of(
        Material.COMPASS,
        Material.RECOVERY_COMPASS
    );
    private static final long UPDATE_INTERVAL_TICKS = 5L;

    private final Plugin plugin;
    private BukkitTask task;

    public CoordinatesHud(Plugin plugin) {
        this.plugin = plugin;
    }

    public void activate() {
        // Ensure we dont run task when already running
        if (isTaskActive()) return;

        task = plugin.getServer().getScheduler().runTaskTimer(
            plugin,
            this::updatePlayersCoords,
            0L, // No initial delay
            UPDATE_INTERVAL_TICKS // Repeat interval
        );
    }

    public void deactivate() {
        if (task == null) return;

        task.cancel();
        task = null;
    }

    private boolean isTaskActive() {
        return task != null && !task.isCancelled();
    }

    private void updatePlayersCoords() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            updatePlayerCoords(player);
        }
    }

    private void updatePlayerCoords(Player player) {
        // Don't send coords to player if not holding a coords provider
        if (!isHoldingCoordsProvider(player.getEquipment())) {
            return;
        }

        // Displays coords above their hotbar
        player.sendActionBar(formatCoordsDisplay(player.getLocation()));
    }

    private static Component formatCoordsDisplay(Location loc) {
        return Component.text(
            loc.getBlockX() + " "
            + loc.getBlockY() + " "
            + loc.getBlockZ()
        );
    }

    private static boolean isHoldingCoordsProvider(EntityEquipment equipment) {
        return COORDS_PROVIDERS.contains(equipment.getItemInMainHand().getType())
            || COORDS_PROVIDERS.contains(equipment.getItemInOffHand().getType());
    }
}

