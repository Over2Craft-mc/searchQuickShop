package fr.quentin.searchquickshop.Menu;

import fr.minuskube.inv.SmartInventory;
import fr.quentin.searchquickshop.Configuration.Config;
import fr.quentin.searchquickshop.Shop.ShopFilters;

public class SmartInv {

    public static SmartInventory getItemInventory(ShopFilters shopFilters) {
        return SmartInventory.builder()
                .provider(new ShopsInventory(shopFilters))
                .size(6, 9)
                .title("Shops")
                .build();
    }

    public static SmartInventory getCategoryInventory(Config config) {
        return SmartInventory.builder()
                .provider(new CategoriesInventory(config))
                .size(6, 9)
                .title("Shops")
                .build();
    }


}
