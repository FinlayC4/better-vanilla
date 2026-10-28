package me.ducklifee.bettervanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class ItemComponentData {
    
    private ItemComponentData() {}
    
    public static final Map<Item, Integer> MAX_STACK_SIZES = new HashMap<>();
    public static final Map<Item, Integer> MAX_DAMAGE = new HashMap<>();

    static {
        // ============ Max Stack Sizes ============

        setMaxStackSize(1,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE
        );

        setMaxStackSize(8,
            Items.BREAD,
            Items.BAKED_POTATO,
            Items.GOLDEN_CARROT,
            Items.CHORUS_FRUIT,
            Items.PUMPKIN_PIE,
            Items.TROPICAL_FISH,
            Items.PORKCHOP,
            Items.MUTTON,
            Items.CHICKEN,
            Items.COD,
            Items.SALMON,
            Items.BEEF,
            Items.RABBIT,
            Items.COOKED_PORKCHOP,
            Items.COOKED_MUTTON,
            Items.COOKED_CHICKEN,
            Items.COOKED_COD,
            Items.COOKED_SALMON,
            Items.COOKED_BEEF,
            Items.COOKED_RABBIT
        );

        setMaxStackSize(32,
            Items.CARROT,
            Items.POTATO,
            Items.BEETROOT,
            Items.POISONOUS_POTATO,
            Items.MELON_SLICE,
            Items.GLOW_BERRIES,
            Items.APPLE,
            Items.COOKIE,
            Items.SWEET_BERRIES,
            Items.DRIED_KELP
        );

        // ============ Max Damage  ============

        // Golden Tools and Weapons
        setMaxDamage(750, // All tools and weapons of same tier have same max damage in Vanilla
            Items.GOLDEN_SWORD,
            Items.GOLDEN_PICKAXE,
            Items.GOLDEN_SPEAR,
            Items.GOLDEN_SHOVEL,
            Items.GOLDEN_HOE,
            Items.GOLDEN_AXE
        );

        // Golden Armour
        setMaxDamage(248, Items.GOLDEN_HELMET);
        setMaxDamage(336, Items.GOLDEN_CHESTPLATE);
        setMaxDamage(315, Items.GOLDEN_LEGGINGS);
        setMaxDamage(273, Items.GOLDEN_BOOTS);
    }

    private static void setMaxStackSize(int maxStackSize, Item... items) {
        putEntries(MAX_STACK_SIZES, maxStackSize, items);
    }

    private static void setMaxDamage(int maxDamage, Item... items) {
        putEntries(MAX_DAMAGE, maxDamage, items);
    }

    private static <T, V> void putEntries(Map<Item, V> map, V value, Item... items) {
        for (Item item : items) {
            map.put(item, value);
        }
    }
}
