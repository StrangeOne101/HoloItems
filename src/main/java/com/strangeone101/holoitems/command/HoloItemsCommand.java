package com.strangeone101.holoitems.command;

import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.CustomItemRegistry;
import com.strangeone101.holoitemsapi.Properties;
import com.strangeone101.holoitemsapi.itemevent.EventCache;
import com.strangeone101.holoitemsapi.itemevent.Position;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class HoloItemsCommand implements CommandExecutor {

    //This entire command class is from the test plugin. Please feel free to scrap it.

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "You must provide a player to give the item to!");
                return true;
            }
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("debug")) {
                if (args.length == 1) {
                    sender.sendMessage("/holoitems debug cache");
                    sender.sendMessage("/holoitems debug item");
                    sender.sendMessage("/holoitems debug registry");
                    sender.sendMessage("/holoitems debug stresscache [amount]");
                } else if (args[1].equalsIgnoreCase("cache")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Must not be used from console");
                        return true;
                    }
                    if (!EventCache.CACHED_POSITIONS_BY_SLOT.containsKey(sender)) {
                        sender.sendMessage("No cache found!");
                        return true;
                    }
                    sender.sendMessage("----- Cache -------");
                    for (Integer slot : EventCache.CACHED_POSITIONS_BY_SLOT.get(sender).keySet()) {
                        MutableTriple<CustomItem, ItemStack, Position> triple =
                                EventCache.CACHED_POSITIONS_BY_SLOT.get(sender).get(slot);
                        sender.sendMessage("[" + slot + "] " + triple.getLeft().getInternalName() + " = " + triple.getRight().toString());

                    }
                    sender.sendMessage("----- End cache -------");
                } else if (args[1].equalsIgnoreCase("registry")) {
                    for (String s : EventCache.getRegistryDebug()) {
                        sender.sendMessage(s);
                    }
                } else if (args[1].equalsIgnoreCase("item")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Must not be used from console");
                        return true;
                    }
                    ItemStack stack = ((Player)sender).getInventory().getItemInMainHand();
                    if (!CustomItemRegistry.isCustomItem(stack)) {
                        sender.sendMessage("Not a custom item!");
                        return true;
                    }
                    CustomItem item = CustomItemRegistry.getCustomItem(stack);
                    if (item == null) {
                        sender.sendMessage("Custom item is not currently registered!");
                        sender.sendMessage("The item is currently has ID [" +
                                Properties.ITEM_ID.get(stack.getItemMeta().getPersistentDataContainer()) + "]");
                        return true;
                    }

                    sender.sendMessage("Item name: [" + item.getInternalName() + "]");
                    sender.sendMessage("Texture ID: [" + item.getInternalID() + "]");
                    sender.sendMessage("Unstackable: [" + !item.isStackable() + "]");
                    sender.sendMessage("Durability: [" + item.getDurability(stack) + "]");
                    sender.sendMessage("Max durability: [" + item.getMaxDurability() + "]");
                    sender.sendMessage("Properties: [" + String.join(", ",
                            item.getProperties().stream().map(property -> property.getPropertyName() + "="
                                    + property.get(stack.getItemMeta().getPersistentDataContainer()))
                                    .collect(Collectors.joining()) + "]"));
                    sender.sendMessage("Lore: " + item.getLore() + "");

                } else if (args[1].equalsIgnoreCase("stresscache")) {
                    int amount = 100;
                    if (args.length > 2) {
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Not a number!");
                        }
                    }


                    long totalTime = 0;
                    Random rand = new Random();

                    for (int i = 0; i < amount; i++) {
                        Player player = (Player) Bukkit.getOnlinePlayers().toArray()[rand.nextInt(Bukkit.getOnlinePlayers().size())];
                        long currentTime = System.currentTimeMillis();
                        EventCache.fullCache(player);
                        totalTime += (System.currentTimeMillis() - currentTime);
                    }

                    sender.sendMessage("Cached " + amount + " times in " + (totalTime) + "ms");
                }
            } else {
                int maxPage = (int) Math.ceil(CustomItemRegistry.getCustomItems().size() / 54.0);

                try {
                    int pageNum = Integer.parseInt(args[0]);
                    if (pageNum <= maxPage && pageNum > 0) {
                        int skip = (pageNum - 1) * 54;

                        int amount = CustomItemRegistry.getCustomItems().size() - skip;
                        int rows = ((amount - 1) / 9) + 1;

                        if (rows > 6) rows = 6;

                        System.out.println(rows);

                        Inventory inv = Bukkit.createInventory(null, rows * 9, "HoloItems List");

                        HashMap<Integer, CustomItem> idMap = new HashMap<>();

                        for (CustomItem ci: CustomItemRegistry.getCustomItems().values()) {
                            idMap.put(ci.getInternalID(), ci);
                        }

                        ArrayList<Integer> idList = new ArrayList<>(idMap.keySet());

                        Collections.sort(idList);

                        for (int id: idList) {
                            if (skip > 0) {
                                skip--;
                                continue;
                            }
                            inv.addItem(idMap.get(id).buildStack((Player) sender));
                        }

                        ((Player)sender).openInventory(inv);
                    } else {
                        sender.sendMessage("Page doesn't exist");
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage("/holoitems <page_num>\nMax page is: " + maxPage);
                }
            }
            return true;
        }

        sender.sendMessage("/holoitems <page_num>");
        return true;
    }
}