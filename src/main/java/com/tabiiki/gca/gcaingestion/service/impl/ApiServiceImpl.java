package com.tabiiki.gca.gcaingestion.service.impl;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.tabiiki.gca.gcaingestion.facade.IS3Facade;
import com.tabiiki.gca.gcaingestion.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

    private final IS3Facade s3Facade;

    @Override
    public List<S3ObjectSummary> objectSummaries(@RequestParam String folder) {
        return s3Facade.list(folder);
    }

}
