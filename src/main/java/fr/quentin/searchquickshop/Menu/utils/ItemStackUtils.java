package fr.quentin.searchquickshop.Menu.utils;

import fr.minuskube.inv.ClickableItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemStackUtils {
    public static ItemStack createItem(final Material material, final String name, final String... lore) {
        return createItem(material, name, Arrays.asList(lore));
    }

    public static ItemStack createItem(final Material material, final String name, List<String> lore) {

        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§b§l" + name);

        ArrayList<String> loreA = new ArrayList<>();

        for (String str:
                lore) {
            loreA.add("§a" + str);
        }

        meta.setLore(loreA);

        item.setItemMeta(meta);

        return item;
    }

    public static ClickableItem clickableItemWithItem(Consumer<InventoryClickEvent> consumer, Material material, final String name, final String... lore) {
        return clickableItemWithItem(consumer, material, name, Arrays.asList(lore));
    }

    public static ClickableItem clickableItemWithItem(Consumer<InventoryClickEvent> consumer, Material material, final String name, final List<String> lore) {
        return ClickableItem.of(ItemStackUtils.createItem(material, name, lore), consumer);
    }
}
