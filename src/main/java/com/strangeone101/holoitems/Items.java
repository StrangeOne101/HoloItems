package com.strangeone101.holoitems;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.strangeone101.holoitems.items.*;
import com.strangeone101.holoitemsapi.CustomItem;
import com.strangeone101.holoitemsapi.HoloItemsAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Items {

    public static RushiaShield RUSHIA_SHIELD;
    public static BerryTrident BERRY_TRIDENT;
    public static CustomItem ETHERREAL_ESSENSE;
    public static CustomItem OTHERWORLDLY_ADHESIVE;
    public static CustomItem NETHER_DIAMOND;
    public static CustomItem ENCHANTED_SAND;
    public static CustomItem ENCHANTED_DIRT;
    public static CustomItem ENCHANTED_STONE;
    public static CustomItem MOGU_BOOTS;
    public static CustomItem RUSSIAN_ROULETTE_REVOLVER;
    public static CustomItem RUSSIAN_ROULETTE_REVOLVER_LARGE;
    public static CustomItem GEM_RUBY;
    public static CustomItem GEM_SAPPHIRE;
    public static CustomItem GEM_TOPAZ;
    public static CustomItem GEM_AMETHYST;
    public static CustomItem SPONGE_LARGE;
    public static CustomItem SPONGE_LARGE_WET;
    public static CustomItem SPONGE_LAVA;
    public static CustomItem SPONGE_LAVA_WET;

    public static CustomItem SCRAMBLED_EGG;
    public static CustomItem BUTTER_CLOCK;
    public static CustomItem SPICY_CHICKEN;
    public static CustomItem SANDWICH_HONEY;
    public static CustomItem SANDWICH_BEEF;
    public static CustomItem SANDWICH_PORK;
    public static CustomItem SANDWICH_CHICKEN;
    public static CustomItem SANDWICH_CHICKEN_SPICY;

    public static CustomItem CAT_PLUSHIE;

    public static CustomItem DEATH_CRYSTAL_1;
    public static CustomItem DEATH_CRYSTAL_2;
    public static CustomItem DEATH_CRYSTAL_3;

    public static CustomItem ENDER_CHEST_UPGRADER_4;
    public static CustomItem ENDER_CHEST_UPGRADER_5;
    public static CustomItem ENDER_CHEST_UPGRADER_6;

    public static CustomItem PEKO_SWORD;

    public static CustomItem MELON_CANNON;
    public static CustomItem GOLD_CANNON;
    public static CustomItem ADMIN_CANNON;

    public static AnimalSoul ANIMAL_SOUL;
    public static AnimalSoul CURSED_ANIMAL_SOUL;
    public static ReviveKit REVIVE_ANIMAL_KIT;

    public static CustomItem SOUL_GLASS;
    public static CustomItem SOUL_STEEL;
    public static CustomItem SOUL_GOLD;
    public static CustomItem SOUL_GOLD_ENHANCED;

    public static CustomItem RAINBOW_HELMET;
    public static CustomItem RAINBOW_CHESTPLATE;
    public static CustomItem RAINBOW_LEGGINGS;
    public static CustomItem RAINBOW_BOOTS;
    public static CustomItem RAINBOW_DYE;
    public static CustomItem GRADIENT_DYE;

    public static CustomItem CAPTURE_SWORD;
    public static CustomItem PAPER_EGG;
    public static CustomItem CORRUPTED_PAPER_EGG;

    public static CustomItem GROUNDPOUND_BOOTS;

    public static CustomItem HOLY_HELMET;
    public static CustomItem HOLY_CHESTPLATE;
    public static CustomItem HOLY_LEGGINGS;
    public static CustomItem HOLY_BOOTS;

    public static void registerHoloItems() {

        //1000-1999 Standard Items: Ingots, crafting ingredients, but not food
        ETHERREAL_ESSENSE = new CustomItem("void_essense", Material.PURPLE_DYE)
                .setDisplayName(ChatColor.LIGHT_PURPLE + "Void Essense")
                .addLore(ChatColor.DARK_GRAY + "Crafting Ingredient").addLore("")
                .addLore(ChatColor.GRAY + "Mysterious...? What use could it have?")
                .setInternalID(1000).register();
        OTHERWORLDLY_ADHESIVE = new CustomItem("otherworldly_adhesive", Material.BLAZE_POWDER)
                .setDisplayName(ChatColor.LIGHT_PURPLE + "Ethereal Adhesive")
                .addLore(ChatColor.DARK_GRAY + "Crafting Ingredient").addLore("")
                .addLore(ChatColor.GRAY + "You feel a mysterious force pulling")
                .addLore(ChatColor.GRAY + "things towards it").setInternalID(1001).register();
        NETHER_DIAMOND = new CustomItem("nether_diamond", Material.DIAMOND)
                .setDisplayName(ChatColor.RED + "Nether Diamond")
                .addLore(ChatColor.DARK_GRAY + "Crafting Ingredient").addLore("")
                .addLore(ChatColor.GRAY + "Hot to the touch").setInternalID(1002).register();
        GEM_RUBY = new CustomItem("gem_ruby", Material.EMERALD, ChatColor.DARK_RED + "Ruby")
                .setInternalID(1003).register();
        GEM_SAPPHIRE = new CustomItem("gem_sapphire", Material.EMERALD, ChatColor.BLUE + "Sapphire")
                .setInternalID(1004).register();
        GEM_TOPAZ = new CustomItem("gem_topaz", Material.EMERALD, ChatColor.GOLD + "Topaz")
                .setInternalID(1005).register();
        GEM_AMETHYST = new CustomItem("gem_amethyst", Material.EMERALD, ChatColor.LIGHT_PURPLE + "Amethyst")
                .setInternalID(1006).register();
        SOUL_GLASS = new CustomItem("soul_glass", Material.BROWN_STAINED_GLASS).setDisplayName(ChatColor.RED + "Soul Glass").addLore(ChatColor.DARK_GRAY + "Crafting Ingredient")
                .setInternalID(1007).register();
        SOUL_STEEL = new CustomItem("soul_steel", Material.IRON_INGOT).setDisplayName(ChatColor.RED + "Soul Silver").addLore(ChatColor.DARK_GRAY + "Crafting Ingredient")
                .setInternalID(1008).register();
        SOUL_GOLD = new CustomItem("soul_gold", Material.GOLD_INGOT).setDisplayName(ChatColor.YELLOW + "Soulaurium").addLore(ChatColor.DARK_GRAY + "Crafting Ingredient")
                .setInternalID(1009).register();
        SOUL_GOLD_ENHANCED = new CustomItem("soul_gold_enhanced", Material.GOLD_INGOT).setDisplayName(ChatColor.YELLOW + "Enhanced Soulaurum").addLore(ChatColor.DARK_GRAY + "Crafting Ingredient")
                .setInternalID(1010).register();
        PAPER_EGG = new CustomItem("paper_egg", Material.PAPER, "Paper Egg")
                .setInternalID(1011).register();

        //2000-2999 Non-Standard Items/with Abilities: Such as the cat plushie, talismans, etc
        CAT_PLUSHIE = new CatPlushie().setInternalID(2000).register();
        DEATH_CRYSTAL_1 = new DeathCrystal("death_crystal_1",1).setInternalID(2001).register();
        DEATH_CRYSTAL_2 = new DeathCrystal("death_crystal_2",2).setInternalID(2002).register();
        DEATH_CRYSTAL_3 = new DeathCrystal("death_crystal_3",3).setInternalID(2003).register();
        ENDER_CHEST_UPGRADER_4 = new EnderChestUpgrader("ender_chest_upgrade_1",1,"four")
                .setInternalID(2004).register();
        ENDER_CHEST_UPGRADER_5 = new EnderChestUpgrader("ender_chest_upgrade_2",2,"five")
                .setInternalID(2005).register();
        ENDER_CHEST_UPGRADER_6 = new EnderChestUpgrader("ender_chest_upgrade_3",3,"six")
                .setInternalID(2006).register();
        ANIMAL_SOUL = (AnimalSoul) new AnimalSoul().setDisplayName(ChatColor.LIGHT_PURPLE + "{name}'s Soul")
                .setInternalID(2007).register();
        RAINBOW_DYE = new RainbowDye().setInternalID(2008).register();
        GRADIENT_DYE = new GradientDye().setInternalID(2009).register();
        REVIVE_ANIMAL_KIT = (ReviveKit) new ReviveKit().setInternalID(2010).register();
        CORRUPTED_PAPER_EGG = new CustomItem("corrupted_paper_egg", Material.EGG, "Corrupted Paper Egg")
                .setInternalID(2011).register();

        //3000-3999 Weapons: Katanas, broadswords, rapiers, magic weapons
        PEKO_SWORD = new WarcriminalSword().setInternalID(3000).register();
        MELON_CANNON = new BlockCannon("melon_cannon", Material.GOLDEN_HORSE_ARMOR, new ItemStack(Material.MELON), new ItemStack(Material.MELON), BlockCannon.MELON_HIT)
                .setDisplayName(ChatColor.RED + "Melon Cannon")
                .addLore(ChatColor.DARK_GRAY + "Party Weapon")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Right click to fire. " + ChatColor.RED + "Requires melons" + ChatColor.YELLOW + " to fire.")
                .setInternalID(3001).register();
        GOLD_CANNON = new BlockCannon("gold_cannon", Material.GOLDEN_HORSE_ARMOR, new ItemStack(Material.GOLD_BLOCK), new ItemStack(Material.GOLD_BLOCK), BlockCannon.GOLD_HIT)
                .setDisplayName(ChatColor.YELLOW + "Gold Cannon")
                .addLore(ChatColor.DARK_GRAY + "Party Weapon")
                .addLore("")
                .addLore(ChatColor.YELLOW + "Right click to fire. " + ChatColor.RED + "Requires gold blocks" + ChatColor.YELLOW + " to fire.")
                .setInternalID(3002).register();
        CAPTURE_SWORD = new CaptureSword().setInternalID(3003).register();
        //BERRY_TRIDENT = new BerryTrident();

        //4000-4999 Tools: Pickaxes, fishing rods, etc

        //5000-5999 Armors: Armor, Shields, Equipables
        RUSHIA_SHIELD = (RushiaShield) new RushiaShield().setInternalID(5000).register();
        MOGU_BOOTS = new MoguBoots().setInternalID(5001).register();
        RAINBOW_HELMET = new CustomItem("rainbow_helmet", Material.LEATHER_HELMET).setDisplayName(IridiumColorAPI.rainbow(ChatColor.BOLD + "RAINBOW HELMET", 1)).addLore(ChatColor.GRAY + "It does what you expect!")
                .addNBT("CMIRainbowArmor", 1).setFlags(ItemFlag.HIDE_DYE).setLeatherColor(0xFF2222)
                .setInternalID(5002).register();
        RAINBOW_CHESTPLATE = new CustomItem("rainbow_chestplate", Material.LEATHER_CHESTPLATE).setDisplayName(IridiumColorAPI.rainbow(ChatColor.BOLD + "RAINBOW CHESTPLATE", 1)).addLore(ChatColor.GRAY + "It does what you expect!")
                .addNBT("CMIRainbowArmor", 1).setFlags(ItemFlag.HIDE_DYE).setLeatherColor(0xFFFF22)
                .setInternalID(5003).register();
        RAINBOW_LEGGINGS = new CustomItem("rainbow_leggings", Material.LEATHER_LEGGINGS).setDisplayName(IridiumColorAPI.rainbow(ChatColor.BOLD + "RAINBOW LEGGINGS", 1)).addLore(ChatColor.GRAY + "It does what you expect!")
                .addNBT("CMIRainbowArmor", 1).setFlags(ItemFlag.HIDE_DYE).setLeatherColor(0x22FF22)
                .setInternalID(5004).register();
        RAINBOW_BOOTS = new CustomItem("rainbow_boots", Material.LEATHER_BOOTS).setDisplayName(IridiumColorAPI.rainbow(ChatColor.BOLD + "RAINBOW BOOTS", 1)).addLore(ChatColor.GRAY + "It does what you expect!")
                .addNBT("CMIRainbowArmor", 1).setFlags(ItemFlag.HIDE_DYE).setLeatherColor(0x2222FF)
                .setInternalID(5005).register();
        GROUNDPOUND_BOOTS = new GroundpoundBoots().setInternalID(5006).register();
        HOLY_HELMET = new HolyArmor("holy_helmet", Material.IRON_HELMET, "Holy Helmet", 250, EquipmentSlot.HEAD, 2, 1)
                .setInternalID(5007).register();
        HOLY_CHESTPLATE = new HolyArmor("holy_chestplate", Material.IRON_CHESTPLATE, "Holy Chestplate", 400, EquipmentSlot.CHEST, 6, 1)
                .setInternalID(5008).register();
        HOLY_LEGGINGS = new HolyArmor("holy_leggings", Material.IRON_LEGGINGS, "Holy Leggings", 350, EquipmentSlot.LEGS, 5, 1)
                .setInternalID(5009).register();
        HOLY_BOOTS = new HolyArmor("holy_boots", Material.IRON_BOOTS, "Holy Boots", 250, EquipmentSlot.FEET, 2, 1)
                .setInternalID(5010).register();

        //6000-6999 Consumables: Food & Potions
        SCRAMBLED_EGG = new BasicFood("scrambled_egg", Material.BAKED_POTATO, 3, 2).setDisplayName(ChatColor.YELLOW + "Scrambled Egg")
                .setInternalID(6000).register();
        BUTTER_CLOCK = new BasicFood("butter_clock", Material.CLOCK, 4, 3).setDisplayName(ChatColor.YELLOW + "Butter Clock")
                .setInternalID(6001).register();
        SPICY_CHICKEN = new PotionFood("spicy_chicken", Material.COOKED_CHICKEN, 6, 6, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 12, 0), 20 * 60 * 4)
                .setDisplayName(ChatColor.RED + "Spicy Chicken")
                .setInternalID(6002).register();
        SANDWICH_BEEF = new BasicFood("sandwich_beef", Material.BREAD, 12, 10).setDisplayName(ChatColor.YELLOW + "Beef Sandwich")
                .setInternalID(6003).register();
        SANDWICH_PORK = new BasicFood("sandwich_pork", Material.BREAD, 12, 10).setDisplayName(ChatColor.YELLOW + "Pork Sandwich")
                .setInternalID(6004).register();
        SANDWICH_HONEY = new BasicFood("sandwich_honey", Material.BREAD, 12, 10).setDisplayName(ChatColor.GOLD + "Honey Sandwich")
                .setInternalID(6005).register();
        SANDWICH_CHICKEN = new BasicFood("sandwich_chicken", Material.BREAD, 10, 10).setDisplayName(ChatColor.YELLOW + "Chicken Sandwich")
                .setInternalID(6006).register();
        SANDWICH_CHICKEN_SPICY = new PotionFood("sandwich_chicken_spicy", Material.BREAD, 12, 10, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 18, 0), 20 * 60 * 8).setDisplayName(ChatColor.RED + "Spicy Chicken Sandwich")
                .setInternalID(6007).register();

        //7000-7999 Blocks: Alloy Furnace
        ENCHANTED_SAND = new EnchantedBlock("enchanted_sand", Material.SAND, "sand")
                .setDisplayName(ChatColor.LIGHT_PURPLE + "Enchanted Sand")
                .setInternalID(7000).register();
        ENCHANTED_DIRT = new EnchantedBlock("enchanted_dirt", Material.DIRT, "dirt")
                .setDisplayName(ChatColor.LIGHT_PURPLE + "Enchanted Dirt")
                .setInternalID(7001).register();
        ENCHANTED_STONE = new EnchantedBlock("enchanted_stone", Material.STONE, "stone")
                .setDisplayName(ChatColor.LIGHT_PURPLE + "Enchanted Stone")
                .setInternalID(7002).register();
        SPONGE_LARGE_WET = new CustomItem("sponge_large_wet", Material.WET_SPONGE, ChatColor.YELLOW + "Large Sponge (Full)")
                .addLore(ChatColor.DARK_GRAY + "Block").addLore("").addLore(ChatColor.GRAY + "Soaks up a large amount of water.")
                .addLore("").addLore(ChatColor.RED + "Dry it in a furnace!")
                .setInternalID(7003).register();
        SPONGE_LARGE = new MegaSponge("sponge_large", 11, Material.WATER, SPONGE_LARGE_WET)
                .setDisplayName(ChatColor.YELLOW + "Large Sponge").addLore(ChatColor.DARK_GRAY + "Placeable Block").addLore("")
                .addLore(ChatColor.GRAY + "Soaks up nearby lava")
                .setInternalID(7004).register();
        SPONGE_LAVA_WET = new CustomItem("sponge_lava_wet", Material.WET_SPONGE, ChatColor.RED + "Lava Sponge (Full)")
                .addLore(ChatColor.DARK_GRAY + "Block").addLore("").addLore(ChatColor.GRAY + "Soaks up nearby lava")
                .addLore("").addLore(ChatColor.YELLOW + "Must be emptied!")
                .setInternalID(7005).register();
        SPONGE_LAVA = new MegaSponge("sponge_lava", 5, Material.LAVA, SPONGE_LAVA_WET)
                .setDisplayName(ChatColor.RED + "Lava Sponge").addLore(ChatColor.DARK_GRAY + "Placeable Block").addLore("")
                .addLore(ChatColor.GRAY + "Soaks up a large amount of lava")
                .setInternalID(7006).register();

        //8000-8999 Other
        RUSSIAN_ROULETTE_REVOLVER = new RussianRevolver("russian_roulette_revolver", 6)
                .setDisplayName(ChatColor.YELLOW + "1967 Soviet Russian Revolver")
                .addLore(ChatColor.GRAY + "Great for playing russian roulette!").addLore("")
                .addLore(ChatColor.GOLD + "Shift right click: " + ChatColor.YELLOW + "Spin barrel")
                .addLore(ChatColor.GOLD + "Right click: " + ChatColor.YELLOW + "Fire one in the chamber").setInternalID(8000).register();
        RUSSIAN_ROULETTE_REVOLVER_LARGE = new RussianRevolver("russian_roulette_revolver_extended", 12)
                .setDisplayName(ChatColor.YELLOW + "1968 Extended Soviet Russian Revolver")
                .addLore(ChatColor.GRAY + "Great for playing russian roulette!")
                .addLore(ChatColor.GRAY + "The extended barrel fits 12 bullets.").addLore("")
                .addLore(ChatColor.GOLD + "Shift right click: " + ChatColor.YELLOW + "Spin barrel")
                .addLore(ChatColor.GOLD + "Right click: " + ChatColor.YELLOW + "Fire one in the chamber").setInternalID(8001).register();

        //9000-9999 GUI Items: Items not meant to be obtained

    }
}
