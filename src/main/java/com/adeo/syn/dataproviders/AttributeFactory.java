package com.adeo.syn.dataproviders;

import com.adeo.syn.enums.Bu;
import com.adeo.syn.enums.FunctionalPerimeter;
import com.adeo.syn.enums.RecordType;
import com.adeo.syn.mappingcrud.model.AttributeDescription;
import com.adeo.syn.mappingcrud.model.IncorrectAttributeValuesAndErrors;
import com.adeo.syn.mappingcrud.repository.MappingCrudRepository;
import com.adeo.syn.utils.StringUtils;
import com.adeo.syndication.qa.contenthub.model.Attribute;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.adeo.syn.constants.AttributesConstants.*;
import static com.adeo.syn.constants.ErrorResponses.*;
import static com.adeo.syn.enums.Bu.LMRU;
import static com.adeo.syn.enums.FunctionalPerimeter.*;
import static com.adeo.syn.enums.Language.ru;
import static com.adeo.syn.enums.Owner.LOGBRICK;
import static com.adeo.syn.enums.RecordType.PARTNER_RECORD;
import static com.adeo.syn.utils.Random.*;
import static com.adeo.syn.utils.StringUtils.parseStringWithDotToInt;

@Component
public class AttributeFactory {

    @Autowired
    private MappingCrudRepository mappingCrudRepository;

    public Map<String, Attribute> getAllModelAttributes(Bu businessUnit,
                                                        String modelId,
                                                        RecordType recordType,
                                                        boolean isOnlyMandatory) {
        return getFilledAttributes(recordType, modelId, businessUnit, isOnlyMandatory, DESCRIPTIVE, QUALITY, COMMON);
    }

    public List<Map<String, Attribute>> getFilledLogisticVariants(Bu businessUnit) {
        return List.of(getFilledAttributes(PARTNER_RECORD, getRandomModelId(), businessUnit, false, LOGISTIC));
    }

    public Map<String, Attribute> getFilledAttributes(RecordType recordType,
                                                      String modelId,
                                                      Bu businessUnit,
                                                      boolean isOnlyMandatory,
                                                      FunctionalPerimeter functionalPerimeter,
                                                      FunctionalPerimeter... functionalPerimeters) {

        var modelAttributes = getAttributesProperties(recordType,
                modelId,
                businessUnit,
                isOnlyMandatory,
                functionalPerimeter,
                functionalPerimeters);

        var attributeMap = new HashMap<String, Attribute>();

        for (Map<String, Object> modelMandatoryAttribute : modelAttributes) {
            replaceNullString(modelMandatoryAttribute);
            var attributeId = modelMandatoryAttribute.get("id").toString();
            var attributeType = modelMandatoryAttribute.get("type").toString();
            var measureId = modelMandatoryAttribute.get("measure_id");
            var owner = modelMandatoryAttribute.get("owner").toString();
            boolean isMultivalued = modelMandatoryAttribute.get("ismultivalued") != null &&
                    !modelMandatoryAttribute.get("ismultivalued").toString().contains("null") &&
                    modelMandatoryAttribute.get("ismultivalued").toString().contains("true");

            if (attributeType.contains("number") || attributeType.contains("integer")) {
                attributeMap.putAll(fillNumberValues(modelMandatoryAttribute));
            } else if (attributeType.contains("text")) {
                attributeMap.putAll(fillTextValues(modelMandatoryAttribute, businessUnit));
            } else if (attributeType.contains("date")) {
                attributeMap.put(attributeId,
                        new Attribute().values(List.of(new TextNode(FAKER.date().past(1, TimeUnit.DAYS)
                                .toString()))));
            } else if (attributeType.contains("boolean")) {
                attributeMap.put(attributeId,
                        new Attribute().values(List.of(new TextNode(FAKER.bool().toString()))));
            } else if (attributeType.contains("LOV") && (owner.equals(LOGBRICK.name()) ||
                    COMMON_ATTRIBUTES.contains(attributeId)) && !attributeId.contains(DISPATCH_COUNTRY_CODE)) {
                if (isMultivalued) {
                    attributeMap.put(attributeId, new Attribute().valueIds(getCommonLogisticRandomValueIds(attributeId)));
                } else {
                    attributeMap.put(attributeId,
                            new Attribute().valueIds(List.of(getCommonLogisticRandomValueIds(attributeId).get(0))));
                }
            } else if (attributeType.contains("LOV")) {
                if (isMultivalued) {
                    attributeMap.put(attributeId, new Attribute().valueIds(getRandomValueIds(modelId, attributeId)));
                } else {
                    attributeMap.put(attributeId,
                            new Attribute().valueIds((List.of(getRandomValueId(modelId, attributeId)))));
                }
            }
            if (measureId != null && !measureId.toString().contains("null")) {
                attributeMap.get(attributeId).measureId(measureId.toString());
            }
        }

        if (attributeMap.containsKey(BRAND)) {
            attributeMap.put(BRAND, new Attribute().valueIds(List.of(getRandomValueId(modelId, BRAND))));
        } else if (functionalPerimeter != LOGISTIC) {
            attributeMap.put(PRODUCT_WITHOUT_BRAND, new Attribute().valueIds(List.of("000001")));
        }

        if (recordType == PARTNER_RECORD && functionalPerimeter != LOGISTIC) {
            attributeMap.put(PARTNER_RECORD_GTIN, new Attribute().values(List.of(new LongNode(Long.parseLong(getRandomGtin())))));
        } else if (functionalPerimeter != LOGISTIC) {
            attributeMap.put(GOLDEN_RECORD_GTIN, new Attribute().values(List.of(new LongNode(Long.parseLong(getRandomGtin())))));
        }

        return attributeMap;
    }

