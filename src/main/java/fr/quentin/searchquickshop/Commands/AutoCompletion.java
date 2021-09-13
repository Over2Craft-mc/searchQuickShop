package fr.quentin.searchquickshop.Commands;

import co.aikar.commands.BukkitCommandManager;
import fr.quentin.searchquickshop.ItemTranslation.FrenchTranslation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AutoCompletion {

    public static void offlinePlayers(BukkitCommandManager bukkitCommandManager) {
        bukkitCommandManager.getCommandCompletions().registerCompletion("offlineplayers",
                c -> Arrays.stream(Bukkit.getOfflinePlayers()).
                        limit(20)
                        .map(OfflinePlayer::getName)
                        .filter(Objects::nonNull)
                        .filter(name -> name.contains(c.getInput()))
                        .collect(Collectors.toList()));
    }

    public static void item(BukkitCommandManager bukkitCommandManager) {
        bukkitCommandManager.getCommandCompletions().registerCompletion("items", c -> {
            List<String> suggest = new ArrayList<>();

            suggest.addAll(Arrays.stream(Material.values()).map(Enum::toString).collect(Collectors.toList()));
            suggest.addAll(FrenchTranslation.getTranslation().values());

            suggest = suggest.stream().
                    filter(value -> value.toUpperCase().contains(c.getInput().toUpperCase()))
                    .limit(10)
                    .collect(Collectors.toList());

            return suggest;
        });

    }

}
