package me.ducklifee.bettervanilla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents.ModifyContext;
import net.fabricmc.fabric.api.registry.LandPathTypeRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterVanilla implements ModInitializer {
	public static final String MOD_ID = "better-vanilla";

	// Integer must not be more than 99 as this is max stack size
	public static final Map<Item, Integer> MAX_STACK_SIZES = Map.ofEntries();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("BetterVanilla mod initialised.");

		DefaultItemComponentEvents.MODIFY.register(context -> {
			
			// Make rotttn flesh compostable
            context.modify(Items.ROTTEN_FLESH, builder -> {
                builder.set(
                    DataComponents.COMPOSTABLE,
                    new Compostable(new ResolvableInt.Constant(3)) // Integer is layers to fill in composter
                );
			});

			registerMaxStackSizes(context);
		});

		makeLeavesBlocksPathfindable();

		CoordinatesHud.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
	
	private static void registerMaxStackSizes(ModifyContext context) {
		MAX_STACK_SIZES.forEach((item, maxStackSize) -> {
			context.modify(item, builder -> {
				builder.set(
					DataComponents.MAX_STACK_SIZE,
					maxStackSize
				);
			});
		});
	}

	private void makeLeavesBlocksPathfindable() {
		// Make any block with the LEAVES block tag so entities can pathfind through it
		for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof LeavesBlock) {
                LandPathTypeRegistry.register(block, PathType.WALKABLE, PathType.WALKABLE);
            }
        }
	}
}
