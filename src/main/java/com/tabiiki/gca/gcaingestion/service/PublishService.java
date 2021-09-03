package com.tabiiki.gca.gcaingestion.service;

import com.tabiiki.gca.gcaingestion.wrapper.ClaimPackWrapper;

public interface PublishService {

    void publish(ClaimPackWrapper finalClaim);
}
