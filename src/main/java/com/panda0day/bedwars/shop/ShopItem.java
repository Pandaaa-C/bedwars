package com.panda0day.bedwars.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItem {
    private final String name;
    private final int amount;
    private final ItemStack item;
    private final int price;
    private final Material currency;
    private final int categoryId;

    public ShopItem(String name, int amount, ItemStack item, int price, Material currency, int categoryId) {
        this.name = name;
        this.amount = amount;
        this.item = item;
        this.price = price;
        this.currency = currency;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public Material getCurrency() {
        return currency;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
