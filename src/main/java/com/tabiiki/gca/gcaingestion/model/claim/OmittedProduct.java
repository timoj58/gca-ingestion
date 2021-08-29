package com.tabiiki.gca.gcaingestion.model.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OmittedProduct {

    private final String productCode;
    private final String productDescription;
    private final String from;
    private final String to;
    private final String total;
}
