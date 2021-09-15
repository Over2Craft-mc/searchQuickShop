package fr.quentin.searchquickshop.Menu;

import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryProvider;
import fr.quentin.searchquickshop.Configuration.Config;
import fr.quentin.searchquickshop.Menu.Inventories.CategoriesInventory;
import fr.quentin.searchquickshop.Menu.Inventories.ShopsInventory;
import fr.quentin.searchquickshop.Shop.ShopFilters;

public class InventoriesBuilder {

    public static SmartInventory getItemInventory(ShopFilters shopFilters, InventoryProvider previousInventory) {
        InventoryBuilderProviderInterface inventoryProvider = new ShopsInventory(shopFilters, previousInventory);
        return inventoryProvider.getBuilder((InventoryProvider) inventoryProvider);
    }

    public static SmartInventory getItemInventory(ShopFilters shopFilters) {
        return getItemInventory(shopFilters, null);
    }

    public static SmartInventory getCategoryInventory(Config config, InventoryProvider previousInventory) {
        InventoryBuilderProviderInterface inventoryProvider = new CategoriesInventory(config, previousInventory);
        return inventoryProvider.getBuilder((InventoryProvider) inventoryProvider);
    }

    public static SmartInventory getCategoryInventory(Config config) {
        return getCategoryInventory(config, null);
    }


}
