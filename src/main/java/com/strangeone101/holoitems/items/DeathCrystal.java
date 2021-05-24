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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class DeathCrystal extends CustomItem implements Interactable, Listener {

    int tier;

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
        if (player.getPersistentDataContainer().has(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY)) {
            int[] loc_array = player.getPersistentDataContainer().get(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY);
            World world = Bukkit.getWorld(player.getPersistentDataContainer().get(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING));

            if (world == null) {
                player.sendMessage("World doesn't exist anymore.");

                return true;
            }

            if (this.tier > 2) {
                Location tp_loc = new Location(world, loc_array[0], loc_array[1], loc_array[2]);
                player.teleport(tp_loc);

                player.getPersistentDataContainer().remove(Keys.getKeys().DEATH_LOC);
                stack.setAmount(stack.getAmount() - 1);
                return true;
            }

            for (int i = 0;i < 50; i++) {
                int x_addition;
                int z_addition;

                x_addition = this.calc_random(this.tier * 50, true, 20);
                z_addition = this.calc_random(this.tier * 50, true, 20);

                Location tp_loc = new Location(world, loc_array[0] + x_addition, loc_array[1], loc_array[2] + z_addition);
                Location head_loc = new Location(world, loc_array[0] + x_addition, loc_array[1] + 1, loc_array[2] + z_addition);

                Material tp_mat = tp_loc.getBlock().getType();
                Material head_mat = head_loc.getBlock().getType();
                if (((!tp_mat.isSolid()) && (tp_mat != Material.LAVA)) && ((!head_mat.isSolid()) && ((head_mat != Material.LAVA) || (head_mat != Material.WATER)))) {
                    player.teleport(tp_loc);

                    player.getPersistentDataContainer().remove(Keys.getKeys().DEATH_LOC);
                    stack.setAmount(stack.getAmount() - 1);
                    return true;
                }
            }

            for (int i = 0;i < 40; i++) {
                int x_addition;
                int y_addition;
                int z_addition;

                x_addition = this.calc_random(this.tier * 50, true, 20);
                y_addition = this.calc_random(5, true, 1);
                if ((loc_array[1] + y_addition) < 2) {
                    continue;
                }
                z_addition = this.calc_random(this.tier * 50, true, 20);

                Location tp_loc = new Location(world, loc_array[0] + x_addition, loc_array[1] + y_addition, loc_array[2] + z_addition);
                Location head_loc = new Location(world, loc_array[0] + x_addition, loc_array[1] + y_addition + 1, loc_array[2] + z_addition);

                Material tp_mat = tp_loc.getBlock().getType();
                Material head_mat = head_loc.getBlock().getType();
                if (((!tp_mat.isSolid()) && (tp_mat != Material.LAVA)) && ((!head_mat.isSolid()) && ((head_mat != Material.LAVA) || (head_mat != Material.WATER)))) {
                    player.teleport(tp_loc);

                    player.getPersistentDataContainer().remove(Keys.getKeys().DEATH_LOC);
                    stack.setAmount(stack.getAmount() - 1);
                    return true;
                }
            }

            int x_addition;
            int z_addition;

            x_addition = this.calc_random(this.tier * 50, true, 20);
            z_addition = this.calc_random(this.tier * 50, true, 20);
            int y_pos = world.getHighestBlockAt(loc_array[0] + x_addition, loc_array[2] + z_addition).getY();

            player.teleport(new Location(world, loc_array[0] + x_addition, y_pos + 1, loc_array[2] + z_addition));
            player.getPersistentDataContainer().remove(Keys.getKeys().DEATH_LOC);
            stack.setAmount(stack.getAmount() - 1);
        } else {
            player.sendMessage("You haven't died once!");
        }
        return true;
    }

    @ItemEvent(target = Target.ALL, active = ActiveConditions.NONE)
    public void onPlayerDeath(EventContext context, PlayerDeathEvent event) {
        Location loc = event.getEntity().getLocation();
        Player player = event.getEntity();
        player.getPersistentDataContainer().set(Keys.getKeys().DEATH_LOC, PersistentDataType.INTEGER_ARRAY, new int[]{loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()});
        player.getPersistentDataContainer().set(Keys.getKeys().DEATH_WORLD, PersistentDataType.STRING, loc.getWorld().getName());
    }

    int calc_random(int max) {
        return this.calc_random(max, false);
    }

    int calc_random(int max, boolean negative) {
        return this.calc_random(max, negative, 0);
    }

    int calc_random(int max, boolean negative, int min) {
        Random rand = new Random();
        int original_max = max;

        max += 1;
        max -= min;
        if (negative) {
            max = max * 2;
        }
        int ret = rand.nextInt(max);
        if (negative) {
            ret -= original_max;
        }
        if (ret >= 0) {
            ret += min;
        } else {
            ret -= min;
        }

        return ret;
    }
}
