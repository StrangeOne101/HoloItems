package com.strangeone101.holoitems.tables;

import com.strangeone101.holoitems.HoloItemsPlugin;
import com.strangeone101.holoitems.Items;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Cat implements LootTable {

    private NamespacedKey key = new NamespacedKey(HoloItemsPlugin.INSTANCE, "endermite_etherreal_essense");

    @Override
    public Collection<ItemStack> populateLoot(Random rand, LootContext context){
        List<ItemStack> drops = new ArrayList<>();

        int looting_level = context.getLootingModifier();

        if(Math.random() < (0.02 + (looting_level * 0.02))){
            drops.add(Items.CAT_PLUSHIE.buildStack(null));
        }

        return drops;
    }

    @Override
    public void fillInventory(Inventory inv, Random rand, LootContext context){

    }

    @Override
    public NamespacedKey getKey(){
        return key;
    }
}