    public Map<String, Attribute> getRandomManagementAttributes(String language) {
        Map<String, Attribute> randomManagementAttributes = new HashMap<>();
        var dispatchCountry = getRandomProductName();
        var minimumOrderQuantity = FAKER.number().randomNumber(4, true);
        var minimumOrderUnit = getRandomProductName();
        var customsName = getRandomProductName();
        var tnVed = FAKER.number().randomNumber(4, true);
        var okpd2 = getRandomProductName();

        randomManagementAttributes.put(DISPATCH_COUNTRY_CODE, new Attribute().addValueIdsItem(dispatchCountry));
        randomManagementAttributes.put(MINIMUM_ORDER_QUANTITY,
                new Attribute().addValuesItem(new LongNode(minimumOrderQuantity)));
        randomManagementAttributes.put(MINIMUM_ORDER_UNIT, new Attribute().addValueIdsItem(minimumOrderUnit));
        randomManagementAttributes.put(CUSTOMS_NAME,
                new Attribute().putLanguageValuesItem(language, List.of(customsName)));
        randomManagementAttributes.put(TN_VED_CODE, new Attribute().addValuesItem(new LongNode(tnVed)));
        randomManagementAttributes.put(OKPD2_CODE,
                new Attribute().putLanguageValuesItem(language, List.of(okpd2)));

        return randomManagementAttributes;
    }

    private List<Map<String, Object>> getAttributesProperties(RecordType recordType,
                                                              String modelId,
                                                              Bu businessUnit,
                                                              boolean isOnlyMandatory,
                                                              FunctionalPerimeter functionalPerimeter,
                                                              FunctionalPerimeter... functionalPerimeters) {

        List<Map<String, Object>> attributes = new ArrayList<>();
        var perimeters = Arrays.stream(functionalPerimeters).toList();
        var allModelAttributes = mappingCrudRepository
                .getAllModelAttributes(businessUnit.getBuCode(), businessUnit.getBuLanguage(), modelId);

        var validAttributes = allModelAttributes
                .stream()
                .filter(a -> a.get("functionalperimeters") != null || a.get("owner").equals("LOGBRICK"))
                .filter(a -> !a.get("type").toString().equals("undefined"))
                .toList();

        if (functionalPerimeter == DESCRIPTIVE || perimeters.contains(DESCRIPTIVE)) {
            attributes = new ArrayList<>(attributes);
            attributes.addAll(validAttributes.stream()
                    .filter(a -> (a.get("functionalperimeters") != null))
                    .filter(a -> (a.get("functionalperimeters").toString().contains(functionalPerimeter.name())
                            || Arrays.stream(functionalPerimeters)
                            .map(Enum::name).toList()
                            .contains(StringUtils.removeQuotes(a.get("functionalperimeters").toString()))))
                    .toList());

            attributes.add(validAttributes.stream()
                    .filter(a -> a.get("id").toString().contains(UNIT_OF_MEASUREMENT)).findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException("Model %s not contains an attribute %s".formatted(modelId, UNIT_OF_MEASUREMENT))));
        }

        if (functionalPerimeter == QUALITY || perimeters.contains(QUALITY)) {
            attributes = new ArrayList<>(attributes);
            attributes.addAll(validAttributes.stream()
                    .filter(a -> a.get("functionalperimeters") != null)
                    .filter(a -> (a.get("functionalperimeters").toString().contains(functionalPerimeter.name())
                            || Arrays.stream(functionalPerimeters)
                            .map(Enum::name).toList()
                            .contains(StringUtils.removeQuotes(a.get("functionalperimeters").toString())))).toList());
        }

