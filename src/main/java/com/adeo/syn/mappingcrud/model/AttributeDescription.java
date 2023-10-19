package com.adeo.syn.mappingcrud.model;

import lombok.Data;

@Data
public class AttributeDescription {
    private String id;
    private String name;
    private String description;
    private String concernedBu;
    private String isFilledBySupplier;
    private String type;
    private String owner;
    private String maxLength;
    private String minValue;
    private String maxValue;
    private String mask;
    private String isMultivalued;
    private String isMandatoryForSupplier;
    private String functionalPerimeter;
    private String measureId;

    public AttributeDescription removeQuotesInFields() {
        if (this.id != null) {
            this.id = this.getId().replaceAll("^\"|\"$", "");
        }

        if (this.name != null) {
            this.name = this.getName().replaceAll("^\"|\"$", "");
        }

        if (this.description != null) {
            this.description = this.getDescription().replaceAll("^\"|\"$", "");
        }

        if (this.concernedBu != null) {
            this.concernedBu = this.getConcernedBu().replaceAll("^\"|\"$", "");
        }

        if (this.isFilledBySupplier != null) {
            this.isFilledBySupplier = this.getIsFilledBySupplier().replaceAll("^\"|\"$", "");
        }

        if (this.type != null) {
            this.type = this.getType().replaceAll("^\"|\"$", "");
        }

        if (this.owner != null) {
            this.owner = this.getOwner().replaceAll("^\"|\"$", "");
        }

        if (this.maxLength != null) {
            this.maxLength = this.getMaxLength().replaceAll("^\"|\"$", "");
        }

        if (this.minValue != null) {
            this.minValue = this.getMinValue().replaceAll("^\"|\"$", "");
        }

        if (this.maxValue != null) {
            this.maxValue = this.getMaxValue().replaceAll("^\"|\"$", "");
        }

        if (this.mask != null) {
            this.mask = this.getMask().replaceAll("^\"|\"$", "");
        }

        if (this.isMultivalued != null) {
            this.isMultivalued = this.getIsMultivalued().replaceAll("^\"|\"$", "");
        }

        if (this.isMandatoryForSupplier != null) {
            this.isMandatoryForSupplier = this.getIsMandatoryForSupplier().replaceAll("^\"|\"$", "");
        }

        if (this.functionalPerimeter != null) {
            this.functionalPerimeter = this.getFunctionalPerimeter().replaceAll("^\"|\"$", "");
        }

        if (this.measureId != null) {
            this.measureId = this.getMeasureId().replaceAll("^\"|\"$", "");
        }
        return this;
    }
}
