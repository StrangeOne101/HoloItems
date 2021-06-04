package com.strangeone101.holoitems.items;

import com.strangeone101.holoitems.abilities.KnockbackAbility;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.interfaces.Interactable;
import com.strangeone101.holoitemsapi.interfaces.Swingable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class WarcriminalSword extends CustomItem implements Interactable, Swingable {

    public WarcriminalSword() {
        super("peko_sword", Material.CARROT);

        this.setDisplayName(ChatColor.RED + "War Criminal Sword");
        String arrow1 = ChatColor.YELLOW + "\u2B08 ";
        String arrow2 = ChatColor.YELLOW + "\u2B0A ";
        String ha = ChatColor.GRAY + "" + ChatColor.ITALIC + "Ha ";
        String quote = ChatColor.GRAY + "" + ChatColor.ITALIC + "\"";
        this.addLore(quote + ha + arrow1 + ha + arrow2 + ha + arrow1 + ha + arrow2 + ha + arrow1 + quote);
        this.addLore("");
        this.addLore(ChatColor.GOLD + "" + ChatColor.BOLD + "PEKO SMASH " + ChatColor.YELLOW + "(Right Click)");
        this.addLore(ChatColor.GRAY + "Send your enemies flying through the air! ");
        this.addLore("");
        this.addLore(ChatColor.DARK_AQUA + "Cooldown: 80s");
        this.addLore("");
        this.addLore(ChatColor.GREEN + "Durability: {durability}/{maxdurability}"); //Broken :(
    }

    @Override
    public ItemStack buildStack(Player player) {
        ItemStack stack = super.buildStack(player);

        ItemMeta meta = stack.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        stack.setItemMeta(meta);

        return stack;
    }

    @Override
    public ItemStack updateStack(ItemStack stack, Player player) {
        ItemMeta meta = stack.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "damage", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        stack.setItemMeta(meta);

        return super.updateStack(stack, player);
    }

    @Override
    public int getMaxDurability() {
        return 100;
    }

    @Override
    public boolean onInteract(Player player, CustomItem customItem, ItemStack itemStack) {
        new KnockbackAbility(player, itemStack, player.getInventory(), player.getInventory().getHeldItemSlot(), 3, 1.1,80 * 1000);
        return false;
    }

    @Override
    public void swing(Player player, CustomItem customItem, ItemStack itemStack) {

    }

    @Override
    public boolean hit(Entity entity, Player player, CustomItem customItem, ItemStack itemStack) {
        customItem.damageItem(itemStack, 1, player);
        return false;
    }
}
