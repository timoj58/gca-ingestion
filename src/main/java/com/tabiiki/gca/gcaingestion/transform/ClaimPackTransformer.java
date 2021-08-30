package com.tabiiki.gca.gcaingestion.transform;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.transform.claim.ClaimTransformer;
import com.tabiiki.gca.gcaingestion.transform.epos.EposTransformer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@UtilityClass
public class ClaimPackTransformer {

    public ClaimPack transform(Workbook workbook, String key) {
        log.info("processing {}", key);

        List<RuntimeException> exceptions = new ArrayList<>();

        return ClaimPack.builder()
                .epos(EposTransformer.transform(workbook, exceptions, key))
                .claim(ClaimTransformer.transform(workbook, exceptions, key))
                .exceptions(exceptions)
                .build();
    }


}
