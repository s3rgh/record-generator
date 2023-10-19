package com.adeo.syn.enums;

import java.util.Locale;
import java.util.Random;

public enum Language {
    fr, en, es, pt, ca, ru, kk, it, pl;

    public static Language getRandomLanguage() {
        Language[] values = Language.values();
        return values[new Random().nextInt(values.length)];
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(name());
    }
}
