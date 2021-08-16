package fr.quentin.searchquickshop.Configuration;


import fr.quentin.searchquickshop.Configuration.Models.Category;
import fr.quentin.searchquickshop.SearchQuickShop;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.maxgamer.quickshop.shop.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Config {

    private static final String KEY_CATEGORIES = "categories";
    private static final String KEY_LORE_BUY = "lore_buy";
    private static final String KEY_LORE_SELL = "lore_sell";

    private ArrayList<Category> categories;
    private ArrayList<String> loreBuy;
    private ArrayList<String> loreSell;

    public String MESSAGE_COMMAND_SEE_ALL_SHOP;
    public String MESSAGE_PLAYER_NOT_EXITS;

    public Config(SearchQuickShop plugin) {
        plugin.saveDefaultConfig();
        this.load(plugin);
    }

    private void load(SearchQuickShop plugin) {
        categories = parseCategories(plugin.getConfig());
        System.out.println(categories.size());

        loreBuy = (ArrayList<String>) plugin.getConfig().getStringList(KEY_LORE_BUY);
        loreSell = (ArrayList<String>) plugin.getConfig().getStringList(KEY_LORE_SELL);

        MESSAGE_COMMAND_SEE_ALL_SHOP = plugin.getConfig().getString("messages.command_see_all_shops");
        MESSAGE_PLAYER_NOT_EXITS = plugin.getConfig().getString("messages.player_does_not_exists");
    }

    public void reload(SearchQuickShop plugin) {
        plugin.reloadConfig();
        load(plugin);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    private List<String> getLore(Shop shop, ArrayList<String> lore) {

        List<String> loreToReturn = new ArrayList<>();

        for (String line : lore) {

            String str = line.replace("%remainingStock%", String.valueOf(shop.getRemainingStock()))
                            .replace("%seller%", shop.ownerName())
                            .replace("%price%", String.valueOf(shop.getPrice()))
                            .replace("&", "§");

            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                str = PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(shop.getOwner()), str);
            }

            loreToReturn.add(str);
        }

        return loreToReturn;
    }

    private List<String> getBuyLore(Shop shop) {
        return getLore(shop, loreBuy);
    }

    private List<String> getSellLore(Shop shop) {
        return getLore(shop, loreSell);
    }

    public List<String> getLore(Shop shop) {
        return shop.isBuying() ? getBuyLore(shop) : getSellLore(shop);
    }

    private static ArrayList<Category> parseCategories(FileConfiguration fileConfiguration) {

        ArrayList<Category> categories = new ArrayList<>();

        SearchQuickShop.log(Level.INFO, "Loading item categories....");
        if (fileConfiguration.contains(KEY_CATEGORIES)) {
            fileConfiguration.getConfigurationSection(KEY_CATEGORIES).getKeys(false).forEach(name -> {
                System.out.println(name);
                Category category = Category.parseFromConfig(fileConfiguration.getConfigurationSection(String.format("%s.%s", KEY_CATEGORIES, name)));
                System.out.println(category);
                if (category != null) {
                    categories.add(category);
                }
            });
        }
        SearchQuickShop.log(Level.INFO, "Categories loaded.");

        return categories;
    }

}