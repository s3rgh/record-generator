package com.adeo.syn.dataproviders;

import com.adeo.syn.enums.State;
import com.adeo.syn.mappingcrud.model.AttributeDescription;
import com.adeo.syn.mappingcrud.model.IncorrectAttributeValuesAndErrors;
import com.adeo.syn.mappingcrud.repository.MappingCrudRepository;
import com.adeo.syn.workersequoya.repository.WorkerSequoyaRepository;
import com.adeo.syndication.qa.contenthub.api.PartnerRecordsControllerApi;
import com.adeo.syndication.qa.contenthub.model.*;
import com.adeo.syndication.qa.statemachine.api.ControllerForSupplierProductStatesManagementApi;
import com.adeo.syndication.qa.statemachine.model.ProductStateCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.adeo.syn.enums.Bu.LMRU;
import static com.adeo.syn.enums.FunctionalPerimeter.*;
import static com.adeo.syn.enums.Language.ru;
import static com.adeo.syn.enums.RecordType.PARTNER_RECORD;
import static com.adeo.syn.utils.Random.*;
import static io.qameta.allure.Allure.step;

@Component
public final class PartnerRecordFactory {

    @Autowired
    private MappingCrudRepository mappingCrudRepository;

    @Autowired
    private WorkerSequoyaRepository workerSequoyaRepository;

    @Autowired
    private PartnerRecordsControllerApi partnerRecordsControllerApi;

    @Autowired
    private ControllerForSupplierProductStatesManagementApi controllerForSupplierProductStatesManagementApi;

    @Autowired
    private AttributeFactory attributeGenerator;

    public List<Long> createRecords(int recordNumber, State recordStatus) {
        return step("Create " + recordNumber + " partner records", () -> {
            List<Long> recordIds = new ArrayList<>();
            for (int i = 0; i < recordNumber; i++) {
                PartnerRecordCreated partnerRecord = createPartnerRecord();
                changePartnerRecordState(partnerRecord.getId(), recordStatus);
                recordIds.add(partnerRecord.getId());
            }
            return recordIds;
        });
    }

    public PartnerRecordCreated createPartnerRecord(Long supplierId,
                                                    String modelId,
                                                    Map<String, Attribute> partnerRecordAttributes,
                                                    List<Map<String, Attribute>> logisticVariants) {

        var attributesSet = new HashMap<>(partnerRecordAttributes);
        var departments = workerSequoyaRepository.getDepartmentsForModel(modelId);
        var department =
                PartnerRecordCreateRequestV2.DepartmentNumberEnum.fromValue(departments.get(FAKER.random().nextInt(departments.size())));

        var partnerRecordCreateRequest = new PartnerRecordCreateRequestV2()
                .modelId(modelId)
                .partnerCode(String.valueOf(supplierId))
                .departmentNumber(department)
                .attributes(attributesSet)
                .logisticVariants(logisticVariants);

        return createPartnerRecord(partnerRecordCreateRequest);
    }

    /**
     * Method creates partner record with random values of Common and Mandatory attributes for random model and partnerId
     */
    public PartnerRecordCreated createPartnerRecord() {
        var partnerRecordCreateRequestV2 = getPartnerRecordCreateRequest();
        return createPartnerRecord(partnerRecordCreateRequestV2);

    }

    public PartnerRecordCreated createPartnerRecord(String modelId) {
        var partnerRecordCreateRequestV2 = getPartnerRecordCreateRequest(modelId);
        return createPartnerRecord(partnerRecordCreateRequestV2);
    }

    public PartnerRecordCreated createPartnerRecord(PartnerRecordCreateRequestV2 partnerRecordCreateRequest) {
        var partnerRecordResponse = partnerRecordsControllerApi
                .v2PartnerRecordsPost()
                .body(partnerRecordCreateRequest)
                .execute(r -> r.then()
                        .statusCode(201)
                        .extract().as(PartnerRecordCreated.class));

        controllerForSupplierProductStatesManagementApi
                .v3ProductStatesPost()
                .body(new ProductStateCreateRequest()
                        .productIdentifier(partnerRecordResponse.getId())
                        .productStatusIdentifier(ProductStateCreateRequest.ProductStatusIdentifierEnum.NUMBER_9))
                .execute(r -> r.then()
                        .statusCode(201));

        return partnerRecordResponse;
    }

