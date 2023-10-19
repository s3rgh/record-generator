package com.adeo.syn.constants;

import com.adeo.syndication.qa.contenthub.model.Attribute;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.adeo.syn.enums.Language.ru;
import static com.adeo.syn.utils.Random.FAKER;

public final class LogisticVariants {
    public static final String INNER_DEPTH_NUMBER_VALUE = "inner_depth";
    public static final String INNER_GROSS_WEIGHT_NUMBER_VALUE = "inner_gross_weight";
    public static final String INNER_HEIGHT_NUMBER_VALUE = "inner_height";
    public static final String INNER_NEST_INTEGER_VALUE = "inner_nest";
    public static final String INNER_NET_WEIGHT_NUMBER_VALUE = "inner_net_weight";
    public static final String INNER_PACK_MATERIAL_LOV_VALUE_IDS = "inner_pack_material";
    public static final String INNER_PACK_TYPE_LOV_VALUE_IDS = "inner_pack_type";
    public static final String INNER_WIDTH_NUMBER_VALUE = "inner_width";
    public static final String INNER_BARCODE_TEXT_LANGUAGE_VALUES = "inner_barcode";
    public static final String OUTER_DEPTH_NUMBER_VALUE = "outer_depth";
    public static final String OUTER_GROSS_WEIGHT_NUMBER_VALUE = "outer_gross_weight";
    public static final String OUTER_HEIGHT_NUMBER_VALUE = "outer_height";
    public static final String OUTER_NEST_INTEGER_VALUE = "outer_nest";
    public static final String OUTER_NET_WEIGHT_NUMBER_VALUE = "outer_net_weight";
    public static final String OUTER_PACK_MATERIAL_LOV_VALUE_IDS = "outer_pack_material";
    public static final String OUTER_PACK_TYPE_LOV_VALUE_IDS = "outer_pack_type";
    public static final String OUTER_WIDTH_NUMBER_VALUE = "outer_width";
    public static final String OUTER_BARCODE_TEXT_LANGUAGE_VALUES = "outer_barcode";
    public static final String UNIT_DEPTH_NUMBER_VALUE = "unit_depth";
    public static final String UNIT_FRAGILE_LOV_VALUE_IDS = "unit_fragile";
    public static final String UNIT_LIQUID_LOV_VALUE_IDS = "unit_liquid";
    public static final String UNIT_GROSS_WEIGHT_NUMBER_VALUE = "unit_gross_weight";
    public static final String UNIT_HEIGHT_NUMBER_VALUE = "unit_height";
    public static final String UNIT_LAYER_PROCESS_LOV_VALUE_IDS = "unit_layer_process";
    public static final String UNIT_NET_WEIGHT_NUMBER_VALUE = "unit_net_weight";
    public static final String UNIT_NO_STACK_LOV_VALUE_IDS = "unit_no_stack";
    public static final String UNIT_PACK_MATERIAL_LOV_VALUE_IDS = "unit_pack_material";
    public static final String UNIT_PACK_PLACE_C_INTEGER_VALUE = "unit_pack_place_c";
    public static final String UNIT_PACK_TYPE_LOV_VALUE_IDS = "unit_pack_type";
    public static final String UNIT_STACK_HEIGHT_INC_NUMBER_VALUE = "unit_stack_height_inc";
    public static final String UNIT_STOR_TEMP_LOV_VALUE_IDS = "unit_stor_temp";
    public static final String UNIT_THIS_SIDE_UP_LOV_VALUE_IDS = "unit_this_side_up";
    public static final String UNIT_WIDTH_NUMBER_VALUE = "unit_width";
    public static final String UNIT_BLISTER_HOLE_C_INTEGER_VALUE = "unit_blister_hole_c";
    public static final String UNIT_BLISTER_HOLE_D_NUMBER_VALUE = "unit_blister_hole_d";
    public static final String UNIT_BLISTER_HOLE_LC_NUMBER_VALUE = "unit_blister_hole_lc";
    public static final String UNIT_BLISTER_HOLE_RC_NUMBER_VALUE = "unit_blister_hole_rc";
    public static final String UNIT_BLISTER_HOLE_UC_NUMBER_VALUE = "unit_blister_hole_uc";
    public static final String PALLET_BOXES_IN_LAYER_INTEGER_VALUE = "pallet_boxes_in_layer";
    public static final String LOG_VARIANT_DEPTH_NUMBER_VALUE = "log_variant_depth";
    public static final String LOG_VARIANT_GROSS_WEIGHT_NUMBER_VALUE = "log_variant_gross_weight";
    public static final String LOG_VARIANT_HEIGHT_NUMBER_VALUE = "log_variant_height";
    public static final String PALLET_LAYERS_C_INTEGER_VALUE = "pallet_layers_c";
    public static final String LOG_VARIANT_WIDTH_NUMBER_VALUE = "log_variant_width";

