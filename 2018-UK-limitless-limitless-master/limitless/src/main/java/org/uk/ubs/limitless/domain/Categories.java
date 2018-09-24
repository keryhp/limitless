package org.uk.ubs.limitless.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Categories {

    private static final Map<Integer, ArrayList<String>> categoriesList = new HashMap<Integer, ArrayList<String>>();

    static {
        ArrayList<String> users = new ArrayList<>();
        users.add("");
        categoriesList.put(1, users);
        users.clear();

        users.add("");
        categoriesList.put(2, users);
        users.clear();

        users.add("");
        categoriesList.put(3, users);
        users.clear();
    }

    public static Integer getFeedCategoryByUser(String user) {
        for (Map.Entry<Integer, ArrayList<String>> entry : categoriesList.entrySet()) {
            if (entry.getValue().contains(user)) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
