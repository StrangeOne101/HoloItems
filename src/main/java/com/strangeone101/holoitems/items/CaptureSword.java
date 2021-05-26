package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.Items;
import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.CustomItemRegistry;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.UUID;

public class CaptureSword extends CustomItem {
    static EntityType[] EXCEPTIONS = new EntityType[] {EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.IRON_GOLEM, EntityType.RAVAGER};

    public CaptureSword() {
        super("capture_sword", Material.GOLDEN_SWORD);
        this.setDisplayName("Capture Sword")
                .addLore("")
                .addLore("Capture mobs with this sword!")
                .addLore("Require 1 paper egg in your inv");
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);
        ItemMeta meta = stack.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        stack.setItemMeta(meta);
        return stack;
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.MAINHAND)
    public void onEntityDeath(EventContext context, EntityDeathEvent event) {
        Player player = context.getPlayer();
        Entity entity = event.getEntity();
        System.out.println("CaptureSword: onEntityDeath");
        if (entity.getType().isAlive() && !Arrays.asList(EXCEPTIONS).contains(entity.getType())) {
            if (player.getLocation().distance(entity.getLocation()) < 5) {
                ItemStack[] contents = player.getInventory().getContents();

                boolean found = false;
                for (ItemStack stack : contents) {
                    if (CustomItemRegistry.getCustomItem(stack) == Items.PAPER_EGG) {
                        stack.setAmount(stack.getAmount() - 1);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    String material_name = entity.getType().getKey().getKey().toUpperCase() + "_SPAWN_EGG";
                    Material material = Material.getMaterial(material_name);
                    if (material != null) {
                        ItemStack egg_stack = new ItemStack(material);
                        ItemMeta meta = egg_stack.getItemMeta();
                        meta.getPersistentDataContainer().set(Keys.getKeys().CUSTOM_SPAWN_EGG, PersistentDataType.STRING, context.getPlayer().getLocation().getWorld().getName());
                        egg_stack.setItemMeta(meta);
                        egg_stack.setAmount(1);
                        context.getPlayer().getInventory().addItem(egg_stack);
                    }
                }
            }
        }
    }

    @ItemEvent(target = Target.ALL)
    public void onPlayerPortalEvent(EventContext context, PlayerPortalEvent event) {
        System.out.println("CaptureSword: onPlayerPortalEvent");
        PlayerInventory inv = event.getPlayer().getInventory();
        int i = 0;
        for (ItemStack stack : inv.getContents()) {
            i += 1;
            if (stack != null) {
                if (stack.hasItemMeta()) {
                    if (stack.getItemMeta().getPersistentDataContainer().has(Keys.getKeys().CUSTOM_SPAWN_EGG, PersistentDataType.STRING)) {
                        if (stack.getItemMeta().getPersistentDataContainer().get(Keys.getKeys().CUSTOM_SPAWN_EGG, PersistentDataType.STRING) != event.getTo().getWorld().getName()) {
                            ItemStack corrupt_stack = Items.CORRUPT_PAPER_EGG.buildStack(null);
                            corrupt_stack.setAmount(stack.getAmount());
                            inv.setItem(i - 1, corrupt_stack);
                        }
                    }
                }
            }
        }
    }

    //To do check if the player took the item out of ender chest or entity
}