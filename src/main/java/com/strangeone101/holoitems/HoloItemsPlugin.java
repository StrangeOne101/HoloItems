package com.strangeone101.holoitems;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.strangeone101.holoitems.command.HoloItemsCommand;
import com.strangeone101.holoitems.items.DeathCrystal;
import com.strangeone101.holoitems.items.GradientDye;
import com.strangeone101.holoitems.items.ReviveKit;
import com.strangeone101.holoitems.tables.Cat;
import com.strangeone101.holoitemsapi.loot.CustomLootRegistry;
import com.strangeone101.holoitems.tables.Endermite;
import com.strangeone101.holoitems.tables.GemOre;
import com.strangeone101.holoitems.tables.Spawner;
import com.strangeone101.holoitemsapi.recipe.CIRecipeChoice;
import com.strangeone101.holoitemsapi.recipe.ItemGroups;
import com.strangeone101.holoitemsapi.recipe.RecipeBuilder;
import com.strangeone101.holoitemsapi.recipe.RecipeGroup;
import com.strangeone101.holoitemsapi.recipe.RecipeManager;
import com.strangeone101.holoitemsapi.CustomItemRegistry;
import com.strangeone101.holoitemsapi.HoloItemsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static com.strangeone101.holoitemsapi.recipe.RecipeGroup.GROUP_1;
import static com.strangeone101.holoitemsapi.recipe.RecipeGroup.GROUP_2;

public final class HoloItemsPlugin extends JavaPlugin {

    public static HoloItemsPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        getCommand("holoitem").setExecutor(new HoloItemsCommand());

        HoloItemsAPI.setup(this);
        new Keys();

        getServer().getPluginManager().registerEvents(new DeathCrystal("",0), this);
        getServer().getPluginManager().registerEvents(new HoloPatchListener(), this);

        Items.registerHoloItems();
        registerRecipes();

        CustomLootRegistry.registerDeathTable(EntityType.ENDERMITE, new Endermite());
        CustomLootRegistry.registerDeathTable(EntityType.CAT, new Cat());
        CustomLootRegistry.registerDeathTable(EntityType.OCELOT, new Cat());
        CustomLootRegistry.registerBlockBreakTable(Material.SPAWNER, new Spawner());
        CustomLootRegistry.registerBlockBreakTable(Material.EMERALD_ORE, new GemOre());

