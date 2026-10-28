package me.ducklifee.bettervanilla;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents.ModifyContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;

public class ItemComponentModifications {

    public static void register() {
        DefaultItemComponentEvents.MODIFY.register(
            ItemComponentModifications::apply
        );
    }

    private static void apply(ModifyContext context) {
        modifyMaxStackSizes(context);
        modifyMaxDamage(context);
        makeRottenFleshCompostable(context);
        makeGlisteringMelonEdible(context);
    }

    private static void modifyMaxDamage(ModifyContext context) {
        ItemComponentData.MAX_DAMAGE.forEach((item, maxDamage) ->
            context.modify(item, builder ->
                builder.set(DataComponents.MAX_DAMAGE, maxDamage)
            )
        );
    }

    private static void modifyMaxStackSizes(ModifyContext context) {
        ItemComponentData.MAX_STACK_SIZES.forEach((item, maxStackSize) ->
            context.modify(item, builder ->
                builder.set(DataComponents.MAX_STACK_SIZE, maxStackSize)
            )
        );
    }

    private static void makeRottenFleshCompostable(ModifyContext context) {
        // Make rotten flesh compostable
        context.modify(Items.ROTTEN_FLESH, builder ->
            builder.set(
                DataComponents.COMPOSTABLE,
                // The integer is the layers to fill in composter
                new Compostable(new ResolvableInt.Constant(3))
            )
        );
    }

    private static void makeGlisteringMelonEdible(ModifyContext context) {
        context.modify(Items.GLISTERING_MELON_SLICE, builder -> {

            // Make glistering melon provide nutrition and saturation when eaten
            builder.set(
                DataComponents.FOOD,
                new FoodProperties(4, 6, true)
            );

            // Make glistering melon consumable
            builder.set(
                DataComponents.CONSUMABLE,
                // 1.6s consumption time is same as most food, including melon slice
                Consumable.builder()
                    .hasConsumeParticles(true)
                    .animation(ItemUseAnimation.EAT)
                    .sound(SoundEvents.GENERIC_EAT)
                    // leaving out consume seconds. Will default to 1.6
                    .build()
            );
        });
    }
}
