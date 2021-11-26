package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EXPPotion extends CustomItem implements Interactable {
    private static double effectiveness = 0.75;

    public EXPPotion() {
        super("exp_potion", Material.POTION, "EXP Potion");
        this.addLore("Sneak + Right Click to store exp")
                .addLore("Right Click to get the stored exp");
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);
        ItemMeta meta = stack.getItemMeta();

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(Keys.getKeys().EXP_AMOUNT, PersistentDataType.INTEGER, 0);

        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public ItemStack updateStack(ItemStack stack, Player player) {
        super.updateStack(stack, player);
        ItemMeta meta = stack.getItemMeta();

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        if (player.isSneaking()) {
            ItemMeta meta = itemStack.getItemMeta();
            int level = player.getLevel();
            if (level > 0) {
                int exp = getPlayerXP(player) - getExperienceForLevel(player.getLevel());
                if (exp == 0) {
                    exp = getExperienceForLevel(player.getLevel()) - getExperienceForLevel(player.getLevel() - 1);
                }
                addPlayerXP(player, -exp);
                int exp_stored = meta.getPersistentDataContainer().get(Keys.getKeys().EXP_AMOUNT, PersistentDataType.INTEGER);
                meta.getPersistentDataContainer().set(Keys.getKeys().EXP_AMOUNT, PersistentDataType.INTEGER, exp_stored + exp);
                itemStack.setItemMeta(meta);
            }
        } else {
            ItemMeta meta = itemStack.getItemMeta();
            int exp = meta.getPersistentDataContainer().get(Keys.getKeys().EXP_AMOUNT, PersistentDataType.INTEGER);
            if (exp != 0) {
                addPlayerXP(player, exp);
                meta.getPersistentDataContainer().set(Keys.getKeys().EXP_AMOUNT, PersistentDataType.INTEGER, 0);
                itemStack.setItemMeta(meta);
            }
        }
        return true;
    }

    public static int getPlayerXP(Player player) {
        return (int)(getExperienceForLevel(player.getLevel()) + (player.getExp() * player.getExpToLevel()));
    }

    public static void addPlayerXP(Player player, int amount) {
        int experience = getPlayerXP(player) + amount;
        player.setTotalExperience(experience);
        player.setLevel(getLevelForExperience(experience));
        int expForLevel = getExperienceForLevel(player.getLevel());
        player.setExp((experience - expForLevel) / (float)player.getExpToLevel());
    }

    public static int xpBarCap(int level) {
        if (level >= 30)
            return 112 + (level - 30) * 9;

        if (level >= 15)
            return 37 + (level - 15) * 5;

        return 7 + level * 2;
    }

    private static int sum(int n, int a0, int d) {
        return n * (2 * a0 + (n - 1) * d) / 2;
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level <= 15) return sum(level, 7, 2);
        if (level <= 30) return 315 + sum(level - 15, 37, 5);
        return 1395 + sum(level - 30, 112, 9);
    }

    public static int getLevelForExperience(int targetXp) {
        int level = 0;
        while (true) {
            final int xpToNextLevel = xpBarCap(level);
            if (targetXp < xpToNextLevel) return level;
            level++;
            targetXp -= xpToNextLevel;
        }
    }
}
