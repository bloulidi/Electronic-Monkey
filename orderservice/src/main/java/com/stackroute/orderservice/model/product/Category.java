package com.stackroute.orderservice.model.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Category {
    PHONES("Phones"),
    COMPUTERS("Computers"),
    ACCESSORIES("Accessories");

    private final String category;

    public static boolean isInEnum(String value) {
        return Arrays.stream(Category.values()).anyMatch(e -> e.getCategory().equals(value));
    }
}
