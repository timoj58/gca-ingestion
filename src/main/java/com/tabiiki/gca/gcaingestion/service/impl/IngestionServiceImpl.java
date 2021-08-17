package com.tabiiki.gca.gcaingestion.service.impl;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.tabiiki.gca.gcaingestion.facade.IS3Facade;
import com.tabiiki.gca.gcaingestion.service.IngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngestionServiceImpl implements IngestionService {

    private final IS3Facade s3Facade;

    @Override
    public void ingest(S3EventNotification event) {
        log.info("ingesting {}", event.toJson());
        //testing deployment.  remove this (for permissions checking).
        s3Facade.put("error/"+ UUID.randomUUID(), event.toJson());
    }
}
