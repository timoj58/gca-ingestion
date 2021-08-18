package com.tabiiki.gca.gcaingestion.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface ApiService {
    String getFinalClaim(@PathVariable UUID id);
}
