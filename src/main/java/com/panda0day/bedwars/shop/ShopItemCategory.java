package com.panda0day.bedwars.shop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItemCategory {
    private final int categoryId;
    private final String categoryName;
    private final ItemStack categoryItem;
    private final List<String> categoryDescription;

    public ShopItemCategory(int categoryId, String categoryName, ItemStack categoryItem, List<String> categoryDescription) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryItem = categoryItem;
        this.categoryDescription = categoryDescription;
    }

    public int getCategoryId() {
        return categoryId;
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
