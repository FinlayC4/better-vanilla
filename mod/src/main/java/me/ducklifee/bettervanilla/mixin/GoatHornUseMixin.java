package me.ducklifee.bettervanilla.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InstrumentItem.class)
public class GoatHornUseMixin {

    @Inject(method = "use", at = @At("RETURN"))
    private void onInstrumentUse(
        Level level,
        Player player,
        InteractionHand hand,
        CallbackInfoReturnable<InteractionResult> cir
    ) {
        InteractionResult result = cir.getReturnValue();
        
        // Check if the action was successfully consumed (horn played and cooldown applied)
        if (result.consumesAction()) {
            ItemStack itemStack = player.getItemInHand(hand);
            
            // Explicitly verify that the item being blown is specifically a Goat Horn
            if (itemStack.is(Items.GOAT_HORN)) {
                // Run strictly on the server side to prevent double-firing
                if (!level.isClientSide()) {
                    player.sendSystemMessage(Component.literal("horn used!"));
                }
            }
        }
    }
}