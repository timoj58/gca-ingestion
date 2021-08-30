package com.tabiiki.gca.gcaingestion.model.claim.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Supplier {

    private final String supplierName;
    private final String supplierAccountNumber;
    private final String supplierContactName;
    private final String supplierContactEmail;
    private final String supplierContactPhone;

}
