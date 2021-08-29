package com.tabiiki.gca.gcaingestion.model.claim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Promo {
    private final String promoStartDate;
    private final String promoEndDate;
    private final String promoDiscountName;
}
