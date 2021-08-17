package com.tabiiki.gca.gcaingestion.facade;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class S3Facade implements IS3Facade {

    private final AmazonS3 amazonS3;
    private final String gcaBucket;

    @Autowired
    public S3Facade(@Value("${s3.ingestion-bucket}") String gcaBucket,
                    AmazonS3 amazonS3) {
        this.gcaBucket = gcaBucket;
        this.amazonS3 = amazonS3;
    }

    @Override
    public void put(String key, String json) {
        amazonS3.putObject(gcaBucket, key, json);
    }

    @Override
    public void put(String bucket, String key, String csv) {
        amazonS3.putObject(bucket, key, csv);
    }

    @Override
    public void get(String key) {

    }

    @Override
    public List<S3ObjectSummary> list(String folder) {
        final List<S3ObjectSummary> objectSummaries = new ArrayList<>();

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(gcaBucket)
                .withPrefix(folder);

        ListObjectsV2Result result;
        do {
            result = amazonS3.listObjectsV2(request);
            objectSummaries.addAll(result.getObjectSummaries());
        } while (result.isTruncated());


        return objectSummaries;
    }

}
