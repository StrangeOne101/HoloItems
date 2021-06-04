package com.strangeone101.holoitems.abilities;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.CustomItemRegistry;
import com.strangeone101.holoitemsapi.ItemAbility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Random;

public class KnockbackAbility extends ItemAbility {

    private long cooldown;

    public KnockbackAbility(Player player, ItemStack stack, Inventory inventory, int slot,
                            double knockback, double knockbackY, long cooldown) {
        super(player, stack, inventory, slot);

        this.cooldown = cooldown;

        if (ItemAbility.isAbilityActive(player, this.getClass()) || isOnCooldown()) return; //Ability already active or on cooldown

        Location target = getPlayer().getEyeLocation().add(getPlayer().getEyeLocation().getDirection().clone().multiply(1.5));
        Collection<Entity> mobs = player.getWorld().getNearbyEntities(target, 1, 1, 1);

        Random random = new Random();

        boolean success = false;
        for (Entity mob : mobs) {
            if (mob instanceof Creature) {
                EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(player, mob, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 0D);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    Vector vec = player.getLocation().getDirection();
                    vec.add(new Vector(random.nextDouble() / 10 - 0.05, 0, random.nextDouble() / 10 - 0.05)); //Add a spread of 0.2 blocks
                    vec.setY(0);
                    vec.normalize();
                    vec.multiply(knockback);
                    vec.setY(knockbackY);

                    mob.setVelocity(mob.getVelocity().add(vec));
                    success = true;
                }
            }
        }

        if (success) {
            this.applyCooldown();
            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, target, 15, 0.6, 0.6, 0.6, 0);
            player.getWorld().playSound(target, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
            CustomItem item = CustomItemRegistry.getCustomItem(stack);
            if (item != null) {
                item.damageItem(stack, 10, player);
            }
        }

    }

    @Override
    public void tick() {
        //Do nothing
    }

    @Override
    public long getCooldownLength() {
        return cooldown;
    }

    @Override
    public CustomItem getItem() {
        return null;
    }
}
