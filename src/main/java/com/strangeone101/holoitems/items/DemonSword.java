package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.abilities.DemonSwordAbility;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.ItemAbility;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class DemonSword extends CustomItem {
    public DemonSword() {
        super("demon_sword", Material.NETHERITE_SWORD, "Demon Sword");
        this.addLore("The sword... thirst for blood")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Each kill will increase the damage")
                .addLore(ChatColor.YELLOW + "of the sword by 5% fo 5 seconds")
                .addLore(ChatColor.YELLOW + "Stacks up to 4 times");
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);
        ItemMeta meta = stack.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", 7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        stack.setItemMeta(meta);
        return stack;
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.MAINHAND)
    public void onEntityDeath(EventContext context, EntityDeathEvent event) {
        Player player = context.getPlayer();
        if (player == event.getEntity().getKiller()) {
            if (ItemAbility.isAbilityActive(player, DemonSwordAbility.class)) {
                ItemAbility.getAbility(player, DemonSwordAbility.class).addStack();
            } else {
                PlayerInventory inv = player.getInventory();
                new DemonSwordAbility(player, inv.getItemInMainHand(), inv, inv.getHeldItemSlot());
            }
        }
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.MAINHAND)
    public void onEntityDamage(EventContext context, EntityDamageByEntityEvent event) {
        Player player = context.getPlayer();
        if (event.getDamager() == player) {
            if (ItemAbility.isAbilityActive(player, DemonSwordAbility.class)) {
                double damage = ItemAbility.getAbility(player, DemonSwordAbility.class).getDamage(event.getDamage());
                event.setDamage(damage);
            }
        }
    }
}
