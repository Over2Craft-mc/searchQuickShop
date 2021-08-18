package fr.quentin.searchquickshop.Menu;

import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryProvider;

public interface InventoryBuilderProviderInterface {

    SmartInventory getBuilder(InventoryProvider inventoryProvider);

}
