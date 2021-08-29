package com.tabiiki.gca.gcaingestion.transform;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.transform.claim.ClaimTransformer;
import com.tabiiki.gca.gcaingestion.transform.epos.EposTransformer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;


@Slf4j
@UtilityClass
public class ClaimPackTransformer {

    public ClaimPack transform(Workbook workbook) {

        return ClaimPack.builder()
                .epos(EposTransformer.transform(workbook))
                .claim(ClaimTransformer.transform(workbook))
                .build();
    }

}
