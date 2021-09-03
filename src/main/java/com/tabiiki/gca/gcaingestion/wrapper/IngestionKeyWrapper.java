package com.tabiiki.gca.gcaingestion.wrapper;

import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class IngestionKeyWrapper {

    private final String key;
    private final String id;
    private final List<IngestionRule> failures;

}
