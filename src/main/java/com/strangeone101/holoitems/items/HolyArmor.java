package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class HolyArmor extends CustomItem {

    EntityType[] UNDEAD = new EntityType[] {EntityType.DROWNED, EntityType.HUSK, EntityType.PHANTOM, EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER, EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN};

    public HolyArmor (String name, Material armor_parts, String displayName) {
        super(name, armor_parts, displayName);
        this.addLore("The nemesis of the undead!");
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.EQUIPED)
    public void onPlayerDamage(EventContext context, EntityDamageByEntityEvent event) {
        if (context.getPlayer() == event.getEntity()) {
            if (Arrays.asList(UNDEAD).contains(event.getDamager().getType())) {
                event.setDamage(event.getDamage() * 3 / 4);
            }
        }
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.EQUIPED)
    public void onPotionEffect(EventContext context, EntityPotionEffectEvent event) {
        if (context.getPlayer() == event.getEntity()) {
            if (event.getNewEffect() != null) {
                if (event.getNewEffect().getType().equals(PotionEffectType.WITHER)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
