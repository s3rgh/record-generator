package com.adeo.syn.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum State {
    ON_VALIDATION(1),
    READY_TO_SEND(2),
    CORRECTION_REQUIRED(3),
    DATA_CORRECTION(4),
    DATA_STRUCTURE_MAPPING(5),
    DATA_STRUCTURE_PARSING(6),
    PRODUCT_MATCHING(7),
    SELECTED_FOR_LISTING(8),
    PRODUCT_UPLOADED(9),
    APPROVING_PRODUCT(10),
    PRODUCT_MATCHED(11),
    CORRECTIONS_DONE_PM(12),
    PRODUCT_REJECTED(13),
    MATCHING_CONFIRMED(14),
    DATA_STRUCTURE_MAPPED(15),
    UNSUCCESSFUL_TRANSFORMATION(16),
    DATA_VALIDATION_REQUIRED(17),
    DATA_VALIDATION(20),
    DATA_CORRECTION_MDM(21),
    CORRECTIONS_DONE_MDM(22),
    MANUAL_MATCHING(23),
    MERGING(25),
    PRODUCT_VALIDATED(30);

    private final int id;

    public static State fromId(int id) {
        return Stream.of(State.values()).filter(state -> state.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalStateException("Something went wrong with state id = " + id));
    }

    public static State getNextStateId(State state) {
        int size = State.values().length;
        int current = state.ordinal();
        if (current + 1 == size) {
            return State.values()[0];
        }
        return State.values()[current + 1];
    }

    public static State getRandomState() {
        State[] values = State.values();
        return values[new Random().nextInt(values.length)];
    }

    public Integer getValue() {
        return getId();
    }

    public Object ofType(String name) {
        return fromId(Integer.parseInt(name));
    }
}
