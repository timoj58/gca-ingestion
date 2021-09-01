package com.tabiiki.gca.gcaingestion.service;

import com.tabiiki.gca.gcaingestion.model.ClaimPack;
import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Optional;

public interface PublishService {

    void publish(Triple<String, Optional<ClaimPack>, List<IngestionRule>> finalClaim);
}
