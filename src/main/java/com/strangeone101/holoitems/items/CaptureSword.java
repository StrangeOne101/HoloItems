package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.Items;
import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.CustomItemRegistry;
import com.strangeone101.holoitemsapi.interfaces.Swingable;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CaptureSword extends CustomItem implements Swingable {
    public CaptureSword() {
        super("capture_sword", Material.GOLDEN_SWORD);
        this.setDisplayName("Capture Sword")
                .addLore("Durability: {durability}");
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public int getMaxDurability() {
        return 150;
    }

    @Override
    public void swing(Player player, CustomItem customItem, ItemStack itemStack) {

    }

    @Override
    public double hit(Entity entity, Player player, CustomItem customItem, ItemStack itemStack, double damage) {
        customItem.damageItem(itemStack, 1, player);
        return damage;
    }

    @ItemEvent(active = ActiveConditions.MAINHAND, target = Target.WORLD)
    public void onEntityDeath(EventContext context, EntityDeathEvent event) {
        if (event.getEntity() instanceof Ageable) {
            if (((Ageable) event.getEntity()).isAdult()) {
                if (event.getEntity().getKiller() == context.getPlayer()) {
                    Material material = Material.getMaterial((event.getEntity().getType().getKey().getKey() + "_spawn_egg").toUpperCase());
                    if (material != null) {
                        Player player = context.getPlayer();
                        for (ItemStack stack: player.getInventory().getContents()) {
                            if (CustomItemRegistry.isCustomItem(stack)) {
                                if (CustomItemRegistry.getCustomItem(stack).getInternalID() == Items.PAPER_EGG.getInternalID()) {
                                    double random = Math.random();
                                    double chance = 1.0 / (event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2.0);
                                    if (chance > 0.2) chance = 0.2;
                                    if (random < chance) {
                                        stack.setAmount(stack.getAmount() - 1);
                                        ItemStack egg_stack = new ItemStack(material);
                                        ItemMeta meta = egg_stack.getItemMeta();
                                        meta.getPersistentDataContainer().set(Keys.getKeys().EGG_WORLD, PersistentDataType.STRING, event.getEntity().getLocation().getWorld().getName());
                                        egg_stack.setItemMeta(meta);
                                        event.getDrops().clear();
                                        event.getDrops().add(egg_stack);
                                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 2.0F, 1.0F);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @ItemEvent(active = ActiveConditions.NONE, target = Target.ALL)
    public void onPortal(EventContext context, PlayerPortalEvent event) {
        int i = 0;
        for (ItemStack stack: event.getPlayer().getInventory().getContents()) {
            i++;
            if (stack != null) {
                if (stack.hasItemMeta()) {
                    if (stack.getItemMeta().getPersistentDataContainer().has(Keys.getKeys().EGG_WORLD, PersistentDataType.STRING)) {
                        if (!(stack.getItemMeta().getPersistentDataContainer().get(Keys.getKeys().EGG_WORLD, PersistentDataType.STRING).equals(event.getTo().getWorld().getName()))) {
                            int amount = stack.getAmount();
                            stack.setAmount(0);
                            ItemStack corrupted_stack = Items.CORRUPTED_PAPER_EGG.buildStack(event.getPlayer());
                            corrupted_stack.setAmount(amount);
                            event.getPlayer().getInventory().setItem(i, corrupted_stack);
                        }
                    }
                }
            }
        }
    }
}
