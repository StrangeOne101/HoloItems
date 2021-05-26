package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class GroundpoundBoots extends CustomItem {
    public GroundpoundBoots() {
        super("grounpound_boots", Material.LEATHER_BOOTS);
        this.setDisplayName("Groundpound Boots")
                .addLore("")
                .addLore("Groundpound your enemies!")
                .addLore(ChatColor.YELLOW + "Transfer fall damage to enemies");
    }

    @Override
    public int getMaxDurability() {
        return 200;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.EQUIPED)
    public void onDamage(EventContext context, EntityDamageEvent event) {
        System.out.println("True 1");
        if (event.getEntity() == context.getPlayer()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                List<Entity> entityList = event.getEntity().getNearbyEntities(3,3,3);
                if (entityList.size() > 0) {
                    boolean found = false;
                    for (Entity entity: entityList) {
                        if (entity instanceof Damageable) {
                            found = true;
                            EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent(event.getEntity(), entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, event.getDamage());
                            Bukkit.getPluginManager().callEvent(damageEvent);
                            if (!event.isCancelled()) {
                                ((Damageable) entity).damage(event.getDamage());
                            }
                        }
                    }
                    if (found) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
