package com.panda0day.bedwars.shop;

import com.panda0day.bedwars.Main;
import com.panda0day.bedwars.utils.ItemManager;
import org.bukkit.Material;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopManager {
    private final List<ShopItemCategory> shopItemCategories;
    private final List<ShopItem> shopItems;

    public ShopManager() {
        this.shopItems = new ArrayList<>();
        this.shopItemCategories = new ArrayList<>();
    }

    public void loadShopCategories() {
        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM shop_category");
            while (resultSet.next()) {
                String categoryName = resultSet.getString("name");
                String categoryItemId = resultSet.getString("item_id");
                String[] categoryDescription = resultSet.getString("description").split(";");

                shopItemCategories.add(new ShopItemCategory(
                        categoryName,
                        new ItemManager(Material.getMaterial(categoryItemId))
                                .setDisplayName(categoryName)
                                .create(),
                        Arrays.stream(categoryDescription).toList()
                ));
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }
    }

    public void loadShopItems() {
        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM shop_items");
            while (resultSet.next()) {
                String displayName = resultSet.getString("item_name");
                String itemID = resultSet.getString("item_id");
                int amount = resultSet.getInt("amount");
                int price = resultSet.getInt("price");
                String currencyId = resultSet.getString("currency_id");
                int categoryID = resultSet.getInt("category_id");

                shopItems.add(new ShopItem(
                        displayName,
                        amount,
                        new ItemManager(Material.getMaterial(itemID))
                                .setDisplayName(displayName)
                                .create(),
                        price,
                        Material.getMaterial(currencyId),
                        categoryID
                ));
            }
        } catch (Exception exception) {
            Main.getInstance().getLogger().info(exception.getMessage());
        }
    }

    public List<ShopItemCategory> getShopItemCategories() {
        return shopItemCategories;
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public static void createDefaultTables() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS shop_items (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     item_name VARCHAR(255) NOT NULL UNIQUE,
                     item_id VARCHAR(255) NOT NULL,
                     currency_id VARCHAR(255) NOT NULL,
                     amount INT(32) NOT NULL,
                     price INT(32) NOT NULL
                 )
                """);
    }
}
