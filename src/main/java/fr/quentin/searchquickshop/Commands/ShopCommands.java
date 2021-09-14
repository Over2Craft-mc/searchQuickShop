package fr.quentin.searchquickshop.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.quentin.searchquickshop.Menu.InventoriesBuilder;
import fr.quentin.searchquickshop.SearchQuickShop;
import fr.quentin.searchquickshop.Shop.ShopFilters;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("shop-search|slist")
public class ShopCommands extends BaseCommand {

    @Default
    @Description("Voir les catégories")
    @Syntax("/shop-search")
    @CommandPermission("searchquickshop.command.default")
    public static void listAll(Player player) {
        InventoriesBuilder.getCategoryInventory(SearchQuickShop.getPluginConfig()).open(player);
    }

    @Subcommand("joueur")
    @CommandCompletion("@offlineplayers")
    @Syntax("/shop-search joueur <nom du joueur>")
    @Description("Voir les shop d'un joueur")
    @CommandPermission("searchquickshop.command.player")
    public static void searchItemsForAPlayer(Player player, String[] args) {

        if (args.length != 1) {
            player.sendMessage(SearchQuickShop.getPluginConfig().MESSAGE_PLAYER_NOT_EXITS);
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

        if (!offlinePlayer.hasPlayedBefore()) {
            player.sendMessage(SearchQuickShop.getPluginConfig().MESSAGE_PLAYER_NOT_EXITS);
            return;
        }

        InventoriesBuilder.getItemInventory(ShopFilters.allItems().filterByPlayer(offlinePlayer.getUniqueId())).open(player);
    }

    @Subcommand("item")
    @CommandCompletion("@items")
    @Syntax("/shop-search item <nom de l'item>")
    @Description("Voir les shops pour un type d'item")
    @CommandPermission("searchquickshop.command.item")
    public static void searchItem(Player player, String[] args) {

        if (args.length == 1) {
            InventoriesBuilder.getItemInventory(ShopFilters.allItems().filterLikeItem(args[0])).open(player);
            return;
        }
        listAll(player);
    }

    @Subcommand("reload")
    @Syntax("/shop-search reload")
    @CommandPermission("searchquickshop.command.reload")
    public static void searchItem(Player player) {
        SearchQuickShop.getPluginConfig().reload(SearchQuickShop.plugin);
        player.sendMessage("Plugin reloaded");
    }
}
