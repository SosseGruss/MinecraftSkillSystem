package io.github.sossegruss.minecraftSkillSystem.listener;

import io.github.sossegruss.minecraftSkillSystem.inventories.XPInfoInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        if(inventory.getHolder() instanceof XPInfoInventory){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvenoryDragEvent(InventoryDragEvent event){
        Inventory inventory = event.getInventory();
        if(inventory.getHolder() instanceof XPInfoInventory){
            event.setCancelled(true);
        }
    }
}
