package com.newspaper.newspaper.model;

public enum Category {
    POLITICA,
    DEPORTES,
    STARTUPS,
    CULTURA,
    VIDEOJUEGOS;

    public static Category fromString(String categoryName) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(categoryName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category with name " + categoryName);
    }
}
