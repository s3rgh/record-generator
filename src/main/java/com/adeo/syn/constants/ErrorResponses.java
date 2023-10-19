package com.adeo.syn.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ErrorResponses {
    public static final String MANDATORY_ATTRIBUTE_MISSING = "Обязательный атрибут не заполнен";
    public static final String ATTRIBUTE_VALUE_NOT_IN_THE_ALLOWED_LIST = "Выбрано несуществующее значение атрибута. Выберите одно из значений из списка в шаблоне";
    public static final String ATTRIBUTE_VALUE_NOT_INTEGER = "Значение атрибута должно быть положительным целым числом";
    public static final String ATTRIBUTE_VALUE_NOT_INTEGER_OR_FLOAT = "Значение атрибута должно быть целым числом или числом с плавающей точкой";
    public static final String ATTRIBUTE_VALUE_NOT_MATCHES_DATE_REGEX = "Значение атрибута должно быть представлено в виде 'ДД.ММ.ГГГГ'";
    public static final String ATTRIBUTE_VALUE_MAX_LENGTH_EXCEEDED = "Длина значения атрибута превышает допустимую максимальную длину (%d символов)";
    public static final String ATTRIBUTE_VALUE_LESS_THAN_MIN = "Значение атрибута меньше допустимого минимального значения (не менее %d)";
    public static final String ATTRIBUTE_VALUE_GREATER_THAN_MAX = "Значение атрибута превышает допустимое максимальное значение (не более %d)";
    public static final String ATTRIBUTE_VALUE_NOT_CONFORMS_TO_FLOATING_POINT_MASK_BEFORE_C = "Количество символов перед плавающей точкой в значении атрибута превышает допустимое максимальное количество (не более %d)";
    public static final String ATTRIBUTE_VALUE_NOT_CONFORMS_TO_FLOATING_POINT_MASK_AFTER_C = "Количество символов после плавающей точки в значении атрибута превышает допустимое максимальное количество (не более %d)";
    public static final String ATTRIBUTE_VALUE_CONTAINS_SPECIAL_CHARACTERS = "Значение атрибута содержит неизвестные, нечитаемые или специальные символы";
}
