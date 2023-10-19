package com.adeo.syn.utils;

import lombok.experimental.UtilityClass;
import net.datafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@UtilityClass
public final class Random {
    public static final Faker FAKER = new Faker();
    private static final String MODELS_FILE_PATH = "src/test/resources/csv/model.csv";
    private static final String PARSING_EXCEPTION_MESSAGE = "File %s couldn't be parsed";

    public static String getRandomValueId() {
        return FAKER.bothify("?#?#?#?#?#?#_autotest");
    }

    public static String getRandomAttributeId() {
        return FAKER.numerify("ATT_########_autotest");
    }

    public static String getRandomAdeoKey() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static long getRandomId() {
        return System.currentTimeMillis();
    }

    public static long getRandomSupplierId() {
        return Long.parseLong(FAKER.code().gtin8());
    }

    public static String getRandomProductName() {
        return FAKER.food().vegetable();
    }

    public static String getRandomGtin() {
        String gtin = FAKER.code().gtin13();

        while (gtin.split("")[0].equals("0")) {
            gtin = FAKER.code().gtin13();
        }
        return gtin;
    }

    public static String getRandomModelId() {
        List<String> modelsIds;
        try {
            modelsIds = Files.readAllLines(Path.of(MODELS_FILE_PATH));
        } catch (IOException e) {
            throw new IllegalStateException(String.format(PARSING_EXCEPTION_MESSAGE, MODELS_FILE_PATH), e);
        }
        return modelsIds.get(new java.util.Random().nextInt(modelsIds.size()));
    }

    public static List<String> getListOfModelsFromFile() {
        List<String> modelsIds;
        try {
            modelsIds = Files.readAllLines(Path.of(MODELS_FILE_PATH));
        } catch (IOException e) {
            throw new IllegalStateException(String.format(PARSING_EXCEPTION_MESSAGE, MODELS_FILE_PATH), e);
        }
        return modelsIds;
    }
}
