package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.BlockInteractable;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class BuilderWand extends CustomItem {

    public BuilderWand(String name, int capacity, int maxPlacements) {
        super(name, Material.STICK);
    }

    @ItemEvent(active = ActiveConditions.HELD)
    public void onInteract(EventContext context, PlayerInteractEvent event) {
        if (event.getPlayer().isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            //REFILL / OPEN INV GUI

        }

        
    }

    @ItemEvent(target = Target.SELF, active = ActiveConditions.MAINHAND)
    public void onSlotChange(EventContext context, PlayerItemHeldEvent event) {
        if (context.getPlayer().isSneaking()) {
            //TODO Change amount to place
        }
    }

    public class WandSession {

        public WandSession(int id) {

        }

    }
}
