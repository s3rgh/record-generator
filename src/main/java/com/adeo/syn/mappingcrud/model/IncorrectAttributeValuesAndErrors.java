package com.adeo.syn.mappingcrud.model;

import com.adeo.syndication.qa.contenthub.model.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class IncorrectAttributeValuesAndErrors {
    private Map<String, Attribute> incorrectAttributes;
    private Map<String, Attribute> incorrectLogisticAttributes;
    private Map<String, String> attributesErrors;
}
