package me.ducklifee.bettervanilla;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.LandPathTypeRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.pathfinder.PathType;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.ducklifee.bettervanilla.mixin.RangedAttributeAccessor;

public class BetterVanilla implements ModInitializer {
	public static final String MOD_ID = "better-vanilla";

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

		((RangedAttributeAccessor) Attributes.ARMOR.value()).setMaxValue(40.0);

		ItemComponentModifications.register();
		
		makeLeavesBlocksPathfindable();

		CoordinatesHud.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
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
