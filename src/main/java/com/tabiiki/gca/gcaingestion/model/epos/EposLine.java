package com.tabiiki.gca.gcaingestion.model.epos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EposLine {
    private String supplierNumber;
    private String supplierName;
    private String productCode;
    private String productDescription;
    private String date;
    private String totalUnitsSold;
    private String salesIncVat;
    private String salesExVat;
    private String averageSellingPrice;
    private String returnedUnits;
    private String returnsIncVat;
    private String returnsExVat;
    private String returnsAverageSellingPrice;
}
