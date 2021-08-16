package fr.quentin.searchquickshop.Menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import fr.quentin.searchquickshop.Configuration.Config;
import fr.quentin.searchquickshop.Menu.utils.ItemStackUtils;
import fr.quentin.searchquickshop.SearchQuickShop;
import fr.quentin.searchquickshop.Shop.ShopFilters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.maxgamer.quickshop.shop.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopsInventory implements InventoryProvider {

    private final ShopFilters filters;

    private final Config config;

    public ShopsInventory(ShopFilters filters) {
        this.filters = filters;
        this.config = SearchQuickShop.getPluginConfig();
    }

    public ClickableItem[] getIems() {

        ClickableItem[] items = new ClickableItem[filters.getShops().size()];
        int i = 0;
        for (Shop shop : filters.getShops()) {

            ItemStack item = new ItemStack(shop.getItem());

            ItemMeta im = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
            List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
            lore.addAll(config.getLore(shop));
            im.setLore(lore);
            item.setItemMeta(im);

            items[i] =  ClickableItem.of(item, e -> e.getWhoClicked().teleport(shop.getLocation()));

            i++;
        }

        return items;

    }

    @Override
    public void init(Player player, InventoryContents contents) {

        Pagination pagination = contents.pagination();

        pagination.setItems(getIems());
        pagination.setItemsPerPage(45);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        contents.set(5, 3, ClickableItem.of(ItemStackUtils.createItem(Material.ARROW, "Page précédente"),
                e -> SmartInv.getItemInventory(filters).open(player, pagination.previous().getPage())));

        contents.set(5, 4, ClickableItem.of(ItemStackUtils.createItem(Material.PAPER, "Voir les catégorie"),
                e -> SmartInv.getCategoryInventory(config).open(player)));

        contents.set(5, 5, ClickableItem.of(ItemStackUtils.createItem(Material.ARROW, "Page suivante"),
                e -> SmartInv.getItemInventory(filters).open(player, pagination.next().getPage())));

    }

    @Override
    public void update(Player player, InventoryContents contents) {}



}