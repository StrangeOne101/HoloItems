package com.strangeone101.holoitems;

import org.bukkit.NamespacedKey;

public class Keys {

    public Keys() {
        keys = this;
    }

    //Rushia Shield

    /** The key for how many mobs are stored. Should be <strong>INT</strong> */
    public final NamespacedKey RUSHIA_SHIELD_COUNT = new NamespacedKey(HoloItemsPlugin.INSTANCE, "rushia_shield_count");
    /** The key for the types of mobs. Should be <strong>STRING</strong> */
    public final NamespacedKey RUSHIA_SHIELD_MOBS = new NamespacedKey(HoloItemsPlugin.INSTANCE, "rushia_shield_mobs");

    /** The key for the types of mobs. Should be <strong>INT</strong> */
    public final NamespacedKey BERRY_TRIDENT_THROWN = new NamespacedKey(HoloItemsPlugin.INSTANCE, "berry_trident");

    /** How many clicks until death. Should be <strong>INT</strong> */
    public final NamespacedKey RUSSIAN_ROULETTE = new NamespacedKey(HoloItemsPlugin.INSTANCE, "russian_roulette");

    public final NamespacedKey DEATH_LOC = new NamespacedKey(HoloItemsPlugin.INSTANCE, "death_loc");
    public final NamespacedKey DEATH_WORLD = new NamespacedKey(HoloItemsPlugin.INSTANCE, "death_world");

    public final NamespacedKey TEMP_ENTITY = new NamespacedKey(HoloItemsPlugin.INSTANCE, "temp");

    public final NamespacedKey ENTITY_SOUL = new NamespacedKey(HoloItemsPlugin.INSTANCE, "entity");

    private static Keys keys;

    public static Keys getKeys() {
        return keys;
    }
}
