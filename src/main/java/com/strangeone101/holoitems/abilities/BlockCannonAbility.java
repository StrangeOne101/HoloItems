package com.strangeone101.holoitems.abilities;

import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.ItemAbility;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BlockCannonAbility extends ItemAbility {

    private FallingBlock fallingBlock;
    private Location lastLoc;
    private Location origin;
    private Vector lastVec;

    private BiConsumer<Player, Location> onHit;

    private long length = 2_000;
    private long distance = 25;

    public BlockCannonAbility(Player player, ItemStack stack, Inventory inventory, int slot, Material block, ItemStack ammo, BiConsumer<Player, Location> onHit) {
        super(player, stack, inventory, slot);

        this.onHit = onHit;

        if (isOnCooldown()) return; //Ability already active or on cooldown

        int index = -1;
        for (int s = 0; s < 36; s++) {
            if (player.getInventory().getItem(s) != null && player.getInventory().getItem(s).isSimilar(ammo)) {
                index = s;
                break;
            }
        }
        if (index != -1 || player.getGameMode() == GameMode.CREATIVE) {

            if (player.getGameMode() != GameMode.CREATIVE) {
                ItemStack currAmmo = player.getInventory().getItem(index);
                currAmmo.setAmount(currAmmo.getAmount() - 1);
            }

            player.getWorld().playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 2F);

            Location loc = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(0.5));
            fallingBlock = player.getWorld().spawnFallingBlock(loc, block.createBlockData());
            fallingBlock.setDropItem(false);
            fallingBlock.setHurtEntities(false);
            fallingBlock.setVelocity(player.getEyeLocation().getDirection().multiply(3.0F));
            fallingBlock.getPersistentDataContainer().set(Keys.getKeys().TEMP_ENTITY, PersistentDataType.INTEGER, 1);

            lastVec = fallingBlock.getVelocity();
            lastLoc = fallingBlock.getLocation();
            origin = fallingBlock.getLocation();

            start();
        }


    }

    @Override
    public void tick() {
        if (fallingBlock == null || fallingBlock.isDead()) {
            explode();
            return;
        }

        Vector vec = fallingBlock.getVelocity();

        if ((isZero(vec.getX()) && !isZero(lastVec.getX())) ||
                (isZero(vec.getZ()) && !isZero(lastVec.getZ())) ||
                (isZero(vec.getY()) && !isZero(lastVec.getY()))) {
            explode();
            return;
        }

        if (getStartTime() + length > System.currentTimeMillis() || fallingBlock.getLocation().distanceSquared(origin) > distance * distance) {
            explode();
            return;
        }

        lastVec = vec.clone();
        lastLoc = fallingBlock.getLocation().clone();

    }

    public void explode() {
        if (fallingBlock != null) {
            lastLoc = fallingBlock.getLocation();
            fallingBlock.remove();
        }

        onHit.accept(getPlayer(), lastLoc);
        remove();
    }

    @Override
    public long getCooldownLength() {
        return 1000 * 3;
    }

    @Override
    public CustomItem getItem() {
        return null;
    }

    public static boolean isZero(double d) {
        return d < 0.00001 && d > -0.999999;
    }
}
