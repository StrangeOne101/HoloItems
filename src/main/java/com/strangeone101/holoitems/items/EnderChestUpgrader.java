package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderChestUpgrader extends CustomItem implements Interactable {

    private int tier;
    private String tier_name;

    public EnderChestUpgrader(String name, int tier, String tier_name) {
        super(name, Material.ENDER_EYE);
        this.setDisplayName(ChatColor.LIGHT_PURPLE + "Ender Chest Upgrader" + ChatColor.YELLOW + "(Tier " + (tier) + ")")
                .addLore(ChatColor.GRAY + "Upgrades the amount of rows you")
                .addLore(ChatColor.GRAY + "have in your ender chest. This")
                .addLore(ChatColor.GRAY + "tier raises it to " + ChatColor.YELLOW + (tier + 3) + ChatColor.GRAY + " rows")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Right click to use!");

        this.tier = tier;
        this.tier_name = tier_name;
    }

    @Override
    public boolean onInteract(Player player, CustomItem item, ItemStack stack) {
        LuckPerms lp = LuckPermsProvider.get();

        if (!player.hasPermission("purpur.enderchest.rows." + this.tier_name)) {
            User user = lp.getPlayerAdapter(Player.class).getUser(player);
            user.data().add(Node.builder("purpur.enderchest.rows." + this.tier_name).build());
            lp.getUserManager().saveUser(user);
            
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Ender chest upgraded!");
            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
        } else if (tier == 3) {
            User user = lp.getPlayerAdapter(Player.class).getUser(player);
            user.data().remove(Node.builder("purpur.enderchest.rows.six").build());
            user.data().remove(Node.builder("purpur.enderchest.rows.five").build());
            user.data().remove(Node.builder("purpur.enderchest.rows.four").build());
            lp.getUserManager().saveUser(user);
        } else {
            player.sendMessage(ChatColor.RED + "You have already used this tier of ender chest upgrade!");
        }

        return true;
    }
}
