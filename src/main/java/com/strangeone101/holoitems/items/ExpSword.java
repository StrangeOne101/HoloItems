package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Swingable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ExpSword extends CustomItem implements Swingable {
    public ExpSword() {
        super("exp_sword", Material.DIAMOND_SWORD, "EXP Sword");
        this.addLore("Increase damage according to the player level.")
                .addLore("Capped at 20.")
                .addLore("Consume 10% of current level for each use.");
    }

    @Override
    public int getMaxDurability() {
        return 350;
    }

    @Override
    public void swing(Player player, CustomItem customItem, ItemStack itemStack) {

    }

    @Override
    public double hit(Entity entity, Player player, CustomItem customItem, ItemStack itemStack, double damage) {
        int level = player.getLevel();
        if (level > 100) level = 100;
        damage = 4 + (level * 0.1);
        damageItem(itemStack, 1, player);
        addPlayerXP(player, (int)(-0.1 * (getExperienceForLevel(player.getLevel()) - getExperienceForLevel(player.getLevel() - 1))));
        return damage;
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
