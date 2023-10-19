package com.adeo.syn.tests;

import com.adeo.syn.dataproviders.PartnerRecordFactory;
import com.adeo.syn.enums.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PartnerRecordFactoryTests extends BaseTests {

    @Autowired
    private PartnerRecordFactory partnerRecordFactory;

    @Test
    public void createRecordsTest() {
        partnerRecordFactory.createRecords(2, State.PRODUCT_UPLOADED);
    }

}
