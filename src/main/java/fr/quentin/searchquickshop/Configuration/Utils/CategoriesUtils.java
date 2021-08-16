package fr.quentin.searchquickshop.Configuration.Utils;

import fr.quentin.searchquickshop.Configuration.Config;
import fr.quentin.searchquickshop.Configuration.Models.Category;

public class CategoriesUtils {

    public static Category getCategoryFromName(Config config, String name) {
        for (Category category : config.getCategories()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }

        return null;
    }

}
