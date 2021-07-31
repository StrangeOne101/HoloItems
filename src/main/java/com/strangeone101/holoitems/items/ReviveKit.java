package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.HoloItemsPlugin;
import com.strangeone101.holoitems.Items;
import com.strangeone101.holoitems.Keys;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.BlockInteractable;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.itemevent.ActiveConditions;
import com.strangeone101.holoitemsapi.itemevent.EventContext;
import com.strangeone101.holoitemsapi.itemevent.ItemEvent;
import com.strangeone101.holoitemsapi.itemevent.Target;
import com.strangeone101.holoitemsapi.util.EntityUtils;
import com.strangeone101.holoitemsapi.util.MapTagType;
import com.strangeone101.holoitemsapi.util.ReflectionUtils;
import com.strangeone101.holoitemsapi.util.TranslationUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviveKit extends CustomItem implements BlockInteractable, Interactable {

    public static NamespacedKey TYPE = new NamespacedKey(HoloItemsPlugin.INSTANCE, "type");
    public static NamespacedKey NAME = new NamespacedKey(HoloItemsPlugin.INSTANCE, "name");
    public static NamespacedKey PROGRESS = new NamespacedKey(HoloItemsPlugin.INSTANCE, "progress");
    public static NamespacedKey STAGE = new NamespacedKey(HoloItemsPlugin.INSTANCE, "stage");

    public static Map<EntityType, Pair<Integer, Double>> MOB_INFO = new HashMap<>(); //Soul amount, exp scale
    public static int EXP_NEEDED = 2000;

    static {
        MOB_INFO.put(EntityType.PIG, new ImmutablePair<>(25, 0.8));
        MOB_INFO.put(EntityType.COW, new ImmutablePair<>(25, 0.8));
        MOB_INFO.put(EntityType.MUSHROOM_COW, new ImmutablePair<>(15, 1.0));
        MOB_INFO.put(EntityType.SHEEP, new ImmutablePair<>(25, 0.8));
        MOB_INFO.put(EntityType.CHICKEN, new ImmutablePair<>(25, 0.6));

        MOB_INFO.put(EntityType.PARROT, new ImmutablePair<>(5, 1.0));
        MOB_INFO.put(EntityType.BEE, new ImmutablePair<>(8, 0.8));
        MOB_INFO.put(EntityType.BAT, new ImmutablePair<>(5, 0.2));
        MOB_INFO.put(EntityType.CAT, new ImmutablePair<>(12, 1.5));
        MOB_INFO.put(EntityType.WOLF, new ImmutablePair<>(10, 1.5));
        MOB_INFO.put(EntityType.HORSE, new ImmutablePair<>(10, 2.0));
        MOB_INFO.put(EntityType.AXOLOTL, new ImmutablePair<>(10, 1.5));
        MOB_INFO.put(EntityType.DONKEY, new ImmutablePair<>(4, 1.8));
        MOB_INFO.put(EntityType.MULE, new ImmutablePair<>(6, 1.9));
        MOB_INFO.put(EntityType.COD, new ImmutablePair<>(25, 0.3));
        MOB_INFO.put(EntityType.SALMON, new ImmutablePair<>(25, 0.3));
        MOB_INFO.put(EntityType.TROPICAL_FISH, new ImmutablePair<>(12, 0.8));
        MOB_INFO.put(EntityType.PUFFERFISH, new ImmutablePair<>(8, 0.5));
        MOB_INFO.put(EntityType.DOLPHIN, new ImmutablePair<>(10, 1.0));
        MOB_INFO.put(EntityType.TURTLE, new ImmutablePair<>(10, 0.95));
        MOB_INFO.put(EntityType.FOX, new ImmutablePair<>(10, 1.2));
        MOB_INFO.put(EntityType.LLAMA, new ImmutablePair<>(12, 1.0));
        MOB_INFO.put(EntityType.GOAT, new ImmutablePair<>(12, 1.0));
        MOB_INFO.put(EntityType.OCELOT, new ImmutablePair<>(10, 0.8));
        MOB_INFO.put(EntityType.PANDA, new ImmutablePair<>(5, 1.2));
        MOB_INFO.put(EntityType.RABBIT, new ImmutablePair<>(20, 1.0));
        MOB_INFO.put(EntityType.SQUID, new ImmutablePair<>(25, 0.5));
        MOB_INFO.put(EntityType.GLOW_SQUID, new ImmutablePair<>(10, 0.5));
        MOB_INFO.put(EntityType.STRIDER, new ImmutablePair<>(15, 1.0));
    }

    public ReviveKit() {
        super("revive_kit", Material.MAP);

        this.setDisplayName(ChatColor.RED + "Revive Kit " + ChatColor.YELLOW + "({name})");
        this.addVariable("name", (container) -> container.getOrDefault(NAME, PersistentDataType.STRING, "?????"));
    }

    public ItemStack fromSoul(ItemStack soul) {
        ItemStack stack = buildStack(null); //Builds the initial custom item stack, including the
        ItemMeta meta = stack.getItemMeta();      // name with ????? in it

        int flags = EntityUtils.SKIP_ATTRIBUTES + EntityUtils.SKIP_HEALTH
                + EntityUtils.SKIP_MEMORIES + EntityUtils.SKIP_INVENTORY
                + EntityUtils.SKIP_LOCATION + EntityUtils.SKIP_VELOCITY
                + EntityUtils.SKIP_POTIONS + EntityUtils.SKIP_LIVING_EFFECTS;

        PersistentDataContainer dc = soul.getItemMeta().getPersistentDataContainer();
        PersistentDataContainer dcnew = meta.getPersistentDataContainer();

        //Pull the data from the soul and record it
        dcnew.set(Keys.getKeys().ENTITY_SOUL, MapTagType.TYPE, dc.get(Keys.getKeys().ENTITY_SOUL, MapTagType.TYPE));
        dcnew.set(AnimalSoul.TYPE, PersistentDataType.STRING, dc.get(AnimalSoul.TYPE, PersistentDataType.STRING));
        dcnew.set(AnimalSoul.NAME, PersistentDataType.STRING, dc.get(AnimalSoul.NAME, PersistentDataType.STRING));
        dcnew.set(STAGE, PersistentDataType.BYTE, (byte)0);
        dcnew.set(PROGRESS, PersistentDataType.LONG, 0L);


        meta.setDisplayName(ChatColor.RED + "Revive Kit " + ChatColor.YELLOW + "(" + dc.get(AnimalSoul.NAME, PersistentDataType.STRING) + ")");

        stack.setItemMeta(meta);

        ReflectionUtils.setTrueLore(stack, getLore(EntityType.valueOf(dc.get(TYPE, PersistentDataType.STRING)), stack)); //Set the lore

        return stack;
    }


    public List<String> getLore(EntityType entity, ItemStack stack) {
        List<String> lore = new ArrayList<>();
        ItemMeta meta = stack.getItemMeta();

        lore.add(ComponentSerializer.toString(TextComponent.fromLegacyText(ChatColor.GRAY + "A way to revive the dead")));
        lore.add("");

        byte stage = meta.getPersistentDataContainer().getOrDefault(STAGE, PersistentDataType.BYTE, (byte)0);

        TranslatableComponent entityComponent = TranslationUtils.getEntity(entity);

        TextComponent stageText = new TextComponent("Revival Stage: ");
        stageText.setColor(ChatColor.YELLOW);
        TextComponent stageExtra;
        if (stage == 0) {
            stageExtra = new TextComponent("Soul Collection");
            stageExtra.setColor(ChatColor.AQUA);
            stageText.addExtra(stageExtra);

            int maxSouls = MOB_INFO.containsKey(entity) ? MOB_INFO.get(entity).getLeft() : 10;
            long souls = meta.getPersistentDataContainer().getOrDefault(PROGRESS, PersistentDataType.LONG, 0L);

            BaseComponent[] com1 = new ComponentBuilder().append("You must kill other mobs to try").color(ChatColor.GRAY).create();
            BaseComponent[] com2 = new ComponentBuilder().append("collect").color(ChatColor.GRAY).append(" Soul Shards").color(ChatColor.AQUA)
                    .append(". Only ").color(ChatColor.GRAY).append(entityComponent).color(ChatColor.YELLOW).create();
            BaseComponent[] com3 = new ComponentBuilder().append("souls will be collected.").color(ChatColor.GRAY).create();

            BaseComponent[] com4 = new ComponentBuilder().append(souls + "/" + maxSouls + " souls shards collected").color(ChatColor.AQUA).create();

            lore.add(ComponentSerializer.toString(stageText));
            lore.add(ComponentSerializer.toString(com1));
            lore.add(ComponentSerializer.toString(com2));
            lore.add(ComponentSerializer.toString(com3));
            lore.add("");
            lore.add(ComponentSerializer.toString(com4));
        } else if (stage == 1) {
            stageExtra = new TextComponent("Player Sacrifice");
            stageExtra.setColor(ChatColor.RED);
            stageText.addExtra(stageExtra);

            long exp = meta.getPersistentDataContainer().getOrDefault(PROGRESS, PersistentDataType.LONG, 0L);
            double scale = MOB_INFO.containsKey(entity) ? MOB_INFO.get(entity).getRight() : 1.0;
            int expNeeded = (int) ((double)EXP_NEEDED * scale);
            int percent = (int)(((double)exp / (double)expNeeded) * 100);
            ChatColor percentC = (percent <= 40 ? ChatColor.YELLOW : (percent <= 85 ? ChatColor.GREEN : ChatColor.DARK_GREEN));

            BaseComponent[] com1 = new ComponentBuilder().append("Sacrifice your experience to help").color(ChatColor.GRAY).create();
            BaseComponent[] com2 = new ComponentBuilder().append("fund the revival of your pet.").color(ChatColor.GRAY).create();
            BaseComponent[] com3 = new ComponentBuilder().append(exp + "/" + expNeeded + " experience deposited").color(ChatColor.AQUA)
                    .append("(" + percent + "%)").color(percentC).create();
            BaseComponent[] com4 = new ComponentBuilder().append("Right click to deposit 20% of your XP!").color(ChatColor.YELLOW).create();

            lore.add(ComponentSerializer.toString(stageText));
            lore.add(ComponentSerializer.toString(com1));
            lore.add(ComponentSerializer.toString(com2));
            lore.add(ComponentSerializer.toString(com3));
            lore.add("");
            lore.add(ComponentSerializer.toString(com4));
        } else if (stage == 2) {
            stageExtra = new TextComponent("Revival");
            stageExtra.setColor(ChatColor.DARK_RED);
            stageText.addExtra(stageExtra);

            String name = meta.getPersistentDataContainer().getOrDefault(NAME, PersistentDataType.STRING, "Boris");

            BaseComponent[] com1 = new ComponentBuilder().append("It's time! When the moon is").color(ChatColor.GRAY).create();
            BaseComponent[] com2 = new ComponentBuilder().append("at its highest, use this kit").color(ChatColor.GRAY).create();
            BaseComponent[] com3 = new ComponentBuilder().append("to revive ").color(ChatColor.GRAY)
                    .append(name).color(ChatColor.AQUA).append("!").color(ChatColor.GRAY).create();
            BaseComponent[] com4 = new ComponentBuilder().append("Right click to use!").color(ChatColor.YELLOW).create();

            lore.add(ComponentSerializer.toString(stageText));
            lore.add(ComponentSerializer.toString(com1));
            lore.add(ComponentSerializer.toString(com2));
            lore.add(ComponentSerializer.toString(com3));
            lore.add("");
            lore.add(ComponentSerializer.toString(com4));
        } else {
            lore.add(ComponentSerializer.toString(TextComponent.fromLegacyText("Well this is awkward. You broke something.")));
        }

        return lore;
    }

    @Override
    public ItemStack updateStack(ItemStack stack, Player player) {
        ItemStack newstack = super.updateStack(stack, player);
        ItemMeta meta = newstack.getItemMeta();
        String type = meta.getPersistentDataContainer().get(TYPE, PersistentDataType.STRING);

        ReflectionUtils.setTrueLore(newstack, getLore(EntityType.valueOf(type), stack));
        return newstack;
    }


    @Override
    public boolean onInteract(Player player, Block block, CustomItem customItem, ItemStack itemStack, boolean b) {

            ItemMeta meta = itemStack.getItemMeta();

            Map properties = meta.getPersistentDataContainer().get(Keys.getKeys().ENTITY_SOUL, MapTagType.TYPE);

            EntityUtils.decodeCreate(properties, block.getLocation().add(0, 1, 0));

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.5F);

            itemStack.setAmount(itemStack.getAmount() - 1);

        return true;
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer dc = meta.getPersistentDataContainer();

        byte stage = dc.getOrDefault(STAGE, PersistentDataType.BYTE, (byte)0);

        if (stage == 1) {
            if (player.getTotalExperience() > 0) {
                EntityType type = EntityType.valueOf(dc.get(AnimalSoul.TYPE, PersistentDataType.STRING));
                int exp = dc.getOrDefault(PROGRESS, PersistentDataType.INTEGER, 0);
                double scale = MOB_INFO.containsKey(type) ? MOB_INFO.get(type).getRight() : 1.0;
                int expNeeded = (int) ((double)EXP_NEEDED * scale);

                int left = expNeeded - exp;

                int tenPercent = player.getTotalExperience() / 5;

                int take = Math.min(left, tenPercent);

                player.setTotalExperience(player.getTotalExperience() - take);

                exp += tenPercent;

                if (exp >= expNeeded) {
                    dc.set(STAGE, PersistentDataType.BYTE, (byte)2);
                    dc.set(PROGRESS, PersistentDataType.LONG, System.currentTimeMillis());
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0.5F);
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.5F);
                }


            }



        }

        return true;
    }
}
