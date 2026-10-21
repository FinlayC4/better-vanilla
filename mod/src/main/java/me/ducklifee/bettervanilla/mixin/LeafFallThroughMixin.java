package me.ducklifee.bettervanilla.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class LeafFallThroughMixin {

    /**
     * Allows walking on top of leaves normally, but lets entities fall 
     * straight through when descending, stepping off, or dropping down.
     */
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void makeLeavesPassable(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        CallbackInfoReturnable<VoxelShape> cir
    ) {
        // This wont be true for any custom leaves blocks like from another mod
        if (state.getBlock() instanceof LeavesBlock) {
            cir.setReturnValue(Shapes.empty()); // Removes collision
        }
    }

    /**
     * Applies powder-snow-style drag and cushions fall distance 
     * as entities sink through the canopy.
     */
    @Inject(method = "entityInside", at = @At("HEAD"))
    private void applyLeafDrag(
        BlockState state,
        Level level,
        BlockPos pos,
        Entity entity,
        InsideBlockEffectApplier effectApplier,
        boolean isPrecise,
        CallbackInfo ci
    ) {
        if (state.getBlock() instanceof LeavesBlock) {
            // Apply movement drag (X/Z friction, Y downward resistance)
            entity.makeStuckInBlock(state, new Vec3(0.8D, 1.5D, 0.8D));
        }
    }
}