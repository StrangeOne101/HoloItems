package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarcriminalExplosive extends CustomItem implements Interactable {

    public WarcriminalExplosive() {
        super("peko_explosive", Material.CARROT, ChatColor.RED + "Peko of Mass Destruction");
        this.addLore(ChatColor.GRAY + "" + ChatColor.ITALIC + "\"Yabai-peko...\"");
        this.addLore("");
        this.addLore(ChatColor.GOLD + "" + ChatColor.BOLD + "WAR CRIMINAL CHAOS" + ChatColor.YELLOW + " (Right Click");
        this.addLore("Summons 5 " + ChatColor.RED + "explosive" + ChatColor.WHITE + " bunnies around you");
        this.addLore("that detonate after 10 seconds.");
        this.addLore("");
        this.addLore(ChatColor.DARK_AQUA + "ONE TIME USE!");

        this.setStackable(false);
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack buildStack(Player player) {
        return super.buildStack(player);
    }
}
