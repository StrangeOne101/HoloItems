package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.itemevent.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class GroundpoundBoots extends CustomItem implements Interactable {
    public GroundpoundBoots() {
        super("groundpound_boots", Material.IRON_BOOTS);
        this.setDisplayName("Groundpound Boots")
                .addLore(ChatColor.GRAY + "Transfer all of your fall damage to your enemy!")
                .addLore("Durability: {durability}");
        this.setArmor(3);
    }

    @Override
    public ItemStack buildStack(Player player) {
        return super.buildStack(player);
    }

    @Override
    public int getMaxDurability() {
        return 350;
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        if (player.getInventory().getItem(EquipmentSlot.FEET) == null || player.getInventory().getItem(EquipmentSlot.FEET).getType() == Material.AIR) {
            player.getInventory().setItem(EquipmentSlot.FEET, new ItemStack(itemStack));
            itemStack.setAmount(0);
            EventCache.fullCache(player);
        }
        return true;
    }

    @ItemEvent(active = ActiveConditions.EQUIPED, target = Target.SELF)
    public void onPlayerDamage(EventContext context, EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Collection<Entity> entities = event.getEntity().getWorld().getNearbyEntities(context.getPlayer().getLocation(), 4, 4, 2, (entity) -> (entity.getType().isAlive() && entity != context.getPlayer()));
            if (entities.size() > 0) {
                double damage = event.getDamage();
                event.setCancelled(true);
                for (Entity entity: entities) {
                    EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent(context.getPlayer(), entity, EntityDamageEvent.DamageCause.MAGIC, damage);
                    Bukkit.getPluginManager().callEvent(damageEvent);
                    if (!damageEvent.isCancelled()) {
                        ((Damageable) entity).damage(damage);
                    }
                    damageItem(context.getStack(), (((int) damage) * 2), context.getPlayer());
                    if (entity instanceof Monster) {
                        if (((Monster) entity).getTarget() == null) {
                            ((Monster) entity).setTarget(context.getPlayer());
                        }
                    }
                }
                return;
            }
        }
        damageItem(context.getStack(), 1, context.getPlayer());
    }
}
