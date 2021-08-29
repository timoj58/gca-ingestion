package com.tabiiki.gca.gcaingestion.transform;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class ClaimPackTransformerTest {

    @Test
    public void transformTest() throws IOException {

        FileInputStream file = new FileInputStream(new File("src/test/resources/perfect_claim.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);

        ClaimPackTransformer.transform(workbook);

    }

}