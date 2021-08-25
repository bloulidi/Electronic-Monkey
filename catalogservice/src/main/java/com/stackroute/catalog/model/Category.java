package com.stackroute.catalog.model;

import lombok.Getter;

@Getter
public enum Category {
    PHONES("Phones"),
    COMPUTERS("Computers"),
    ACCESSORIES("Accessories");

    private final String category;

    Category(String category) {
        this.category = category;
    }
}
