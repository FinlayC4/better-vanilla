package me.ducklifee.bettervanilla.listeners;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathDropsListener implements Listener {
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        switch (event.getEntityType()) {
            case SHEEP -> removeWoolDrops(event);
            case ELDER_GUARDIAN -> addHeartOfTheSea(event);
            default -> {}
        }
    }

    private void removeWoolDrops(EntityDeathEvent event) {
        // Removes any wool drops as shears will be new main way of obtaining wool
        event.getDrops().removeIf(item ->
            Tag.WOOL.isTagged(item.getType())
        );
    }

    private void addHeartOfTheSea(EntityDeathEvent event) {
        // Makes elder guardian drop a single Heart of the Sea
        event.getDrops().add(
            new ItemStack(Material.HEART_OF_THE_SEA)
        );
    }
}