    public PartnerRecordCreateRequestV2 getPartnerRecordCreateRequest() {
        var models = getListOfModelsFromFile();
        var actualModels = mappingCrudRepository.getModelNamesByListIds(models, ru);

        return getPartnerRecordCreateRequest(actualModels.get(new Random().nextInt(actualModels.size())).getId());
    }

    public PartnerRecordCreateRequestV2 getPartnerRecordCreateRequest(String modelId) {
        var mandatoryAttributes = attributeGenerator
                .getFilledAttributes(PARTNER_RECORD, modelId, LMRU, true, COMMON, DESCRIPTIVE, QUALITY);

        var logisticVariants = attributeGenerator.getFilledLogisticVariants(LMRU);
        var departments = workerSequoyaRepository.getDepartmentsForModel(modelId);
        var department =
                PartnerRecordCreateRequestV2.DepartmentNumberEnum.fromValue(departments.get(FAKER.random().nextInt(departments.size())));


        return new PartnerRecordCreateRequestV2()
                .modelId(modelId)
                .partnerCode(String.valueOf(getRandomSupplierId()))
                .departmentNumber(department)
                .attributes(mandatoryAttributes)
                .logisticVariants(logisticVariants);
    }

    public PartnerRecordUpdateRequest getPartnerRecordUpdateRequest(String modelId) {
        var mandatoryAttributes = attributeGenerator
                .getFilledAttributes(PARTNER_RECORD, modelId, LMRU, true, COMMON, DESCRIPTIVE, QUALITY);

        var logisticVariants = attributeGenerator.getFilledLogisticVariants(LMRU);

        return new PartnerRecordUpdateRequest()
                .modelId(modelId)
                .attributes(mandatoryAttributes)
                .logisticVariants(logisticVariants);
    }

    public IncorrectAttributeValuesAndErrors getIncorrectAttributesAndErrorDescriptions(List<AttributeDescription> constraints) {
        return attributeGenerator.getIncorrectAttributesAndErrorDescriptions(constraints);
    }

    public PartnerRecordUpdateRequest getPartnerRecordIncorrectUpdateRequest(String modelId, IncorrectAttributeValuesAndErrors attributeValuesAndErrors) {
        return new PartnerRecordUpdateRequest()
                .modelId(modelId)
                .attributes(attributeValuesAndErrors.getIncorrectAttributes())
                .logisticVariants(List.of(attributeValuesAndErrors.getIncorrectLogisticAttributes()));
    }

    public PartnerRecordPartialUpdateRequest getPartnerRecordPatchRequest(String modelId) {
        var mandatoryAttributes = attributeGenerator
                .getFilledAttributes(PARTNER_RECORD, modelId, LMRU, true, COMMON, DESCRIPTIVE, QUALITY);

        var logisticVariants = attributeGenerator.getFilledLogisticVariants(LMRU);

        return new PartnerRecordPartialUpdateRequest()
                .attributes(mandatoryAttributes)
                .logisticVariants(logisticVariants);
    }

    public PartnerRecordUpdateRequest updatePartnerRecord(PartnerRecordCreated partnerRecord,
                                                          Map<String, Attribute> updatedAttributes) {
        return partnerRecordsControllerApi.v1PartnerRecordsIdPut()
                .idPath(partnerRecord.getId())
                .body(new PartnerRecordUpdateRequest()
                        .modelId(partnerRecord.getModelId())
                        .attributes(updatedAttributes)
                        .logisticVariants(partnerRecord.getLogisticVariants()))
                .execute(r -> r.then().statusCode(200))
                .extract().as(PartnerRecordUpdateRequest.class);
    }

    private void changePartnerRecordState(long productIdentifier, State recordStatus) {
        controllerForSupplierProductStatesManagementApi
                .v1ProductStatesProductIdentifierPut().productIdentifierPath(productIdentifier)
                .productStatusIdentifierQuery(recordStatus.getId())
                .execute(r -> r.then().statusCode(200));
    }
}
