package com.strangeone101.holoitems.items;

import com.google.common.collect.HashBiMap;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.strangeone101.holoitems.HoloItemsPlugin;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.BlockInteractable;
import com.strangeone101.holoitemsapi.util.ReflectionUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;

public class GradientDye extends CustomItem implements BlockInteractable {

    private static final NamespacedKey LEFT = new NamespacedKey(HoloItemsPlugin.INSTANCE, "left_color");
    private static final NamespacedKey RIGHT = new NamespacedKey(HoloItemsPlugin.INSTANCE, "right_color");

    private static HashBiMap<Integer, DyeColor> colors = HashBiMap.create();

    private static final String ARROW = "\u27A4";

    static {
        colors.put(0, DyeColor.BLACK);
        colors.put(1, DyeColor.RED);
        colors.put(2, DyeColor.ORANGE);
        colors.put(3, DyeColor.YELLOW);
        colors.put(4, DyeColor.LIME);
        colors.put(5, DyeColor.GREEN);
        colors.put(6, DyeColor.CYAN);
        colors.put(7, DyeColor.LIGHT_BLUE);
        colors.put(8, DyeColor.BLUE);
        colors.put(9, DyeColor.PURPLE);
        colors.put(10, DyeColor.MAGENTA);
        colors.put(11, DyeColor.PINK);
        colors.put(12, DyeColor.BROWN);
        colors.put(13, DyeColor.GRAY);
        colors.put(14, DyeColor.LIGHT_GRAY);
        colors.put(15, DyeColor.WHITE);

    }

    public GradientDye() {
        super("gradient_dye", Material.YELLOW_DYE);

        this.setInternalID(255);
        this.setDisplayName(ChatColor.GRAY + "Gradient Dye");
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);

        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(LEFT, PersistentDataType.BYTE, (byte)13);
        meta.getPersistentDataContainer().set(RIGHT, PersistentDataType.BYTE, (byte)15);
        meta.setDisplayName(IridiumColorAPI.color("Gradient Dye", Color.DARK_GRAY, Color.WHITE));
        stack.setItemMeta(meta);

        TranslatableComponent left = new TranslatableComponent("color.minecraft.gray");
        TranslatableComponent right = new TranslatableComponent("color.minecraft.white");
        TextComponent arrow = new TextComponent(" " + ARROW + " ");
        arrow.setColor(ChatColor.GRAY);
        left.addExtra(arrow);
        left.addExtra(right);
        TextComponent blank = new TextComponent("");
        TextComponent msg = new TextComponent(ChatColor.GRAY + "Right click a sign to dye!");

        ReflectionUtils.setTrueLore(stack, left, blank, msg);

        return stack;
    }

    @Override
    public ItemStack updateStack(ItemStack stack, Player player) {
        stack = super.updateStack(stack, player);

        ItemMeta meta = stack.getItemMeta();

        int lcolor = meta.getPersistentDataContainer().get(LEFT, PersistentDataType.BYTE);
        int rcolor = meta.getPersistentDataContainer().get(RIGHT, PersistentDataType.BYTE);

        meta.setCustomModelData(lcolor << 4 | rcolor);

        DyeColor dcl = colors.get(lcolor);
        DyeColor dcr = colors.get(rcolor);

        meta.setDisplayName(IridiumColorAPI.color("Gradient Dye", new Color(dcl.getColor().asRGB()),
                new Color(dcr.getColor().asRGB())));
        stack.setItemMeta(meta);

        TranslatableComponent left = new TranslatableComponent("color.minecraft." + dcl.name().toLowerCase());
        TranslatableComponent right = new TranslatableComponent("color.minecraft." + dcr.name().toLowerCase());
        TextComponent arrow = new TextComponent(" " + ARROW + " ");
        arrow.setColor(ChatColor.GRAY);
        left.addExtra(arrow);
        left.addExtra(right);
        TextComponent blank = new TextComponent("");
        TextComponent msg = new TextComponent(ChatColor.GRAY + "Right click a sign to dye!");

        ReflectionUtils.setTrueLore(stack, left, blank, msg);

        return stack;
    }

    @Override
    public boolean onInteract(Player player, Block block, CustomItem customItem, ItemStack itemStack, boolean b) {
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign) block.getState();
            sign.setColor(DyeColor.BLACK);

            ItemMeta meta = itemStack.getItemMeta();

            int lcolor = meta.getPersistentDataContainer().get(LEFT, PersistentDataType.BYTE);
            int rcolor = meta.getPersistentDataContainer().get(RIGHT, PersistentDataType.BYTE);

            DyeColor dcl = colors.get(lcolor);
            DyeColor dcr = colors.get(rcolor);

            Color cl = new Color(dcl.getColor().asRGB());
            Color cr = new Color(dcr.getColor().asRGB());


            for (int line = 0; line < 4; line++) {
                String s = sign.getLine(line);

                s = IridiumColorAPI.color(s, cl, cr);
                sign.setLine(line, s);
            }
            itemStack.setAmount(itemStack.getAmount() - 1);
            sign.update();
            return true;
        }

        return false;

    }

    public static void setColor(ItemStack stack, Material dye, boolean left) {
        DyeColor color = DyeColor.WHITE;
        switch (dye) {
            case RED_DYE -> color = DyeColor.RED;
            case YELLOW_DYE -> color = DyeColor.YELLOW;
            case ORANGE_DYE -> color = DyeColor.ORANGE;
            case LIME_DYE -> color = DyeColor.LIME;
            case GREEN_DYE -> color = DyeColor.GREEN;
            case CYAN_DYE -> color = DyeColor.CYAN;
            case LIGHT_BLUE_DYE -> color = DyeColor.LIGHT_BLUE;
            case BLUE_DYE -> color = DyeColor.BLUE;
            case PURPLE_DYE -> color = DyeColor.PURPLE;
            case MAGENTA_DYE -> color = DyeColor.MAGENTA;
            case PINK_DYE -> color = DyeColor.PINK;
            case BROWN_DYE -> color = DyeColor.BROWN;
            case GRAY_DYE -> color = DyeColor.GRAY;
            case LIGHT_GRAY_DYE -> color = DyeColor.LIGHT_GRAY;
            case BLACK_DYE -> color = DyeColor.BLACK;
            default -> color = DyeColor.WHITE;
        };

        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(left ? LEFT : RIGHT, PersistentDataType.BYTE, colors.inverse().get(color).byteValue());
        stack.setItemMeta(meta);
    }
}
