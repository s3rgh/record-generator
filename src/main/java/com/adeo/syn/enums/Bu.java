package com.adeo.syn.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Getter
public enum Bu {
    PASQ("PAS", List.of(Language.en)),
    LMFR("LMFR", List.of(Language.fr)),
    LMRU("LMRU", List.of(Language.ru, Language.kk, Language.en)),
    LMES("LMES", List.of(Language.es)),
    LMPT("LMPT", List.of(Language.pt)),
    LMIT("LMIT", List.of(Language.it)),
    LMPL("LMPL", List.of(Language.pl));

    private final String buCode;
    private final List<Language> acceptedLanguages;

    public String getBuLanguageString() {
        return getAcceptedLanguages().get(0).name();
    }

    public Language getBuLanguage() {
        return getAcceptedLanguages().get(0);
    }

    public static Bu getRandomBU() {
        return Bu.values()[new Random().nextInt(Bu.values().length)];
    }

    @Override
    public String toString() {
        return getBuCode();
    }
}
