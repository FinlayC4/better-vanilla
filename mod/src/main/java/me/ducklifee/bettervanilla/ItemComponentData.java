package me.ducklifee.bettervanilla;

import java.util.Map;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class ItemComponentData {
    
    private ItemComponentData() {}
    
    public static final Map<Item, Integer> MAX_STACK_SIZES = Map.ofEntries(
        // Stack Size: 1
        Map.entry(Items.GOLDEN_APPLE, 1),
        Map.entry(Items.ENCHANTED_GOLDEN_APPLE, 1),

        // Stack Size: 8
        Map.entry(Items.BREAD, 8),
        Map.entry(Items.BAKED_POTATO, 8),
        Map.entry(Items.GOLDEN_CARROT, 8),
        Map.entry(Items.CHORUS_FRUIT, 8),
        Map.entry(Items.PUMPKIN_PIE, 8),
        Map.entry(Items.TROPICAL_FISH, 8),
        Map.entry(Items.PORKCHOP, 8),
        Map.entry(Items.MUTTON, 8),
        Map.entry(Items.CHICKEN, 8),
        Map.entry(Items.COD, 8),
        Map.entry(Items.SALMON, 8),
        Map.entry(Items.BEEF, 8),
        Map.entry(Items.RABBIT, 8),
        Map.entry(Items.COOKED_PORKCHOP, 8),
        Map.entry(Items.COOKED_MUTTON, 8),
        Map.entry(Items.COOKED_CHICKEN, 8),
        Map.entry(Items.COOKED_COD, 8),
        Map.entry(Items.COOKED_SALMON, 8),
        Map.entry(Items.COOKED_BEEF, 8),
        Map.entry(Items.COOKED_RABBIT, 8),

        // Stack Size: 32
        Map.entry(Items.CARROT, 32),
        Map.entry(Items.POTATO, 32),
        Map.entry(Items.BEETROOT, 32),
        Map.entry(Items.POISONOUS_POTATO, 32),
        Map.entry(Items.MELON_SLICE, 32),
        Map.entry(Items.GLOW_BERRIES, 32),
        Map.entry(Items.APPLE, 32),
        Map.entry(Items.COOKIE, 32),
        Map.entry(Items.SWEET_BERRIES, 32),
        Map.entry(Items.DRIED_KELP, 32)
    );

    // The max durability of items
    public static final Map<Item, Integer> MAX_DAMAGE = Map.ofEntries(
        // Golden Weapons and Tools
        Map.entry(Items.GOLDEN_SWORD, 750),
        Map.entry(Items.GOLDEN_PICKAXE, 750),
        Map.entry(Items.GOLDEN_SPEAR, 750),
        Map.entry(Items.GOLDEN_SHOVEL, 750),
        Map.entry(Items.GOLDEN_HOE, 750),
        Map.entry(Items.GOLDEN_AXE, 750),

        // Gold Armour
        Map.entry(Items.GOLDEN_HELMET, 248),
        Map.entry(Items.GOLDEN_CHESTPLATE, 336),
        Map.entry(Items.GOLDEN_LEGGINGS, 315),
        Map.entry(Items.GOLDEN_BOOTS, 273)
    );
}
