package me.ducklifee.bettervanilla.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {

    // How many layers above the bottom of the world keep the lava
    private static final int LAVA_LAYERS_AT_BOTTOM = 10;

    // The vanilla lava level. We never raise the level above this, so
    // dimensions with a normal min Y (Nether, End, vanilla overworld) are unchanged.
    private static final int VANILLA_LAVA_LEVEL = -54;

    @Inject(method = "createFluidPicker", at = @At("HEAD"), cancellable = true)
    private static void bettervanilla$lowerLavaLevel(
            NoiseGeneratorSettings settings,
            CallbackInfoReturnable<Aquifer.FluidPicker> cir
    ) {
        int minY = settings.noiseSettings().minY();
        int lavaLevel = Math.min(VANILLA_LAVA_LEVEL, minY + LAVA_LAYERS_AT_BOTTOM);
        int seaLevel = settings.seaLevel();

        Aquifer.FluidStatus lava =
                new Aquifer.FluidStatus(lavaLevel, Blocks.LAVA.defaultBlockState());
        Aquifer.FluidStatus defaultFluid =
                new Aquifer.FluidStatus(seaLevel, settings.defaultFluid());

        int threshold = Math.min(lavaLevel, seaLevel);

        cir.setReturnValue((x, y, z) -> y < threshold ? lava : defaultFluid);
    }
}