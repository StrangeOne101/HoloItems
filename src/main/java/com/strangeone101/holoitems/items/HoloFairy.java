package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HoloFairy extends CustomItem implements Interactable {

    private String properSkullURL;
    private String holomember;

    public HoloFairy(String holomember, String color, String skinURL, String properHeadSkinURL) {
        super("fairy_" + holomember.toLowerCase()
                .replace("(", "")
                .replace(")", "")
                .replace(" ", ""), Material.PLAYER_HEAD);

        this.setDisplayName(color + holomember + " Fairy");
        this.addLore(ChatColor.GRAY + "" + ChatColor.ITALIC + "\"It's cute!\"");
        this.addLore("");
        this.addLore(ChatColor.YELLOW + "Right click " + ChatColor.WHITE + "to release!");

        this.setHeadSkin(skinURL);

        this.properSkullURL = properHeadSkinURL;
        this.holomember = holomember;
    }

    public String getProperSkullURL() {
        return properSkullURL;
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        //TODO summon Holomember
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 0.5F);

        return true;
    }

    public String getHoloMember() {
        return holomember;
    }
}
