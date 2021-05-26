package com.strangeone101.holoitems.abilities;

import com.strangeone101.holoitems.Items;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.ItemAbility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DemonSwordAbility extends ItemAbility {

    int ticks_left;
    int stacks;
    static int duration = 20;
    static double damage_multiplier = 0.05;
    static int max_stacks = 4;

    public DemonSwordAbility(Player player, ItemStack stack, Inventory inventory, int slot) {
        super(player, stack, inventory, slot);

        this.stacks = 1;
        this.ticks_left = duration * 20;
        start();
    }

    @Override
    public void tick() {
        ticks_left -= 1;
        if (ticks_left == 0) {
            System.out.println("Removed");
            remove();
        }
    }

    public void addStack() {
        if (stacks != max_stacks) {
            this.stacks += 1;
        }
        this.ticks_left = duration * 20;
    }

    public double getDamage(double damage) {
        return damage * (1 + (stacks * damage_multiplier));
    }

    @Override
    public long getCooldownLength() {
        return 0;
    }

    @Override
    public CustomItem getItem() {
        return Items.DEMON_SWORD;
    }
}