    public static Map<String, Attribute> getRandomLogisticVariants() {
        Map<String, Attribute> logisticVariants = new HashMap<>();

        logisticVariants.put(INNER_DEPTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(INNER_GROSS_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(INNER_HEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(INNER_NEST_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        logisticVariants.put(INNER_NET_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(INNER_PACK_MATERIAL_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(INNER_PACK_TYPE_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(INNER_WIDTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(INNER_BARCODE_TEXT_LANGUAGE_VALUES, new Attribute()
                .languageValues(Collections.singletonMap(ru.name(),
                        Collections.singletonList(String.valueOf(FAKER.number().numberBetween(1, 10000))))));

        logisticVariants.put(OUTER_DEPTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());

        logisticVariants.put(OUTER_BARCODE_TEXT_LANGUAGE_VALUES, new Attribute()
                .languageValues(Collections.singletonMap(ru.name(),
                        Collections.singletonList(String.valueOf(FAKER.number().numberBetween(1, 10000))))));

        logisticVariants.put(OUTER_GROSS_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(OUTER_HEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(OUTER_NEST_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        logisticVariants.put(OUTER_NET_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(OUTER_PACK_MATERIAL_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(OUTER_PACK_TYPE_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(OUTER_WIDTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());

        logisticVariants.put(UNIT_FRAGILE_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_LIQUID_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_GROSS_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_HEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_LAYER_PROCESS_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_NET_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_NO_STACK_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_PACK_MATERIAL_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_DEPTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_PACK_PLACE_C_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        logisticVariants.put(UNIT_PACK_TYPE_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_STACK_HEIGHT_INC_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_STOR_TEMP_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_THIS_SIDE_UP_LOV_VALUE_IDS, getNewRandomLovAttribute());
        logisticVariants.put(UNIT_WIDTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_BLISTER_HOLE_C_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        logisticVariants.put(UNIT_BLISTER_HOLE_D_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_BLISTER_HOLE_LC_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_BLISTER_HOLE_RC_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(UNIT_BLISTER_HOLE_UC_NUMBER_VALUE, getNewRandomNumberValueAttribute());

        logisticVariants.put(PALLET_BOXES_IN_LAYER_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        logisticVariants.put(LOG_VARIANT_DEPTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(LOG_VARIANT_GROSS_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(LOG_VARIANT_HEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        logisticVariants.put(PALLET_LAYERS_C_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        logisticVariants.put(LOG_VARIANT_WIDTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());

        return logisticVariants;
    }

    public static Map<String, Attribute> getRandomDataPack() {
        var dataPack = new HashMap<String, Attribute>();

        dataPack.put(UNIT_FRAGILE_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_LIQUID_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_GROSS_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_HEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_LAYER_PROCESS_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_NET_WEIGHT_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_NO_STACK_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_PACK_MATERIAL_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_DEPTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_PACK_PLACE_C_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        dataPack.put(UNIT_PACK_TYPE_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_STACK_HEIGHT_INC_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_STOR_TEMP_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_THIS_SIDE_UP_LOV_VALUE_IDS, getNewRandomLovAttribute());
        dataPack.put(UNIT_WIDTH_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_BLISTER_HOLE_C_INTEGER_VALUE, getNewRandomIntegerValueAttribute());
        dataPack.put(UNIT_BLISTER_HOLE_D_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_BLISTER_HOLE_LC_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_BLISTER_HOLE_RC_NUMBER_VALUE, getNewRandomNumberValueAttribute());
        dataPack.put(UNIT_BLISTER_HOLE_UC_NUMBER_VALUE, getNewRandomNumberValueAttribute());

        return dataPack;
    }

    private static Attribute getNewRandomLovAttribute() {
        return new Attribute().addValueIdsItem(String.valueOf(FAKER.number().numberBetween(1, 10000)));
    }

    private static Attribute getNewRandomIntegerValueAttribute() {
        return new Attribute().addValuesItem(new IntNode(FAKER.number().numberBetween(1, 10000)));
    }

    private static Attribute getNewRandomNumberValueAttribute() {
        return new Attribute().addValuesItem(new DoubleNode(FAKER.random().nextDouble() + FAKER.number().numberBetween(1, 10000)));
    }

    private LogisticVariants() {
    }
}
