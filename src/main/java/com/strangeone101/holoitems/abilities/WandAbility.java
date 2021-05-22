package com.strangeone101.holoitems.abilities;

import com.strangeone101.holoitems.items.BuilderWand;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.ItemAbility;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WandAbility extends ItemAbility {

    private BuilderWand wand;

    public WandAbility(Player player, ItemStack stack, Inventory inventory, BuilderWand wand) {
        super(player, stack, inventory, player.getInventory().getHeldItemSlot());

        this.wand = wand;
    }

    @Override
    public void tick() {
        if (getPlayer() == null || getPlayer().getInventory().getHeldItemSlot() != getSlot()
                || getPlayer().getInventory().getItemInMainHand() != getStack()) {
            remove();
            return;
        }


    }

    @Override
    public long getCooldownLength() {
        return 0;
    }

    @Override
    public CustomItem getItem() {
        return wand;
    }

    public static List<Block> getPattern(Block block, BlockFace face, int length) {
        List<Block> sorted = new ArrayList<Block>();

        Map<Block, Integer> blocks = getNearby(block, face, new HashSet<>(), 0, length);

        sorted.addAll(blocks.keySet());
        sorted.sort((o1, o2) -> blocks.get(o2) - blocks.get(o1)); //Sort the blocks by distance from the center

        while (sorted.size() > length) { //This trims the list to the max it can place
            sorted.remove(-1); //Remove last entry
        }

        return sorted;
    }

    /**
     * Recursive method to get all blocks around the block that are the same
     * @param block The center block
     * @param face The block face to use as reference to what directions to check
     * @param checked A set of blocks already checked
     * @param distance The current distance from the center
     * @param maxLength The max length the chain can be
     * @return A map of all nearby blocks that match
     */
    public static Map<Block, Integer> getNearby(Block block, BlockFace face, Set<Block> checked, int distance, int maxLength) {
        Map<Block, Integer> found = new HashMap<Block, Integer>();

        for (int x = face.getModX() * -1; x < face.getModX(); x++) {
            for (int y = face.getModY() * -1; y < face.getModY(); y++) {
                for (int z = face.getModZ() * -1; z < face.getModZ(); z++) {
                    Block b = block.getRelative(x, y, z);
                    int localDistance = Math.abs(x) + Math.abs(y) + Math.abs(z);
                    if (!checked.contains(b) && (localDistance + distance < maxLength)) {
                        checked.add(b);
                        if (b.getType() == block.getType()) {
                            found.put(b, localDistance);
                        }
                    }
                }
            }
        }
        Map<Block, Integer> newFound = new HashMap<Block, Integer>();
        for (Block b : found.keySet()) {
            int d = found.get(b);
            newFound.putAll(getNearby(b, face, checked, distance + d, maxLength));
        }
        found.putAll(newFound);

        return found;
    }
}