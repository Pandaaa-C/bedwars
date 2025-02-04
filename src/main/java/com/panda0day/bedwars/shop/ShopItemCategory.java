package com.panda0day.bedwars.shop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItemCategory {
    private final String categoryName;
    private final ItemStack categoryItem;
    private final List<String> categoryDescription;

    public ShopItemCategory(String categoryName, ItemStack categoryItem, List<String> categoryDescription) {
        this.categoryName = categoryName;
        this.categoryItem = categoryItem;
        this.categoryDescription = categoryDescription;
    }

    public ItemStack getCategoryItem() {
        return categoryItem;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<String> getCategoryDescription() {
        return categoryDescription;
    }
}
