package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Random;

public class DeathCrystal extends CustomItem implements Interactable, Listener {

    int tier;
    static Material[] passable_block = new Material[] {Material.AIR, Material.WATER};

    public DeathCrystal(String name, int tier) {
        super(name, Material.NETHER_STAR);
        this.setDisplayName("Death Crystal")
                .addLore("Teleport you to last death location")
                .addLore("Can only be used once per death")
                .addLore("Right click to use")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Tier: " + tier);
        this.tier = tier;
    }

    @Override
    public boolean onInteract(Player player, CustomItem item, ItemStack stack) {
        if (player.getPersistentDataContainer().get(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER) == 1) {
            Random rand = new Random();
            int[] loc_array = player.getPersistentDataContainer().get(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY);
            World world = Bukkit.getWorld(player.getPersistentDataContainer().get(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING));

            if (this.tier > 2) {
                Location tp_loc = new Location(world, loc_array[0], loc_array[1], loc_array[2]);
                player.teleport(tp_loc);

                player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 0);
                stack.setAmount(stack.getAmount() - 1);
                return true;
            }

            for (int i = 0;i < 50; i++) {
                int x_addition;
                int z_addition;

                x_addition = rand.nextInt((((2 - this.tier) * 50) + 30) * 2) - (((2 - this.tier) * 50) + 30);
                if (x_addition < 0) {
                    x_addition -= 20;
                } else {
                    x_addition += 20;
                }
                z_addition = rand.nextInt((((2 - this.tier) * 50) + 30) * 2) - (((2 - this.tier) * 50) + 30);
                if (z_addition < 0) {
                    z_addition -= 20;
                } else {
                    z_addition += 20;
                }

                Location tp_loc = new Location(world, loc_array[0] + x_addition, loc_array[1], loc_array[2] + z_addition);
                Location head_loc = new Location(world, loc_array[0] + x_addition, loc_array[1] + 1, loc_array[2] + z_addition);

                if ((Arrays.asList(passable_block).contains(tp_loc.getBlock().getType())) && ((Arrays.asList(passable_block).contains(head_loc.getBlock().getType())))) {
                    player.teleport(tp_loc);

                    player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 0);
                    stack.setAmount(stack.getAmount() - 1);
                    return true;
                }
            }

            for (int i = 0;i < 40; i++) {
                int x_addition;
                int y_addition;
                int z_addition;

                x_addition = rand.nextInt((((2 - this.tier) * 50) + 30) * 2) - (((2 - this.tier) * 50) + 30);
                if (x_addition < 0) {
                    x_addition -= 20;
                } else {
                    x_addition += 20;
                }
                y_addition = rand.nextInt(7) - 4;
                if (y_addition >= 0){
                    y_addition += 1;
                }
                if ((loc_array[1] + y_addition) < 2) {
                    continue;
                }
                z_addition = rand.nextInt((((2 - this.tier) * 50) + 30) * 2) - (((2 - this.tier) * 50) + 30);
                if (z_addition < 0) {
                    z_addition -= 20;
                } else {
                    z_addition += 20;
                }

                Location tp_loc = new Location(world, loc_array[0] + x_addition, loc_array[1] + y_addition, loc_array[2] + z_addition);
                Location head_loc = new Location(world, loc_array[0] + x_addition, loc_array[1] + y_addition + 1, loc_array[2] + z_addition);

                if ((Arrays.asList(passable_block).contains(tp_loc.getBlock().getType())) && ((Arrays.asList(passable_block).contains(head_loc.getBlock().getType())))) {
                    player.teleport(tp_loc);

                    player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 0);
                    stack.setAmount(stack.getAmount() - 1);
                    return true;
                }
            }

            int y_pos = world.getHighestBlockAt(loc_array[0], loc_array[2]).getY();
            int x_addition;
            int z_addition;

            x_addition = rand.nextInt((((2 - this.tier) * 50) + 30) * 2) - (((2 - this.tier) * 50) + 30);
            if (x_addition < 0) {
                x_addition -= 20;
            } else {
                x_addition += 20;
            }
            z_addition = rand.nextInt((((2 - this.tier) * 50) + 30) * 2) - (((2 - this.tier) * 50) + 30);
            if (z_addition < 0) {
                z_addition -= 20;
            } else {
                z_addition += 20;
            }

            player.teleport(new Location(world, loc_array[0] + x_addition, y_pos, loc_array[2] + z_addition));
            player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 0);
            stack.setAmount(stack.getAmount() - 1);
        } else {
            player.sendMessage("You haven't died once!");
        }
        return true;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Location loc = event.getEntity().getLocation();
        Player player = event.getEntity();
        player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY, new int[]{loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()});
        player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 1);
        player.getPersistentDataContainer().set(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING, loc.getWorld().getName());
    }

    @ItemEvent(target = Target.ALL,active = ActiveConditions.NONE)
    public void onPlayerJoin(EventContext context, PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()) {
            player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY, new int[] {0,0,0});
            player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC_SET, PersistentDataType.INTEGER, 0);
            player.getPersistentDataContainer().set(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING, "");
        }
    }
}
