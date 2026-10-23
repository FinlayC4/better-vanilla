package me.ducklifee.bettervanilla.mixin;

import net.minecraft.world.level.block.BedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Any new beds that explode in Nether/End will need to be added to Mixin declaration args
// Straw bed does not explode as of 26.3 hence it has been left out
@Mixin(BedBlock.class)
public abstract class BedExplosionMixin {

    // Default explosion power is 5.0F for reference
    private static final float EXPLOSION_POWER = 1.5F;

    @ModifyArg(
        method = "destroyOnUse",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode"),
        require = 1 // Missing hook crashes with clear error
    )
    private float smallerBedExplosion(float vanillaPower) {
        return EXPLOSION_POWER;
    }
}