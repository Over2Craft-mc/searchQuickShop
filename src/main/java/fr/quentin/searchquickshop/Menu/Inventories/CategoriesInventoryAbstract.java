package fr.quentin.searchquickshop.Menu.Inventories;

import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.quentin.searchquickshop.Configuration.Config;
import fr.quentin.searchquickshop.Menu.AbstractPreviousInventory;
import fr.quentin.searchquickshop.Menu.InventoriesBuilder;
import fr.quentin.searchquickshop.Menu.InventoryBuilderProviderInterface;
import fr.quentin.searchquickshop.Menu.utils.ItemStackUtils;
import fr.quentin.searchquickshop.Shop.ShopFilters;
import org.bukkit.entity.Player;


public class CategoriesInventoryAbstract extends AbstractPreviousInventory implements InventoryProvider, InventoryBuilderProviderInterface {

    private final Config config;

    public CategoriesInventoryAbstract(Config config) {
        super(null);
        this.config = config;
    }

    public CategoriesInventoryAbstract(Config config, InventoryProvider previousInventory) {
        super(previousInventory);
        this.config = config;
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        config.getCategories().forEach(category -> contents.set(
                category.getPositionRow(), category.getPositionColumn(),
                ItemStackUtils.clickableItemWithItem(
                        e -> InventoriesBuilder.getItemInventory(ShopFilters.allItems().filterCategory(category.getName()), this).open(player),
                        category.getIcon(),
                        category.getName(),
                        category.getLore()
                )));

        if (this.hasPreviousInventory()) {
            contents.set(5, 0, this.getItemPreviousInventory());
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    @Override
    public SmartInventory getBuilder(InventoryProvider inventoryProvider) {
        return SmartInventory.builder()
                .provider(inventoryProvider)
                .size(6, 9)
                .title("Shops")
                .build();
    }
}
