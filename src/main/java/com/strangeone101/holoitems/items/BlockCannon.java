package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.HoloItemsPlugin;
import com.strangeone101.holoitems.abilities.BlockCannonAbility;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.function.Consumer;

public class BlockCannon extends CustomItem implements Interactable {

    private ItemStack ammo;
    private ItemStack blockFired;
    private Consumer<Location> onHit;

    public static Consumer<Location> MELON_HIT = loc -> {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            ItemStack melon = new ItemStack(Material.MELON_SLICE, rand.nextInt(3) + 1);
            loc.getWorld().dropItem(loc, melon, (item -> item.setVelocity(Vector.getRandom().multiply(0.1))));
        }
        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1F, 1.2F);
        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 2);
        loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 50,0.9, 0.9, 0.9, 2, Material.MELON.createBlockData());
    };

    public BlockCannon(String name, Material material, ItemStack ammo, ItemStack blockFired, Consumer<Location> onHit) {
        super(name, material);

        this.ammo = ammo;
        this.blockFired = blockFired;
        this.onHit = onHit;

        if (!blockFired.getType().isBlock()) {
            HoloItemsPlugin.INSTANCE.getLogger().warning("Item " + name + " tried to register with "
                    + blockFired.getType().toString() + " as the fired block, but it isn't a block!");

            this.blockFired = new ItemStack(Material.STONE);
        }
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        new BlockCannonAbility(player, itemStack, player.getInventory(), player.getInventory().getHeldItemSlot(), blockFired.getType(), ammo, onHit);
        return true;
    }
}
