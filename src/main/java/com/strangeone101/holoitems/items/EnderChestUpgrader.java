package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderChestUpgrader extends CustomItem implements Interactable {

    int tier;
    String tier_name;

    public EnderChestUpgrader(String name, int tier, String tier_name) {
        super(name, Material.ENDER_EYE);
        this.setDisplayName("Ender Chest Upgrader")
                .addLore("Upgrade the amount of ender chest slots")
                .addLore("Right click to use")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Tier: " + tier);

        this.tier = tier;
        this.tier_name = tier_name;
    }

    @Override
    public boolean onInteract(Player player, CustomItem item, ItemStack stack) {
        int highest_tier;

        if (player.hasPermission("purpur.enderchest.rows.six")) {
            highest_tier = 6;
        } else if (player.hasPermission("purpur.enderchest.rows.five")) {
            highest_tier = 5;
        } else if (player.hasPermission("purpur.enderchest.rows.four")) {
            highest_tier = 4;
        } else {
            highest_tier = 3;
        }

        LuckPerms lp = LuckPermsProvider.get();

        if (highest_tier < tier) {
            User user = lp.getPlayerAdapter(Player.class).getUser(player);
            user.data().add(Node.builder("purpur.enderchest.rows." + this.tier_name).build());
            lp.getUserManager().saveUser(user);
            
            player.sendMessage("Ender chest upgraded!");
        } else if (tier == 3) {
            User user = lp.getPlayerAdapter(Player.class).getUser(player);
            user.data().add(Node.builder("purpur.enderchest.rows.three").build());
            lp.getUserManager().saveUser(user);
        } else {
            player.sendMessage("You already have this tier/higher tier ender chest!");
        }

        return true;
    }
}
