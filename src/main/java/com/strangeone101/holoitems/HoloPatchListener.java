package com.strangeone101.holoitems;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.persistence.PersistentDataType;

public class HoloPatchListener implements Listener {


    @EventHandler
    public void onFallingBlockPlace(EntityChangeBlockEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(Keys.getKeys().TEMP_ENTITY, PersistentDataType.INTEGER)) {
            event.setCancelled(true);
        }
    }

}
