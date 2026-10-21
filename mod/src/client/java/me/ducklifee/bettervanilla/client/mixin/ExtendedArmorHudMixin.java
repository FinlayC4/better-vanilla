package me.ducklifee.bettervanilla.client.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class ExtendedArmorHudMixin {

    // Shadow the sprite references from Hud so we can use them in our custom method
    @Shadow private static Identifier ARMOR_FULL_SPRITE;
    @Shadow private static Identifier ARMOR_HALF_SPRITE;
    @Shadow private static Identifier ARMOR_EMPTY_SPRITE;

    @Inject(
        method = "extractArmor",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void onExtractArmor(
        GuiGraphicsExtractor graphics,
        Player player,
        int yLineBase,
        int numHealthRows,
        int healthRowHeight,
        int xLeft,
        CallbackInfo ci
    ) {
        ci.cancel(); // Cancel vanilla's single-row renderer

        int armor = player.getArmorValue();
        if (armor <= 0) return;

        // Calculate where first armor row should start
        int baseArmorY = yLineBase - (numHealthRows - 1) * healthRowHeight - 10;
        int numArmorRows = (armor + 19) / 20;

        for (int row = 0; row < numArmorRows; row++) {
            int yLineArmor = baseArmorY - (row * 10);
            int rowArmor = armor - (row * 20);

            for (int i = 0; i < 10; ++i) {
                int xo = xLeft + i * 8;
                
                if (i * 2 + 1 < rowArmor) {
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_SPRITE, xo, yLineArmor, 9, 9);
                } else if (i * 2 + 1 == rowArmor) {
                    graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_HALF_SPRITE, xo, yLineArmor, 9, 9);
                } else {
                    if (row == 0 || rowArmor > 0) {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_EMPTY_SPRITE, xo, yLineArmor, 9, 9);
                    }
                }
            }
        }
    }
}