package me.ducklifee.bettervanilla;

import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import me.ducklifee.bettervanilla.listeners.BedExplosionListener;
import me.ducklifee.bettervanilla.listeners.DeathDropsListener;
import me.ducklifee.bettervanilla.listeners.GoatHornTauntListener;

public class BetterVanilla extends JavaPlugin {

    private CoordinatesHud coordinatesHud;

    @Override
    public void onEnable() {
        getLogger().info("BetterVanilla has been enabled.");

        registerListeners();

        this.coordinatesHud = new CoordinatesHud(this);
        coordinatesHud.activate();
    }

    @Override
    public void onDisable() {
        coordinatesHud.deactivate();

        getLogger().info("BetterVanilla has been disabled.");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new LavaLayerChunkGenerator();
    }

    private void registerListeners() {
        Listener[] listeners = {
            new DeathDropsListener(),
            new GoatHornTauntListener(),
            new BedExplosionListener()
        };

        for (Listener l : listeners) {
            getServer().getPluginManager().registerEvents(l, this);
        }
    }
}