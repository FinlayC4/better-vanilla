package me.ducklifee.bettervanilla;

import java.util.Set;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * Shows a player's coordinates above their hotbar while they hold a compass.
 * Runs entirely on the server, so players do not need the mod installed.
 */
public final class CoordinatesHud {

    // Items that, when held in either hand, provide the coords
    private static final Set<Item> COORDS_PROVIDERS = Set.of(
        Items.COMPASS,
        Items.RECOVERY_COMPASS
    );

    // 20 ticks = 1 second
    private static final int UPDATE_INTERVAL_TICKS = 5;

    private CoordinatesHud() {}

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(CoordinatesHud::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        if (server.getTickCount() % UPDATE_INTERVAL_TICKS != 0) return;

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            updatePlayerCoords(player);
        }
    }

    private static void updatePlayerCoords(ServerPlayer player) {
        // Don't send coords to the player if not holding a coords provider
        if (!isHoldingCoordsProvider(player)) return;

        // "true" = show as an action bar message above the hotbar
        player.sendSystemMessage(formatCoords(player.blockPosition()), true);
    }

    private static Component formatCoords(BlockPos pos) {
        return Component.literal(pos.getX() + " " + pos.getY() + " " + pos.getZ());
    }

    private static boolean isHoldingCoordsProvider(ServerPlayer player) {
        return COORDS_PROVIDERS.contains(player.getMainHandItem().getItem())
            || COORDS_PROVIDERS.contains(player.getOffhandItem().getItem());
    }
}