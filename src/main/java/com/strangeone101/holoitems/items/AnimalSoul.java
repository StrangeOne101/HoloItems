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
import org.bukkit.persistence.PersistentDataType;


import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnimalSoul extends CustomItem implements BlockInteractable {

    public static NamespacedKey TYPE = new NamespacedKey(HoloItemsPlugin.INSTANCE, "type");
    public static NamespacedKey NAME = new NamespacedKey(HoloItemsPlugin.INSTANCE, "name");

    public AnimalSoul() {
        super("animal_soul", Material.NAME_TAG);

        this.addVariable("name", (container) -> container.getOrDefault(NAME, PersistentDataType.STRING, "?????"));
    }

    public ItemStack createSoul(Entity entity) {
        ItemStack stack = buildStack(null); //Builds the initial custom item stack, including the
                                                                    //name with ????? in it
        int flags = EntityUtils.SKIP_ATTRIBUTES + EntityUtils.SKIP_HEALTH
                + EntityUtils.SKIP_MEMORIES + EntityUtils.SKIP_INVENTORY
                + EntityUtils.SKIP_LOCATION + EntityUtils.SKIP_VELOCITY
                + EntityUtils.SKIP_POTIONS + EntityUtils.SKIP_LIVING_EFFECTS;
        Map<String, Object> map = EntityUtils.encode(entity, flags);

        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(Keys.getKeys().ENTITY_SOUL, MapTagType.TYPE, map); //Set the entity data in the item
        meta.getPersistentDataContainer().set(TYPE, PersistentDataType.STRING, entity.getType().toString()); //Set the entity type
        meta.getPersistentDataContainer().set(NAME, PersistentDataType.STRING, entity.getCustomName());
        //Set the new name of the item with the updated entity name

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + entity.getCustomName() + "'s Soul");

        stack.setItemMeta(meta);
         //Update the item with the data above


        ReflectionUtils.setTrueLore(stack, Items.ANIMAL_SOUL.getLore(entity.getType())); //Set the lore

        return stack;
    }


    public List<String> getLore(EntityType entity) {
        List<String> lore = new ArrayList<>();

        BaseComponent blank = (new ComponentBuilder()).append("").color(ChatColor.WHITE).italic(false).getCurrentComponent();;
        BaseComponent comp = blank.duplicate();
        comp.setExtra(Arrays.asList(TextComponent.fromLegacyText(ChatColor.DARK_GRAY + "Animal Soul")));
        BaseComponent comp2 = blank.duplicate();
        comp2.setExtra(Arrays.asList(TextComponent.fromLegacyText(ChatColor.GRAY + "The soul of the departed")));

        TranslatableComponent typeOfEntity = new TranslatableComponent("gui.entity_tooltip.type");
        typeOfEntity.setColor(ChatColor.YELLOW);
        typeOfEntity.setItalic(false);
        TranslatableComponent entityComponent = TranslationUtils.getEntity(entity);
        entityComponent.setColor(ChatColor.AQUA);
        entityComponent.setItalic(false);
        typeOfEntity.addWith(entityComponent);

        lore.add(ComponentSerializer.toString(comp));
        lore.add(ComponentSerializer.toString(blank));
        lore.add(ComponentSerializer.toString(comp2));
        lore.add(ComponentSerializer.toString(typeOfEntity));
        return lore;
    }

    @Override
    public ItemStack updateStack(ItemStack stack, Player player) {
        ItemStack newstack = super.updateStack(stack, player);
        ItemMeta meta = newstack.getItemMeta();
        String type = meta.getPersistentDataContainer().get(TYPE, PersistentDataType.STRING);

        ReflectionUtils.setTrueLore(newstack, getLore(EntityType.valueOf(type)));
        return newstack;
    }

    @ItemEvent(active = ActiveConditions.NONE, target = Target.ALL)
    public void onDeath(EventContext context, EntityDeathEvent event) {
        if (event.getEntity() instanceof Animals) {
            if (event.getEntity().getCustomName() != null) {
                event.getDrops().add(createSoul(event.getEntity()));
            }
        }
    }

    @Override
    public boolean onInteract(Player player, Block block, CustomItem customItem, ItemStack itemStack, boolean b) {
        if (player.getName().equalsIgnoreCase("strangeone101")) { //Testing lock
            ItemMeta meta = itemStack.getItemMeta();

            Map properties = meta.getPersistentDataContainer().get(Keys.getKeys().ENTITY_SOUL, MapTagType.TYPE);

            EntityUtils.decodeCreate(properties, block.getLocation().add(0, 1, 0));

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.5F);

            itemStack.setAmount(itemStack.getAmount() - 1);
        }
        return true;
    }
}
