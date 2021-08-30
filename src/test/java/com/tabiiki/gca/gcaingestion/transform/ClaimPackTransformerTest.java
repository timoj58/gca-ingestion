package com.tabiiki.gca.gcaingestion.transform;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ClaimPackTransformerTest {

    @Test
    public void transformTest() throws IOException {

        FileInputStream file = new FileInputStream(new File("src/test/resources/perfect_claim.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);

        var claimPack = ClaimPackTransformer.transform(workbook, "testing");
        //need to check all the fields.  (to save messing up later).
        assertFalse(claimPack.getEpos().getEposLines().isEmpty());
        assertFalse(claimPack.getClaim().getOmittedProducts().isEmpty());
        assertFalse(claimPack.getClaim().getFundingDue().getFundingDues().isEmpty());
        assertFalse(claimPack.getClaim().getFundingPaidByInvoice().getFundingPaidByInvoiceLines().isEmpty());
        assertFalse(claimPack.getClaim().getFundingPaidByProduct().getFundingPaidByProductLines().isEmpty());
        //now need to do all the fields as well
        //claim
        //supplier
        //promo
        //auditor
        //retailer
        //and then fields in epos, calcs etc

    }

}