package fr.quentin.searchquickshop.Shop;

import fr.quentin.searchquickshop.Configuration.Models.Category;
import fr.quentin.searchquickshop.Configuration.Utils.CategoriesUtils;
import fr.quentin.searchquickshop.ItemTranslation.FrenchTranslation;
import fr.quentin.searchquickshop.SearchQuickShop;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopFilters {

    private List<Shop> shops;

    public ShopFilters(List<Shop> shops) {
        this.shops = new ArrayList<>(shops);
    }

    public static ShopFilters allItems() {
        return new ShopFilters(
                QuickShopAPI.getShopAPI().getAllShops()
                        .stream()
                        .filter(shop -> shop.getRemainingStock() > 0)
                        .collect(Collectors.toList())
        );
    }

    public ShopFilters filterByPlayer(UUID uuid) {
        List<Shop> shops = new ArrayList<>();

        for (Shop shop : this.shops ) {
            if (shop.getOwner().equals(uuid)) {
                shops.add(shop);
            }
        }

        return new ShopFilters(shops);
    }

    public ShopFilters filterLikeItem(String itemIdToCompare) {


        List<Shop> shops = new ArrayList<>();

        this.shops.forEach((shop) -> {

            if (
                    shop.getItem().getType().toString().toLowerCase().contains(itemIdToCompare.toLowerCase())
                            || FrenchTranslation.getTranslation().containsKey(shop.getItem().getType().toString().toLowerCase())
                            && FrenchTranslation.getTranslation().get(shop.getItem().getType().toString().toLowerCase())
                                .toLowerCase().contains(itemIdToCompare.toLowerCase())

            ) {
                shops.add(shop);
            }

        });

        return new ShopFilters(shops);
    }

    public ShopFilters filterCategory(String categoryName) {

        List<Shop> shops = new ArrayList<>();

        Category category = CategoriesUtils.getCategoryFromName(SearchQuickShop.getPluginConfig(), categoryName);

        for (Shop shop : this.shops ) {
            category.getItems().forEach(material -> {
                if (shop.getItem().getType() == material) {
                    shops.add(shop);
                }
            });
        }

        return new ShopFilters(shops);
    }

    public List<Shop> getShops() {
        return shops;
    }

}
