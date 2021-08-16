package fr.quentin.searchquickshop;

import co.aikar.commands.BukkitCommandManager;
import fr.quentin.searchquickshop.Commands.AutoCompletion;
import fr.quentin.searchquickshop.Commands.ShopCommands;
import fr.quentin.searchquickshop.Configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class SearchQuickShop extends JavaPlugin {

    public static SearchQuickShop plugin;

    private static Config config;

    @Override
    public void onEnable() {
        plugin = this;

        config = new Config(plugin);
        BukkitCommandManager bukkitCommandManager = new BukkitCommandManager(plugin);
        bukkitCommandManager.registerCommand(new ShopCommands());
        AutoCompletion.offlinePlayers(bukkitCommandManager);
        AutoCompletion.item(bukkitCommandManager);
    }

    public SearchQuickShop getPlugin() {
        return plugin;
    }

    public static Config getPluginConfig() { return config; }

    @Override
    public void onDisable() {}

    public static void log(Level level, String message) {
        Bukkit.getLogger().log(level, String.format("[searchQuickShop] %s", message));
    }
}
