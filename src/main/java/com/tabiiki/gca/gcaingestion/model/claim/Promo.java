package com.tabiiki.gca.gcaingestion.model.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Promo {
    private String promoStartDate;
    private String promoEndDate;
    private String promoDiscountName;
}
