package me.ducklifee.bettervanilla.mixin;

import net.minecraft.world.level.block.BedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Vanilla beds explode when you try to sleep somewhere beds don't work
 * (Nether, End). Hook the explode() call in destroyOnUse and swap the
 * power argument for a smaller one.
 */

// Any new beds that explode in nether/end will need to be added to Mixin declaration args
// Straw bed does not explode as of 26.3 hence it has been left out
@Mixin(BedBlock.class)
public abstract class BedExplosionMixin {

    // Vanilla was 5.0F in 1.21.x
    private static final float EXPLOSION_POWER = 1.5F;

    // require = 1 so a missing hook crashes with a clear error while you test.
    // Once it works, you can change it to 0 if you'd rather get a warning
    // than a crash after a future Minecraft update.
    @ModifyArg(
        method = "destroyOnUse",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode"),
        require = 1
    )
    private float smallerBedExplosion(float vanillaPower) {
        return EXPLOSION_POWER;
    }
}