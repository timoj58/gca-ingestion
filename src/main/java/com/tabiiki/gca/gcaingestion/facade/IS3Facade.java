package com.tabiiki.gca.gcaingestion.facade;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

public interface IS3Facade {
    void put(String key, String json);

    void put(String bucket, String key, String csv);

    void get(String key);

    List<S3ObjectSummary> list(String folder);
}
