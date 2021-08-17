package com.tabiiki.gca.gcaingestion.service;

import com.amazonaws.services.s3.event.S3EventNotification;

public interface IngestionService {
    void ingest(S3EventNotification event);
}
