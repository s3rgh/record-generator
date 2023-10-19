package com.adeo.syn.dataproviders;

import com.adeo.syn.enums.Bu;
import com.adeo.syn.utils.Random;
import com.adeo.syndication.qa.contenthub.api.GoldenRecordsControllerApi;
import com.adeo.syndication.qa.contenthub.model.*;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.adeo.syn.constants.AttributesConstants.*;
import static com.adeo.syn.utils.Random.getRandomAdeoKey;
import static com.adeo.syn.utils.Random.getRandomModelId;
import static com.fasterxml.jackson.databind.node.BooleanNode.FALSE;

@Component
public final class GoldenRecordFactory {

    @Autowired
    private GoldenRecordsControllerApi goldenRecordsControllerApi;

    public GoldenRecordCreateResponse createGoldenRecord(PartnerRecordCreated partnerRecord) {
        var partnerRecordId = partnerRecord.getId();
        var modelId = partnerRecord.getModelId();
        var attributes = new HashMap<>(partnerRecord.getAttributes());
        var dataPack = partnerRecord.getLogisticVariants();

        return createGoldenRecord(partnerRecordId, modelId, attributes, dataPack);
    }

    public GoldenRecordCreateResponse createGoldenRecord(Long partnerRecordId,
                                                         String modelId,
                                                         Map<String, Attribute> attributes,
                                                         List<Map<String, Attribute>> dataPack) {
        attributes.put(GOLDEN_RECORD_GTIN, attributes.remove(PARTNER_RECORD_GTIN));
        attributes.put(IS_PRODUCT_WITHOUT_GTIN, new Attribute().values(List.of(FALSE)));
        attributes.put(PRODUCT_TYPOLOGY_IDENTIFIER, new Attribute().values(List.of(new TextNode("STD"))));

        var adeoKey = getRandomAdeoKey();

        var goldenRecordCreateRequest = new GoldenRecordCreateRequest()
                .adeoKey(adeoKey)
                .partnerRecordId(partnerRecordId)
                .modelId(modelId)
                .attributes(attributes)
                .dataPack(dataPack)
                .productBusinessUnitIdentifierCreator(GoldenRecordCreateRequest.ProductBusinessUnitIdentifierCreatorEnum.LMRU)
                .productBuReference(
                        new GoldenRecordCreateRequestProductBuReference()
                                .bu(Bu.LMRU.getBuCode())
                                .referenceCode(adeoKey));

        var goldenRecord = goldenRecordsControllerApi.v3GoldenRecordsPost()
                .body(goldenRecordCreateRequest)
                .executeAs(r -> r.then()
                        .statusCode(201)
                        .extract()
                        .response());

        goldenRecordsControllerApi
                .v2GoldenRecordsAdeoKeyGet()
                .adeoKeyPath(goldenRecord.getAdeoKey())
                .executeAs(r -> r.then().statusCode(200).extract().response());

        return goldenRecord;
    }

    public GoldenRecordUpdateRequest createGoldenRecordUpdateRequest(String modelId,
                                                         Map<String, Attribute> attributes,
                                                         List<Map<String, Attribute>> dataPack) {
        attributes.put(GOLDEN_RECORD_GTIN, attributes.remove(PARTNER_RECORD_GTIN));
        attributes.put(IS_PRODUCT_WITHOUT_GTIN, new Attribute().values(List.of(FALSE)));
        attributes.put(PRODUCT_TYPOLOGY_IDENTIFIER, new Attribute().values(List.of(new TextNode("STD"))));

        return new GoldenRecordUpdateRequest()
                .modelId(modelId)
                .attributes(attributes)
                .dataPack(dataPack);
    }

    public GoldenRecordsCreateRequestExperience createRandomGoldenRecordRequests(int recordsNumber) {
        var goldenRecordsRequest = new GoldenRecordsCreateRequestExperience();

        for (int i = 0; i < recordsNumber; i++) {
            var goldenRecordCreateRequestExperience = new GoldenRecordCreateRequestExperience()
                    .adeoKey(com.adeo.syn.utils.Random.getRandomValueId())
                    .modelId(getRandomModelId())
                    .attributes(Collections.singletonMap(com.adeo.syn.utils.Random.getRandomAttributeId(),
                            new Attribute().valueIds(Collections.singletonList(com.adeo.syn.utils.Random.getRandomValueId()))))
                    .dataPack(Collections.singletonList(Collections.singletonMap(com.adeo.syn.utils.Random.getRandomAttributeId(),
                            new Attribute().addValueIdsItem(com.adeo.syn.utils.Random.getRandomValueId()))))
                    .productBuReference(Collections.singletonList(
                            new GoldenRecordProductBuReferenceInner().bu(Bu.getRandomBU().getBuCode())
                                    .referenceCode(String.valueOf(Random.getRandomId()))))
                    .productBusinessUnitIdentifierCreator(GoldenRecordCreateRequestExperience
                            .ProductBusinessUnitIdentifierCreatorEnum.LMRU)
                    .productBusinessUnitIdentifierSubscribers(Collections.singletonList(GoldenRecordCreateRequestExperience
                            .ProductBusinessUnitIdentifierSubscribersEnum.LMRU));
            goldenRecordsRequest.addGoldenRecordsItem(goldenRecordCreateRequestExperience);
        }
        return goldenRecordsRequest;
    }
}
