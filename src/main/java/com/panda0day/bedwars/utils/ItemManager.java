package com.panda0day.bedwars.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemManager {
    public ItemManager() {}

    public static ItemStack createItem(Material material, String name, List<String> lore, List<Enchantment> enchantments) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null && !name.isEmpty()) {
                meta.setDisplayName(name);
            }

            if (lore != null && !lore.isEmpty()) {
                meta.setLore(lore);
            }

            if (enchantments != null && !enchantments.isEmpty()) {
                for (Enchantment enchantment : enchantments) {
                    meta.addEnchant(enchantment, 1, true);
                }
            }

            item.setItemMeta(meta);
        }

        return item;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore) {
        return createItem(material, name, lore, null);
    }

    public static ItemStack createItem(Material material, String name) {
        return createItem(material, name, null, null);
    }
}
