package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PhantomTalisman extends CustomItem {
    public PhantomTalisman() {
        super("phantom_talisman", Material.PAPER, "Phantom Talisman");
        this.addLore("Shoo! No birds around here!")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Phantom cannot spawn near you");
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);
        stack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(meta);
        return stack;
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.HOTBAR)
    public void onEntitySpawn(EventContext context, CreatureSpawnEvent event) {
        if (context.getPlayer().getLocation().distance(event.getLocation()) < 200) {
            if (event.getEntity() instanceof Phantom) {
                System.out.println("True 1");
                event.setCancelled(true);
            }
        }
    }

    @ItemEvent(target = Target.WORLD, active = ActiveConditions.HOTBAR)
    public void onEntityTarget(EventContext context, EntityTargetEvent event) {
        if (context.getPlayer() == event.getTarget()) {
            if (event.getEntity() instanceof Phantom) {
                event.setCancelled(true);
            }
        }
    }
}
