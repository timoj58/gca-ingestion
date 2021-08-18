package com.tabiiki.gca.gcaingestion.message;

import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class IngestionMessage {
    private final IngestionStatus ingestionStatus;
    private final UUID id;
    private final List<IngestionRule> ruleFailures;
}
