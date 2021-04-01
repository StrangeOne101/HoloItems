package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.EventContext;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.interfaces.ItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class DeathCrystal extends CustomItem implements Interactable, Listener {

    public ItemStack itemStack;

    public DeathCrystal() {
        super("death_crystal", Material.NETHER_STAR);
        this.setDisplayName("Death Crystal")
                .addLore("Teleport you to last death location")
                .addLore("Can only be used once per death")
                .addLore("Right click to use");
        //Probably can add Lore to say if point has been set or not
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);
        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY, new int[] {0,0,0});
        meta.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING, "placeholder");

        stack.setItemMeta(meta);
        itemStack = stack;
        return stack;
    }

    @Override
    public boolean onInteract(Player player, CustomItem item, ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta.getPersistentDataContainer().get(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER) == 1) {
            int[] loc_array_int = meta.getPersistentDataContainer().get(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY);
            Location tp_loc = new Location(Bukkit.getWorld(meta.getPersistentDataContainer().get(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING)), loc_array_int[0], loc_array_int[1], loc_array_int[2]);
            player.teleport(tp_loc);
        } else {
            player.sendMessage("You haven't died once!");
        }
        return true;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        System.out.println("Test1");
        if (event.getEntity().getInventory().contains(itemStack)) {
            System.out.println("Test2");
            Location loc = event.getEntity().getLocation();
            ItemStack stack = itemStack;
            ItemMeta meta = stack.getItemMeta();
            meta.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY, new int[] {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()});
            meta.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 1);
            meta.getPersistentDataContainer().set(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING, loc.getWorld().getName());
        }
    }
}
