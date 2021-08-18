package com.tabiiki.gca.gcaingestion.service.impl;

import com.tabiiki.gca.gcaingestion.facade.IS3Facade;
import com.tabiiki.gca.gcaingestion.service.ApiService;
import com.tabiiki.gca.gcaingestion.util.S3ObjectConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

    private final IS3Facade s3Facade;

    @Override
    public String getFinalClaim(UUID id) {
        return S3ObjectConverter.convert(s3Facade.get("final/" + id));
    }

}
