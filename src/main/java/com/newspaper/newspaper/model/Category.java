package com.newspaper.newspaper.model;

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

    public static Category fromId(int id) {
        for (Category c : Category.values()) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoría inválida: " + id);
    }
}
