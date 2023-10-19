package com.adeo.syn.utils;

public class StringUtils {
    private StringUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * This method adds a decimal separator and a zero character to the end of the string.
     * This is an assumption for text and integer hints in the current process of creating a template.
     *
     * @param string - a string needing to add a decimal separator and a zero character
     * @return string with decimal separator and a zero character at end
     */
    public static String addDecimalSeparatorAndZero(String string) {
        return string.contains(".") || string.contains(",") ? string : string + ".0";
    }

    public static String removeQuotes(String string) {
        return string.replaceAll("^\"|\"$", "");
    }

    public static boolean isNull(String string) {
        return string == null || string.equals("null");
    }

    public static String removeEscape(String string) {
        return string.replace("\\", "");
    }

    public static String removeDecimalSeparator(String string) {
        return string.split("[.,]")[0];
    }

    public static int parseStringWithDotToInt(String string) {
        return Integer.parseInt(removeQuotes(removeDecimalSeparator(string)));
    }
}
