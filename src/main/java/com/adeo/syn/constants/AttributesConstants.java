package com.adeo.syn.constants;

import java.util.List;

public class AttributesConstants {
    public static final String SUPPLIER_REFERENCE_CODE = "ATT_99659";
    public static final String CUSTOMS_NAME = "ATT_98822";
    public static final String TN_VED_CODE = "ATT_98823";
    public static final String OKPD2_CODE = "ATT_98808";
    public static final String MINIMUM_ORDER_QUANTITY = "ATT_98950";
    public static final String MINIMUM_ORDER_UNIT = "partnerProductMinimumOrderUnit";
    public static final String DISPATCH_COUNTRY_CODE = "ATT_99626";
    public static final String PRODUCT_NAME = "ATT_99663";
    public static final String BRAND = "ATT_06575";
    public static final String PARTNER_RECORD_GTIN = "ATT_98807";
    public static final String GOLDEN_RECORD_GTIN = "ATT_99618";
    public static final String UNIT_OF_MEASUREMENT = "ATT_99540";
    public static final String IS_PRODUCT_WITHOUT_GTIN = "isProductWithoutGtin";
    public static final String PRODUCT_TYPOLOGY_IDENTIFIER = "productTypologyIdentifier";
    public static final String PRODUCT_WITHOUT_BRAND = "ATT_22228";
    public static final List<String> COMMON_ATTRIBUTES =
            List.of(SUPPLIER_REFERENCE_CODE,
                    PARTNER_RECORD_GTIN,
                    GOLDEN_RECORD_GTIN,
                    CUSTOMS_NAME,
                    TN_VED_CODE,
                    OKPD2_CODE,
                    MINIMUM_ORDER_QUANTITY,
                    MINIMUM_ORDER_UNIT,
                    DISPATCH_COUNTRY_CODE,
                    PRODUCT_NAME);

    private AttributesConstants() {
    }
}
