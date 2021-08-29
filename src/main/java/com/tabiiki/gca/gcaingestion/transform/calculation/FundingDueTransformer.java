package com.tabiiki.gca.gcaingestion.transform.calculation;

import com.tabiiki.gca.gcaingestion.exception.FundingDueException;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDue;
import com.tabiiki.gca.gcaingestion.model.claim.calculation.FundingDueLine;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FundingDueTransformer {
    public FundingDue transform(Sheet sheet) throws FundingDueException {
        List<FundingDueLine> fundingDueLines = new ArrayList<>();

        var claimTotal = "";
        var lessFundingClaimed = "";
        var totalFundingsDue = "";

        return FundingDue.builder()
                .claimTotal(claimTotal)
                .lessFundingClaimed(lessFundingClaimed)
                .totalFundingDue(totalFundingsDue)
                .fundingDues(fundingDueLines)
                .build();
    }
}
