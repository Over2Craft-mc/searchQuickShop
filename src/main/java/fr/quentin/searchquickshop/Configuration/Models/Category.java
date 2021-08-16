package fr.quentin.searchquickshop.Configuration.Models;

import fr.quentin.searchquickshop.SearchQuickShop;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Category {

    private static final String MATERIAL_ICON = "material_icon";
    private static final String ITEMS = "items";
    private static final String POSITION_ROW = "position_row";
    private static final String POSITION_COLUMN = "position_column";
    private static final String LORE = "lore";

    private final String name;
    private final Material icon;
    private final List<Material> items;
    private final Integer positionRow;
    private final Integer positionColumn;
    private final List<String> lore;

    public Category(String name, Material icon, List<Material> items, Integer positionRow, Integer positionColumn, List<String> lore) {

        assert name != null;
        assert icon != null;
        assert items != null;
        assert positionColumn != null;
        assert positionRow != null;
        assert lore != null;

        this.name = name;
        this.icon = icon;
        this.items = items;
        this.positionRow = positionRow;
        this.positionColumn = positionColumn;
        this.lore = lore;
    }

    public static Category parseFromConfig(ConfigurationSection configurationSection) {

        if (!configurationSection.contains(MATERIAL_ICON)) {
            SearchQuickShop.log(Level.WARNING, String.format("There is no %s for configuration section %s", MATERIAL_ICON, configurationSection.getName()));
            return null;
        }

        if (Material.matchMaterial(configurationSection.getString(MATERIAL_ICON)) == null) {
            SearchQuickShop.log(Level.WARNING, String.format("%s does not match an existing material for configuration section %s. See https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html", MATERIAL_ICON, configurationSection.getName()));
            return null;
        }

        if (configurationSection.getList(ITEMS) == null) {
            SearchQuickShop.log(Level.WARNING, String.format("There is no %s for configuration section %s", ITEMS, configurationSection.getName()));
            return null;
        }

        ArrayList<Material> items = new ArrayList<>();

        for (Object item : configurationSection.getList(ITEMS)) {

            if (Material.matchMaterial((String) item) == null) {
                SearchQuickShop.log(Level.WARNING, String.format("%s does not match an existing material for configuration section %s. See https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html", ITEMS, configurationSection.getName()));
                return null;
            }

            items.add(Material.matchMaterial((String) item));
        }

        if (!configurationSection.contains(POSITION_ROW) || configurationSection.getInt(POSITION_ROW) < 0 || configurationSection.getInt(POSITION_ROW) > 5) {
            SearchQuickShop.log(Level.WARNING, String.format("%s must be between 0 and 5 for %s.", POSITION_ROW, configurationSection.getName()));
            return null;
        }

        if (!configurationSection.contains(POSITION_COLUMN) || configurationSection.getInt(POSITION_COLUMN) < 0 || configurationSection.getInt(POSITION_COLUMN) > 8) {
            SearchQuickShop.log(Level.WARNING, String.format("%s must be between 0 and 8 for %s.", POSITION_COLUMN, configurationSection.getName()));
            return null;
        }

        configurationSection.getStringList(LORE);
        return new Category(
                configurationSection.getName(),
                Material.matchMaterial(configurationSection.getString(MATERIAL_ICON)),
                items,
                configurationSection.getInt(POSITION_ROW),
                configurationSection.getInt(POSITION_COLUMN),
                configurationSection.getStringList(LORE)
        );
    }

    public String getName() {
        return name.replace("&", "§");
    }

    public Material getIcon() {
        return icon;
    }

    public List<Material> getItems() {
        return items;
    }

    public Integer getPositionRow() { return positionRow; }

    public Integer getPositionColumn() { return positionColumn; }

    public List<String> getLore() {

        ArrayList<String> lore = new ArrayList<>();

        this.lore.forEach(line -> {
            lore.add(line.replace("&", "§"));
        });

        return lore;
    }
}
