package com.tabiiki.gca.gcaingestion.wrapper;

import com.tabiiki.gca.gcaingestion.rule.IngestionRule;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

@AllArgsConstructor
public class IngestionKeyWrapper extends Triple<String, String, List<IngestionRule>> {

    private final String key;
    private final String id;
    private final List<IngestionRule> failures;

    @Override
    public String getLeft() {
        return key;
    }

    @Override
    public String getMiddle() {
        return id;
    }

    @Override
    public List<IngestionRule> getRight() {
        return failures;
    }
}
