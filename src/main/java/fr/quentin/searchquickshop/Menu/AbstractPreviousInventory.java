package fr.quentin.searchquickshop.Menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryProvider;
import fr.quentin.searchquickshop.Menu.utils.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class AbstractPreviousInventory implements InventoryProvider, InventoryBuilderProviderInterface {

    private final InventoryProvider previousInventory;

    public AbstractPreviousInventory(InventoryProvider previousInventory) {
        this.previousInventory = previousInventory;
    }

    public InventoryProvider getPreviousInventory() {
        return previousInventory;
    }

    public boolean hasPreviousInventory() {
        return previousInventory != null;
    }

    public ClickableItem getItemPreviousInventory() {

        if (!hasPreviousInventory()) {
            throw new NullPointerException("Can't an item to go back to previous Inventory if there is no previous inventory.");
        }

        return ItemStackUtils.clickableItemWithItem(
                e -> ((InventoryBuilderProviderInterface) getPreviousInventory()).getBuilder(previousInventory).open((Player) e.getWhoClicked()),
                Material.ARROW,
                "Retour"
        );
    }
}
