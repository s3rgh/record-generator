package com.adeo.syn.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
public final class FileUtils {

    public static File getFileFromResources(String path) {
        try {
            return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + path);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(String.format("File %s not found", path), e);
        }
    }

    public static String readString(String fileFromResources) {
        try {
            return Files.readString(getFileFromResources(fileFromResources).toPath());
        } catch (IOException e) {
            throw new IllegalStateException("Can't read file", e);
        }
    }
}