        if (functionalPerimeter == LOGISTIC || perimeters.contains(LOGISTIC)) {
            attributes = new ArrayList<>(attributes);
            attributes.addAll(validAttributes.stream()
                    .filter(a -> (a.get("owner").equals("LOGBRICK")))
                    .toList());
        }

        if (functionalPerimeter == COMMON || perimeters.contains(COMMON)) {
            attributes = new ArrayList<>(attributes);
            attributes.addAll(getCommonAttributesProperties(LMRU, recordType));
        }

        if (isOnlyMandatory) {
            attributes = attributes.stream()
                    .filter(a -> a.get("ismandatoryforsupplier").toString().contains(Boolean.toString(isOnlyMandatory)))
                    .toList();
        }
        return attributes;
    }

    private List<Map<String, Object>> getCommonAttributesProperties(Bu businessUnit, RecordType recordType) {
        var commonAttributes = mappingCrudRepository
                .getCommonAttributesForTemplate(businessUnit.getBuCode(), businessUnit.getBuLanguage());

        if (recordType == PARTNER_RECORD) {
            return commonAttributes
                    .stream()
                    .filter(a -> !a.get("id").toString().contains(GOLDEN_RECORD_GTIN))
                    .toList();
        } else {
            return commonAttributes
                    .stream()
                    .filter(a -> !a.get("id").toString().contains(PARTNER_RECORD_GTIN))
                    .toList();
        }
    }

    private Map<String, Attribute> fillTextValues(Map<String, Object> attribute, Bu businessUnit) {
        var language = businessUnit.getBuLanguageString();
        if (attribute.get("maxlength") != null) {
            return Map.of(attribute.get("id").toString(),
                    new Attribute().languageValues(Map.of(language, List.of(FAKER.lorem()
                            .characters(parseStringWithDotToInt(attribute.get("maxlength").toString()))))));
        } else {
            return Map.of(attribute.get("id").toString(),
                    new Attribute().languageValues(Map.of(language, List.of(FAKER.lorem().characters()))));
        }
    }

    private Map<String, Attribute> fillNumberValues(Map<String, Object> attribute) {
        if (attribute.get("id").toString().contains(PARTNER_RECORD_GTIN)) {
            return Map.of(PARTNER_RECORD_GTIN, new Attribute().values(List.of(new LongNode(Long.parseLong(getRandomGtin())))));
        }

        if (attribute.get("id").toString().contains(PARTNER_RECORD_GTIN)) {
            return Map.of(PARTNER_RECORD_GTIN, new Attribute().values(List.of(new LongNode(Long.parseLong(getRandomGtin())))));
        }

        if (attribute.get("maxvalue") != null && attribute.get("minvalue") != null) {
            return Map.of(attribute.get("id").toString(),
                    getAttributeValues(parseStringWithDotToInt(attribute.get("minvalue").toString()),
                            parseStringWithDotToInt(attribute.get("maxvalue").toString())));
        } else if (attribute.get("minvalue") != null) {
            int minValue = parseStringWithDotToInt(attribute.get("minvalue").toString());
            return Map.of(attribute.get("id").toString(),
                    getAttributeValues(minValue, minValue + 1));
        } else if (attribute.get("maxvalue") != null) {
            int maxValue = parseStringWithDotToInt(attribute.get("maxvalue").toString());
            return Map.of(attribute.get("id").toString(),
                    getAttributeValues(1, maxValue));
        } else {
            return Map.of(attribute.get("id").toString(), getAttributeValues(1, 3000));
        }
    }

    private String getRandomValueId(String modelId, String attributeId) {
        var valueIds = mappingCrudRepository.getLovAttributeValueIds(attributeId, modelId);
        return valueIds.get(FAKER.number().numberBetween(0, valueIds.size()));
    }

    private List<String> getRandomValueIds(String modelId, String attributeId) {
        List<String> valueIds = mappingCrudRepository.getLovAttributeValueIds(attributeId, modelId);
        Collections.shuffle(valueIds);
        return valueIds.stream().limit(valueIds.size() / 2).toList();
    }

    private List<String> getCommonLogisticRandomValueIds(String attributeId) {
        List<String> valueIds = mappingCrudRepository.getCommonValueIds(attributeId);
        Collections.shuffle(valueIds);
        return valueIds.stream().limit(valueIds.size() / 2).toList();
    }

    private void replaceNullString(Map<String, Object> rawAttributes) {
        if (rawAttributes.get("minvalue") != null && rawAttributes.get("minvalue").toString().contains("null")) {
            rawAttributes.replace("minvalue", null);
        }
        if (rawAttributes.get("maxvalue") != null && rawAttributes.get("maxvalue").toString().contains("null")) {
            rawAttributes.replace("maxvalue", null);
        }
        if (rawAttributes.get("maxlength") != null && rawAttributes.get("maxlength").toString().contains("null")) {
            rawAttributes.replace("maxlength", null);
        }
    }

    private Attribute getAttributeValues(int minValue, int maxValue) {
        return new Attribute().addValuesItem(new LongNode(FAKER.number()
                .numberBetween(minValue, maxValue)));
    }

    public IncorrectAttributeValuesAndErrors getIncorrectAttributesAndErrorDescriptions(List<AttributeDescription> constraints) {
        Map<String, Attribute> incorrectAttributes = new HashMap<>();
        Map<String, Attribute> incorrectLogisticAttributes = new HashMap<>();
        Map<String, String> attributesErrors = new HashMap<>();

        for (AttributeDescription attribute : constraints) {
            if (attribute.getOwner().equals("LOGBRICK")) {
                if (attribute.getType().equals("text")) {
                    if (attribute.getMaxLength() != null && !attribute.getMaxLength().equals("null")) {
                        var length = parseStringWithDotToInt(attribute.getMaxLength());
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().languageValues(Map.of(ru.name(), List.of(FAKER.lorem().characters(length + (new Random().nextInt(9) + 1))))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_MAX_LENGTH_EXCEEDED.formatted(length));
                    } else {
                        if (attribute.getIsMandatoryForSupplier().equals("true")) {
                            attributesErrors.put(attribute.getId(), MANDATORY_ATTRIBUTE_MISSING);
                        }
                    }
                }

                if (attribute.getType().equals("integer")) {
                    if ((attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) && (attribute.getMinValue() != null && !attribute.getMinValue().equals("null"))) {
                        var max = parseStringWithDotToInt(attribute.getMaxValue());
                        var min = parseStringWithDotToInt(attribute.getMinValue());
                        if (Math.random() < 0.5) {
                            incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(max, Integer.MAX_VALUE)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                        } else {
                            incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(Integer.MIN_VALUE, min)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                        }
                    } else if (attribute.getMinValue() != null && !attribute.getMinValue().equals("null")) {
                        var min = parseStringWithDotToInt(attribute.getMinValue());
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(Integer.MIN_VALUE, min)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                    } else if (attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) {
                        var max = parseStringWithDotToInt(attribute.getMaxValue());
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(max, Integer.MAX_VALUE)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                    } else {
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new TextNode(FAKER.regexify("[A-Z]{5}")))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_INTEGER);
                    }
                }

                if (attribute.getType().equals("number")) {
                    if (attribute.getMask() != null && !attribute.getMask().equals("null")) {
                        var mask = attribute.getMask().replace("C", ".").replace("9", "1");
                        var before = attribute.getMask().indexOf("C");
                        var after = attribute.getMask().length() - attribute.getMask().indexOf("C") - 1;
                        if (Math.random() < 0.5) {
                            var number = Double.parseDouble(mask);
                            incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(number * 10))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_CONFORMS_TO_FLOATING_POINT_MASK_BEFORE_C.formatted(before));
                        } else {
                            var number = Double.parseDouble(mask + "9");
                            incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of((new DoubleNode(number)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_CONFORMS_TO_FLOATING_POINT_MASK_AFTER_C.formatted(after));
                        }
                    } else if ((attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) && (attribute.getMinValue() != null && !attribute.getMinValue().equals("null"))) {
                        if (Math.random() < 0.5) {
                            var max = parseStringWithDotToInt(attribute.getMaxValue());
                            incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, max, max + 1)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                        } else {
                            var min = parseStringWithDotToInt(attribute.getMinValue());
                            incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, min - 1, min)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                        }

                    } else if (attribute.getMinValue() != null && !attribute.getMinValue().equals("null")) {
                        var min = parseStringWithDotToInt(attribute.getMinValue());
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, min - 1, min)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                    } else if (attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) {
                        var max = parseStringWithDotToInt(attribute.getMaxValue());
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, max, max + 1)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                    } else {
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().values(List.of(new TextNode(FAKER.regexify("[A-Z]{5}")))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_INTEGER_OR_FLOAT);
                    }
                }
                if (attribute.getType().equals("LOV")) {
                    if (!attribute.getIsMandatoryForSupplier().equals("true")) {
                        incorrectLogisticAttributes.put(attribute.getId(), new Attribute().valueIds(List.of((FAKER.numerify("#####")))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_IN_THE_ALLOWED_LIST);
                    } else {
                        attributesErrors.put(attribute.getId(), MANDATORY_ATTRIBUTE_MISSING);
                    }
                }
            }

            if (!attribute.getOwner().equals("LOGBRICK")) {
                if (attribute.getType().equals("text")) {
                    if (attribute.getMaxLength() != null && !attribute.getMaxLength().equals("null")) {
                        var length = parseStringWithDotToInt(attribute.getMaxLength());
                        incorrectAttributes.put(attribute.getId(), new Attribute().languageValues(Map.of(ru.name(), List.of(FAKER.lorem().characters(length + (new Random().nextInt(9) + 1))))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_MAX_LENGTH_EXCEEDED.formatted(length));
                    } else {
                        if (attribute.getIsMandatoryForSupplier().equals("true")) {
                            attributesErrors.put(attribute.getId(), MANDATORY_ATTRIBUTE_MISSING);
                        }
                    }
                }

                if (attribute.getType().equals("integer")) {
                    if ((attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) && (attribute.getMinValue() != null && !attribute.getMinValue().equals("null"))) {
                        var max = parseStringWithDotToInt(attribute.getMaxValue());
                        var min = parseStringWithDotToInt(attribute.getMinValue());
                        if (Math.random() < 0.5) {
                            incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(max, Integer.MAX_VALUE)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                        } else {
                            incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(Integer.MIN_VALUE, min)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                        }
                    } else if (attribute.getMinValue() != null && !attribute.getMinValue().equals("null")) {
                        var min = parseStringWithDotToInt(attribute.getMinValue());
                        incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(Integer.MIN_VALUE, min)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                    } else if (attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) {
                        var max = parseStringWithDotToInt(attribute.getMaxValue());
                        incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new IntNode(FAKER.number().numberBetween(max, Integer.MAX_VALUE)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                    } else {
                        incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new TextNode(FAKER.regexify("[A-Z]{5}")))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_INTEGER);
                    }
                }

                if (attribute.getType().equals("number")) {
                    if (attribute.getMask() != null && !attribute.getMask().equals("null")) {
                        var mask = attribute.getMask().replace("C", ".").replace("9", "1");
                        var before = attribute.getMask().indexOf("C");
                        var after = attribute.getMask().length() - attribute.getMask().indexOf("C") - 1;
                        if (Math.random() < 0.5) {
                            var number = Double.parseDouble(mask);
                            incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(number * 10))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_CONFORMS_TO_FLOATING_POINT_MASK_BEFORE_C.formatted(before));
                        } else {
                            var number = Double.parseDouble(mask + "9");
                            incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of((new DoubleNode(number)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_CONFORMS_TO_FLOATING_POINT_MASK_AFTER_C.formatted(after));
                        }
                    } else if ((attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) && (attribute.getMinValue() != null && !attribute.getMinValue().equals("null"))) {
                        if (Math.random() < 0.5) {
                            var max = parseStringWithDotToInt(attribute.getMaxValue());
                            incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, max, max + 1)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                        } else {
                            var min = parseStringWithDotToInt(attribute.getMinValue());
                            incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, min - 1, min)))));
                            attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                        }

                    } else if (attribute.getMinValue() != null && !attribute.getMinValue().equals("null")) {
                        var min = parseStringWithDotToInt(attribute.getMinValue());
                        incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, min - 1, min)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_LESS_THAN_MIN.formatted(min));
                    } else if (attribute.getMaxValue() != null && !attribute.getMaxValue().equals("null")) {
                        var max = parseStringWithDotToInt(attribute.getMaxValue());
                        incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new DoubleNode(FAKER.number().randomDouble(2, max, max + 1)))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_GREATER_THAN_MAX.formatted(max));
                    } else {
                        incorrectAttributes.put(attribute.getId(), new Attribute().values(List.of(new TextNode(FAKER.regexify("[A-Z]{5}")))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_INTEGER_OR_FLOAT);
                    }
                }

                if (attribute.getType().equals("LOV")) {
                    if (!attribute.getIsMandatoryForSupplier().equals("true")) {
                        incorrectAttributes.put(attribute.getId(), new Attribute().valueIds(List.of((FAKER.numerify("#####")))));
                        attributesErrors.put(attribute.getId(), ATTRIBUTE_VALUE_NOT_IN_THE_ALLOWED_LIST);
                    } else {
                        attributesErrors.put(attribute.getId(), MANDATORY_ATTRIBUTE_MISSING);
                    }
                }
            }
        }
        return new IncorrectAttributeValuesAndErrors(incorrectAttributes, incorrectLogisticAttributes, attributesErrors);
    }
}

