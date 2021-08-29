package com.tabiiki.gca.gcaingestion.model.claim.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Auditor {
    private final String auditCompany;
    private final String auditorName;
    private final String auditorEmail;
    private final String auditorPhone;

}
