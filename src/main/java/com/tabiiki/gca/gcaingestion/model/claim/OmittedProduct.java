package com.tabiiki.gca.gcaingestion.model.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OmittedProduct {

    private String productCode;
    private String productDescription;
    private String from;
    private String to;
    private String total;
}
