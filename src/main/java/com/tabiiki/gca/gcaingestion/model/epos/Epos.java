package com.tabiiki.gca.gcaingestion.model.epos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Epos {
    private final String from;
    private final String to;
    private final List<EposLine> eposLines;
}
