package com.strangeone101.holoitems.items;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.EventContext;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.interfaces.ItemEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class CatPlushie extends CustomItem implements Interactable {

    public CatPlushie() {
        super("cat_plushie", Material.RABBIT_HIDE);
        this.setDisplayName(ChatColor.BLUE + "Cat Plushie")
                .addLore(ChatColor.GREEN + "Nya nya nya!")
                .addLore(ChatColor.GREEN + "Become a cat nya!")
                .addLore(ChatColor.GREEN + "A rare gift from cat nya! Don't hurt them!");
    }

    @Override
    public boolean onInteract(Player player, CustomItem item, ItemStack stack){
        player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, SoundCategory.PLAYERS,2.0F,1.0F);
        return false;
    }

    @ItemEvent
    public void onChat(EventContext context, AsyncPlayerChatEvent event){
        System.out.println("true");
        if(context.getPosition() == EventContext.Position.HELD || context.getPosition() == EventContext.Position.OFFHAND) {
            event.setMessage(event.getMessage() + " nya");
        }
    }
}
