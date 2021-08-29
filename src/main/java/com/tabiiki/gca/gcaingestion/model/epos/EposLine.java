package com.tabiiki.gca.gcaingestion.model.epos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EposLine {
    private final String supplierNumber;
    private final String supplierName;
    private final String productCode;
    private final String productDescription;
    private final String date;
    private final String totalUnitsSold;
    private final String salesIncVat;
    private final String salesExVat;
    private final String averageSellingPrice;
    private final String returnedUnits;
    private final String returnsIncVat;
    private final String returnsExVat;
    private final String returnsAverageSellingPrice;
}
