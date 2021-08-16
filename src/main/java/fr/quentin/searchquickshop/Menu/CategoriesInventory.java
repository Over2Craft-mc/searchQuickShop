package fr.quentin.searchquickshop.Menu;

import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.quentin.searchquickshop.Configuration.Config;
import fr.quentin.searchquickshop.Menu.utils.ItemStackUtils;
import fr.quentin.searchquickshop.Shop.ShopFilters;
import org.bukkit.entity.Player;


public class CategoriesInventory implements InventoryProvider {

    private Config config;

    public CategoriesInventory(Config config) {
        this.config = config;
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        config.getCategories().forEach(category -> {
            contents.set(
                    category.getPositionRow(), category.getPositionColumn(),
                    ItemStackUtils.clickableItemWithItem(
                            e -> SmartInv.getItemInventory(ShopFilters.allItems().filterCategory(category.getName())).open(player),
                            category.getIcon(),
                            category.getName(),
                            category.getLore()
                    ));
        });

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
