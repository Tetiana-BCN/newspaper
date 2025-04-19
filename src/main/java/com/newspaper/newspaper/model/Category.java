
package com.newspaper.newspaper.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    POLITICA(1),
    DEPORTES(2),
    STARTUPS(3),
    CULTURA(4),
    VIDEOJUEGOS(5);

    private final int id;

    Category(int id) {
        this.id = id;
    }

    @JsonValue
    public String getName() {
        return name();
    }

    public int getId() {
        return id;
    }

    public static Category fromId(Integer id) {
        if (id == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Categoría con ID %d no encontrada", id)));
    }

    public static List<Category> getAllCategories() {
        return Arrays.asList(values());
    }

    public static boolean isValidId(int id) {
        return Arrays.stream(values())
                .anyMatch(c -> c.getId() == id);
    }
}