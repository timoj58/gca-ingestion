package com.tabiiki.gca.gcaingestion.transform.claim;

import com.tabiiki.gca.gcaingestion.exception.ClaimHeaderException;
import com.tabiiki.gca.gcaingestion.model.claim.ClaimHeader;
import com.tabiiki.gca.gcaingestion.util.CellUtils;
import com.tabiiki.gca.gcaingestion.util.TransformExceptionHandler;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

@UtilityClass
public class ClaimHeaderTransformer {

    public ClaimHeader transform(Sheet sheet, List<RuntimeException> exceptions, String key) {

        var claimIdentificationDate = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimIdentificationDate(sheet), key);
        var claimNumber = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimNumber(sheet), key);
        var claimAmount = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimAmount(sheet), key);
        var claimCurrency = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimCurrency(sheet), key);
        var claimType = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimType(sheet), key);
        var rootCauseSummary = TransformExceptionHandler.handle(exceptions::add, () -> transformRootCause(sheet), key);
        var claimDescription = TransformExceptionHandler.handle(exceptions::add, () -> transformClaimDescription(sheet), key);

        return ClaimHeader.builder()
                .claimIdentificationDate(claimIdentificationDate.map(m -> (String) m).orElse(""))
                .claimNumber(claimNumber.map(m -> (String) m).orElse(""))
                .claimAmount(claimAmount.map(m -> (String) m).orElse(""))
                .claimCurrency(claimCurrency.map(m -> (String) m).orElse(""))
                .claimType(claimType.map(m -> (String) m).orElse(""))
                .rootCauseSummary(rootCauseSummary.map(m -> (String) m).orElse(""))
                .claimDescription(claimDescription.map(m -> (String) m).orElse(""))
                .build();
    }

    private String transformClaimIdentificationDate(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Claim Identification Date", new ClaimHeaderException("claimIdentificationDate"));
    }

    private String transformClaimNumber(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Claim Number", new ClaimHeaderException("claimNumber"));
    }

    private String transformClaimAmount(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Claim Amount", new ClaimHeaderException("claimAmount"));
    }

    private String transformClaimCurrency(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Claim Currency", new ClaimHeaderException("claimCurrency"));
    }

    private String transformClaimType(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Claim Type", new ClaimHeaderException("claimType"));
    }

    private String transformRootCause(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Root Cause Summary", new ClaimHeaderException("rootCauseSummary"));
    }

    private String transformClaimDescription(Sheet sheet) throws ClaimHeaderException {
        return CellUtils.search(sheet, "Claim Description/Explanation", new ClaimHeaderException("claimDescription"));
    }
}
