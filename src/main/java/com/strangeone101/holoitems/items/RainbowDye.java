package com.strangeone101.holoitems.items;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.BlockInteractable;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RainbowDye extends CustomItem implements BlockInteractable {
    public RainbowDye() {
        super("rainbow_dye", Material.RED_DYE);

        this.setDisplayName(IridiumColorAPI.rainbow(ChatColor.BOLD + "RAINBOW DYE", 1))
                .addLore(ChatColor.DARK_GRAY + "Crafting Ingredient")
                .setInternalID(2250);
    }

    @Override
    public boolean onInteract(Player player, Block block, CustomItem customItem, ItemStack itemStack, boolean b) {
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign) block.getState();
            sign.setColor(DyeColor.BLACK);

            for (int line = 0; line < 4; line++) {
                String s = sign.getLine(line);

                s = IridiumColorAPI.rainbow(s, 1);
                sign.setLine(line, s);
            }
            itemStack.setAmount(itemStack.getAmount() - 1);
            sign.update();
        }

        return true;
    }
}
