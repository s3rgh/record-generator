package com.adeo.syn.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public enum Department {
    CONSTRUCTION(1),
    JOINERY(2),
    ELECTRIC(3),
    TOOLS(4),
    FLOOR_COVERINGS(5),
    TILE(6),
    PLUMBING(7),
    WATER_SUPPLY(8),
    GARDEN(9),
    IRON_MONGERY(10),
    PAINTS(11),
    DECOR(12),
    ILLUMINATION(13),
    STORAGE(14),
    KITCHENS(15);

    private final int id;
}
