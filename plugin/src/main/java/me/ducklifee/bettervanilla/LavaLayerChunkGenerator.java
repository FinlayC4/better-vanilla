package me.ducklifee.bettervanilla;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public class LavaLayerChunkGenerator extends ChunkGenerator {

    @Override
    public void generateCaves(
        WorldInfo worldInfo,
        Random random,
        int chunkX,
        int chunkZ,
        ChunkData chunkData
    ) {
        // Since we are overriding the generateCaves method, we need to call
        // the super method to ensure that the vanilla cave generation still occurs
        super.generateCaves(worldInfo, random, chunkX, chunkZ, chunkData);

        stripLavaLayerFromChunk(worldInfo.getMinHeight(), -55, chunkData);
    }

    private void stripLavaLayerFromChunk(int minY, int maxY, ChunkData chunkData) {
        // Starting with Y keeps internal ChunkSection pointer cached and stable
        for (int y = minY; y <= maxY; y++) {
            for (int z = 0; z < 16; z++) {
                for (int x = 0; x < 16; x++) {
                    if (chunkData.getType(x, y, z) == Material.LAVA) {
                        chunkData.setBlock(x, y, z, Material.AIR);
                    }
                }
            }
        }
    }

    // The defaults for these methods return false, so we override them
    // to return true so that the vanilla generation methods are called
    @Override public boolean shouldGenerateNoise() { return true; }
    @Override public boolean shouldGenerateSurface() { return true; }
    @Override public boolean shouldGenerateStructures() { return true; }
    @Override public boolean shouldGenerateDecorations() { return true; }
    @Override public boolean shouldGenerateCaves() { return true; }
    @Override public boolean shouldGenerateMobs() { return true; }
}