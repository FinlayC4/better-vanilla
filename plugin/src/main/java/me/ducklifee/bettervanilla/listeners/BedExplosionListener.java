package me.ducklifee.bettervanilla.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.papermc.paper.event.player.PlayerBedFailEnterEvent;

public class BedExplosionListener implements Listener {

    private static final float EXPLOSION_POWER = 1.5f;
    
    @EventHandler
    public void onPlayerBedFailEnter(PlayerBedFailEnterEvent event) {
        // We only want to do my own explosion if it was actually going to explode
        if (!event.getWillExplode()) return;

        // Prevent explosion as I will do my own
        event.setWillExplode(false);

        Block bedBlock = event.getBed();
        
        if (bedBlock.getBlockData() instanceof Bed bedData) {
            removeBed(bedBlock, bedData);
        }

        // Create an explosion that breaks blocks and creates fire
        bedBlock.getLocation()
            .add(0.5, 0.5, 0.5) // Center in middle of bed block
            .createExplosion(EXPLOSION_POWER, true, true);
    }

    private static void removeBed(Block bedBlock, Bed bedData) {
        Block otherBedHalf = getOtherBedHalf(bedData, bedBlock);

        // Ensure we don't remove the other half when it isn't a bed
        if (otherBedHalf.getBlockData() instanceof Bed) {
            otherBedHalf.setType(Material.AIR, false);
        }

        // Remove the main bed block
        bedBlock.setType(Material.AIR, false);
    }

    private static Block getOtherBedHalf(Bed bedData, Block bedBlock) {
        BlockFace facing = bedData.getFacing();

        return (bedData.getPart() == Bed.Part.FOOT)
            ? bedBlock.getRelative(facing)
            : bedBlock.getRelative(facing.getOppositeFace());
    }
}
