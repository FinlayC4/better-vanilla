package me.ducklifee.bettervanilla.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
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
public class PassableLeavesMixin {

    private static final double MAX_FALL_SPEED = -0.6; // blocks/tick, more negative = faster
    private static final double HORIZONTAL_DRAG = 0.7; // Lower value for more sideways drag

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void makeLeavesPassable(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        CallbackInfoReturnable<VoxelShape> cir
    ) {
        // The state cache queries with EmptyBlockGetter. Leave that alone so
        // the block still counts as a full block for rendering and lighting

        // Removing the line below would result in the leaves blocks looking
        // different and vines not being placeable on them
        if (level == EmptyBlockGetter.INSTANCE) return;

        if (state.getBlock() instanceof LeavesBlock) {
            cir.setReturnValue(Shapes.empty()); // Removes collision
        }
    }

    // Applies movement drag / slowing effect inside the canopy
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
            // each tick in leaves, keep only 75% of the accumulated fall distance
            entity.fallDistance *= 0.75D;

            Vec3 v = entity.getDeltaMovement();
            double y = v.y;
            if (y < MAX_FALL_SPEED) {
                y = Math.max(MAX_FALL_SPEED, y * 0.7D);
            }
            entity.setDeltaMovement(v.x * HORIZONTAL_DRAG, y, v.z * HORIZONTAL_DRAG);
        }
    }
}