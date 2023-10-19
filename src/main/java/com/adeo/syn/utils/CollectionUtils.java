package com.adeo.syn.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class CollectionUtils {
    public static <T> T getRandomItem(List<T> items) {
        return items.get(ThreadLocalRandom.current().nextInt(items.size()));
    }
}