        getLogger().info("Registered " + CustomItemRegistry.getCustomItems().size()
                + " custom items and " + RecipeManager.getRegisteredAmount() + " custom recipes!");

        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        HoloItemsAPI.shutdown();
    }

    private void registerRecipes() {
        ItemStack rushiaShield = Items.RUSHIA_SHIELD.buildStack(null);
        ShapedRecipe rushiaShieldRecipe = new ShapedRecipe(new NamespacedKey(this, "rushia_shield"), rushiaShield);
        rushiaShieldRecipe.shape("WSW", "WWW", " W ");
        rushiaShieldRecipe.setIngredient('W', Material.WARPED_PLANKS);
        rushiaShieldRecipe.setIngredient('S', Material.NETHERITE_INGOT);

        RecipeManager.registerRecipe(rushiaShieldRecipe);

        //Storing the recipe that allows shields to be decorated
        RecipeManager.addRecipe(Bukkit.getRecipe(NamespacedKey.minecraft("shield_decoration")), (NamespacedKey.minecraft("shield_decoration")));

        ItemStack etherreal_essense = Items.ETHERREAL_ESSENSE.buildStack(null);
        ItemStack otherworldly_adhesive = Items.OTHERWORLDLY_ADHESIVE.buildStack(null);
        ItemStack nether_diamond = Items.NETHER_DIAMOND.buildStack(null);
        ItemStack enchanted_sand = Items.ENCHANTED_SAND.buildStack(null);

        ShapelessRecipe adhesiveRecipe = new ShapelessRecipe(new NamespacedKey(this, "otherworldly_adhesive"), otherworldly_adhesive);
        adhesiveRecipe.addIngredient(Material.WHEAT);
        adhesiveRecipe.addIngredient(Material.WATER_BUCKET);
        adhesiveRecipe.addIngredient(Material.SAND);
        adhesiveRecipe.addIngredient(new CIRecipeChoice(etherreal_essense));

        RecipeManager.registerRecipe(adhesiveRecipe);

        ShapelessRecipe netherDiamondRecipe = new ShapelessRecipe(new NamespacedKey(this, "nether_diamond"), nether_diamond);
        netherDiamondRecipe.addIngredient(Material.DIAMOND);
        netherDiamondRecipe.addIngredient(Material.NETHERITE_INGOT);
        netherDiamondRecipe.addIngredient(Material.NETHER_STAR);
        netherDiamondRecipe.addIngredient(new CIRecipeChoice(otherworldly_adhesive));

        RecipeManager.registerRecipe(netherDiamondRecipe);

        ShapedRecipe enchantedSandRecipe = new ShapedRecipe(new NamespacedKey(this, "enchanted_sand"), enchanted_sand);
        enchantedSandRecipe.shape("SSS", "SXS", "SSS");
        enchantedSandRecipe.setIngredient('S', new RecipeChoice.ExactChoice(new ItemStack(Material.SAND, 64)));
        enchantedSandRecipe.setIngredient('X', new CIRecipeChoice(nether_diamond));

        RecipeManager.registerRecipe(enchantedSandRecipe);

        ShapedRecipe testRecipe = new ShapedRecipe(new NamespacedKey(this, "test"), Items.DEATH_CRYSTAL_2.buildStack(null));
        testRecipe.shape("SSS", "SXS", "SSS");
        testRecipe.setIngredient('S', Material.OBSIDIAN);
        testRecipe.setIngredient('X', new CIRecipeChoice(Items.DEATH_CRYSTAL_1.buildStack(null)));

        RecipeManager.registerRecipe(testRecipe);

        ItemStack rainbowDyeStack = Items.RAINBOW_DYE.buildStack(null);
        ShapelessRecipe rainbowDye = new ShapelessRecipe(new NamespacedKey(this, "rainbow_dye"), rainbowDyeStack);
        rainbowDye.addIngredient(Material.RED_DYE);
        rainbowDye.addIngredient(Material.ORANGE_DYE);
        rainbowDye.addIngredient(Material.YELLOW_DYE);
        rainbowDye.addIngredient(Material.LIME_DYE);
        rainbowDye.addIngredient(Material.LIGHT_BLUE_DYE);
        rainbowDye.addIngredient(Material.BLUE_DYE);
        rainbowDye.addIngredient(Material.PURPLE_DYE);
        rainbowDye.addIngredient(Material.MAGENTA_DYE);
        rainbowDye.addIngredient(Material.PINK_DYE);
        RecipeManager.registerRecipe(rainbowDye);

        ShapelessRecipe rainbowHelmet = new ShapelessRecipe(new NamespacedKey(this, "rainbow_helmet"), Items.RAINBOW_HELMET.buildStack(null));
        for (int i = 0; i < 8; i++) rainbowHelmet.addIngredient(new CIRecipeChoice(rainbowDyeStack));
        rainbowHelmet.addIngredient(Material.LEATHER_HELMET);

        ShapelessRecipe rainbowChest = new ShapelessRecipe(new NamespacedKey(this, "rainbow_chest"), Items.RAINBOW_CHESTPLATE.buildStack(null));
        for (int i = 0; i < 8; i++) rainbowChest.addIngredient(new CIRecipeChoice(rainbowDyeStack));
        rainbowChest.addIngredient(Material.LEATHER_CHESTPLATE);

        ShapelessRecipe rainbowLegs = new ShapelessRecipe(new NamespacedKey(this, "rainbow_legs"), Items.RAINBOW_LEGGINGS.buildStack(null));
        for (int i = 0; i < 8; i++) rainbowLegs.addIngredient(new CIRecipeChoice(rainbowDyeStack));
        rainbowLegs.addIngredient(Material.LEATHER_LEGGINGS);

        ShapelessRecipe rainbowBoots = new ShapelessRecipe(new NamespacedKey(this, "rainbow_boots"), Items.RAINBOW_BOOTS.buildStack(null));
        for (int i = 0; i < 8; i++) rainbowBoots.addIngredient(new CIRecipeChoice(rainbowDyeStack));
        rainbowBoots.addIngredient(Material.LEATHER_BOOTS);

        RecipeManager.registerRecipe(rainbowHelmet);
        RecipeManager.registerRecipe(rainbowChest);
        RecipeManager.registerRecipe(rainbowLegs);
        RecipeManager.registerRecipe(rainbowBoots);

        //Define new advanced recipe with the key `gradient_dye` and set the output to a basic gradient dye
        new RecipeBuilder.AdvancedShape("gradient_dye", Items.GRADIENT_DYE.buildStack(null))
                .setShape("1X2").setIngredient('X', rainbowDyeStack) //Set the shape
                .setIngredientGroup('1', RecipeGroup.GROUP_1) //Make the left group 1
                .setIngredientGroup('2', RecipeGroup.GROUP_2) //Make the right side group 2
                .setGroupItems(RecipeGroup.GROUP_1, ItemGroups.DYES) //Set group 1 to accept all dyes
                .setGroupItems(RecipeGroup.GROUP_2, ItemGroups.DYES) //Same again

                //The code to run when the item is avaliable to preview in the crafting table
                .previewModifier(((itemStack, map, recipeContext) -> { //The map contains the itemstacks for each group we made
                    if (map.get(GROUP_1).getType() == map.get(GROUP_2).getType()) return null;   //No gradients for the same color
                    GradientDye.setColor(itemStack, map.get(RecipeGroup.GROUP_1).getType(), true); //Set the gradient parameters
                    GradientDye.setColor(itemStack, map.get(RecipeGroup.GROUP_2).getType(), false);//to the colors in the crafting
                    return Items.GRADIENT_DYE.updateStack(itemStack, recipeContext.getPlayer());       //table

                //The code to run when we actually craft it. Same thing again
                })).craftModifier(((itemStack, map, recipeContext) -> {
                    if (map.get(GROUP_1).getType() == map.get(GROUP_2).getType()) return null;
                    GradientDye.setColor(itemStack, map.get(RecipeGroup.GROUP_1).getType(), true);
                    GradientDye.setColor(itemStack, map.get(RecipeGroup.GROUP_2).getType(), false);
                    return Items.GRADIENT_DYE.updateStack(itemStack, recipeContext.getPlayer());
                })).buildRegister(); //Build and register the recipe

        new RecipeBuilder.AdvancedShape("revive_kit", Items.REVIVE_ANIMAL_KIT.buildStack(null))
                .setShape("OXO", "X@X", "OXO").setIngredientGroup('@', GROUP_1)
                .setIngredient('O', Material.DARK_OAK_LOG)
                .setIngredient('X', Items.SOUL_GOLD.buildStack(null))
                .setGroupItems(GROUP_1, Items.ANIMAL_SOUL.buildStack(null))
                .previewModifier((itemstack, map, context) -> Items.REVIVE_ANIMAL_KIT.fromSoul(map.get(GROUP_1)))
                .craftModifier((itemstack, map, context) -> Items.REVIVE_ANIMAL_KIT.fromSoul(map.get(GROUP_1))).buildRegister();
    }
}
