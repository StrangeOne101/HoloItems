package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Edible;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BasicFood extends CustomItem implements Edible {

    private int hunger;
    private float saturation;

    public BasicFood(String string, Material material, int hunger, float saturation) {
        super(string, material);
        this.addLore(ChatColor.DARK_GRAY + "Food Item");
        this.addLore("");
        if (hunger >= 16) {
            double amount = hunger / 2;
            this.addLore(ChatColor.GRAY + "Restores " + ChatColor.GREEN + (hunger / 2) + "x " + ChatColor.BOLD + "☕");
        } else {
            int green = hunger / 2;
            int yellow = hunger % 2;
            String all = ChatColor.GREEN + "" + ChatColor.BOLD;

            for (int i = 0; i < green; i++) {
                all = all + "☕";
            }
            for (int i = 0; i < yellow; i++) {
                all = all + ChatColor.YELLOW + ChatColor.BOLD + "☕";
            }

            this.addLore(ChatColor.GRAY + "Restores " + all);
        }

        this.hunger = hunger;
        this.saturation = saturation;
    }

    @Override
    public int getHungerAmount() {
        return hunger;
    }

    @Override
    public float getSaturationAmount() {
        return saturation;
    }
}
