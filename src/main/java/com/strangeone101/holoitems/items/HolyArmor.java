package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.itemevent.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HolyArmor extends CustomItem implements Interactable {
    static List<Class<? extends Entity>> UNDEAD = new ArrayList<>(Arrays.asList(Zombie.class, AbstractSkeleton.class, Zoglin.class, Wither.class));

    private EquipmentSlot equipmentSlot;

    public HolyArmor(String name, Material material, String displayName, int durability, EquipmentSlot equipmentSlot, int armor, int armorToughness) {
        super(name, material, displayName);
        this.addLore(ChatColor.GRAY + "Reduce damage from Undead monster")
                .addLore("Durability: {durability}");
        this.setMaxDurability(durability);
        this.equipmentSlot = equipmentSlot;
        this.setArmor(armor)
                .setArmorToughness(armorToughness);
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        if (player.getInventory().getItem(equipmentSlot) == null || player.getInventory().getItem(equipmentSlot).getType() == Material.AIR) {
            player.getInventory().setItem(equipmentSlot, new ItemStack(itemStack));
            itemStack.setAmount(0);
            EventCache.fullCache(player);
        }
        return true;
    }

    @ItemEvent(active = ActiveConditions.EQUIPED, target = Target.SELF)
    public void onPlayerDamage(EventContext context, EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            for (Class classCheck: UNDEAD) {
                if (classCheck.isAssignableFrom(event.getDamager().getType().getEntityClass())) {
                    event.setDamage(event.getDamage() * 0.75);
                    return;
                }
            }
        }
        damageItem(context.getStack(), 1, context.getPlayer());
    }

    @ItemEvent(active = ActiveConditions.EQUIPED, target = Target.SELF)
    public void onPlayerEffect(EventContext context, EntityPotionEffectEvent event) {
        if (event.isCancelled()) return;
        if ((event.getAction() == EntityPotionEffectEvent.Action.ADDED) || (event.getAction() == EntityPotionEffectEvent.Action.CHANGED)) {
            if (event.getModifiedType().equals(PotionEffectType.WITHER)) {
                event.setCancelled(true);
            }
        }
    }
}
