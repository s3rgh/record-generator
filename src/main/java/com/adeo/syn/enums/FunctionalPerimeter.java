package com.adeo.syn.enums;

import java.util.Map;

public enum FunctionalPerimeter {
    COMMON(Map.of(Language.en, "Common", Language.ru, "Общие" )),
    DESCRIPTIVE(Map.of(Language.en, "Descriptive", Language.ru, "Описательные")),
    LOGISTIC(Map.of(Language.en, "Logistic", Language.ru, "Логистические")),
    QUALITY(Map.of(Language.en, "Quality", Language.ru, "Качество"));

    private final Map<Language, String> sheetName;

    FunctionalPerimeter(Map<Language, String> sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName(Language language) {
        return sheetName.get(language);
    }
}
