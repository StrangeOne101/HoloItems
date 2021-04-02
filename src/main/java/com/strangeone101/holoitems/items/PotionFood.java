package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.util.TranslationUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.awt.Color;

public class PotionFood extends BasicFood {

    private int maxPotionLength;
    private PotionEffect potion;

    public PotionFood(String string, Material material, int hunger, float saturation, PotionEffect potion, int max) {
        super(string, material, hunger, saturation);
        this.potion = potion;
        this.maxPotionLength = max;

        //HoloItemsPlugin.INSTANCE.getLogger().info(potion.toString());
        Color color = new Color(potion.getType().getColor().asRGB());
        int minutes = (potion.getDuration() / 20) / 60;
        int seconds = (potion.getDuration() - (minutes * 20 * 60)) / 20;
        String secondsString = seconds < 10 ? "0" + seconds : seconds + "";
        TextComponent component = new TextComponent();
        for (BaseComponent comp : TextComponent.fromLegacyText(ChatColor.GRAY + "Grants ")) {
            component.addExtra(comp);
        }
        component.setColor(ChatColor.of(color));
        component.addExtra(TranslationUtils.getPotionEffect(potion.getType()));
        component.addExtra(new TextComponent(" (" + minutes + ":" + secondsString + ")"));
        this.addLore(component);
    }

    public void addPotion(Player player) {
        if (player.hasPotionEffect(potion.getType())) {
            int current = player.getPotionEffect(potion.getType()).getDuration();

            current += potion.getDuration();
            current = Math.min(current, maxPotionLength);

            PotionEffect newPotion = new PotionEffect(potion.getType(), current,
                    potion.getAmplifier(), potion.isAmbient(), potion.hasParticles(), potion.hasIcon());
            player.addPotionEffect(newPotion);
            return;
        }

        PotionEffect newPotion = new PotionEffect(potion.getType(), potion.getDuration(),
                potion.getAmplifier(), potion.isAmbient(), potion.hasParticles(), potion.hasIcon());
        player.addPotionEffect(newPotion);
    }

    @Override
    public void onEat(Player player, CustomItem item, ItemStack stack) {
        addPotion(player);
    }
}
