package com.stackroute.orderservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Status {
    PENDING("Pending"),
    DONE("Done");

    private final String status;

    public static boolean isInEnum(String value) {
        return Arrays.stream(Status.values()).anyMatch(e -> e.getStatus().equals(value));
    }
}
