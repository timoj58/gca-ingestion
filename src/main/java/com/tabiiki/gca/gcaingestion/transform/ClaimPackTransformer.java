package com.tabiiki.gca.gcaingestion.transform;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.model.claim.Claim;
import com.tabiiki.gca.gcaingestion.model.epos.Epos;
import com.tabiiki.gca.gcaingestion.transform.claim.ClaimTransformer;
import com.tabiiki.gca.gcaingestion.transform.epos.EposTransformer;
import com.tabiiki.gca.gcaingestion.util.TransformerExceptionHandler;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@UtilityClass
public class ClaimPackTransformer {

    public ClaimPack transform(Workbook workbook) {

        List<RuntimeException> exceptions = new ArrayList<>();

        var epos = TransformerExceptionHandler.handle(exceptions::add, () ->EposTransformer.transform(workbook));
        var claim = TransformerExceptionHandler.handle(exceptions::add, () ->ClaimTransformer.transform(workbook));

        return ClaimPack.builder()
                .epos(epos.map(m -> (Epos)m).orElseGet(() -> Epos.builder().build()))
                .claim(claim.map(m -> (Claim) m).orElseGet(() -> Claim.builder().build()))
                .exceptions(exceptions)
                .build();
    }



}
