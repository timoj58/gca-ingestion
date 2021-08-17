package com.tabiiki.gca.gcaingestion.facade;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@Slf4j
@Component
public class S3Facade implements IS3Facade {

    private final String gcaBucket;
    Supplier<AmazonS3> amazonS3Supplier = () -> AmazonS3ClientBuilder.standard()
            .withCredentials(new InstanceProfileCredentialsProvider(false))
            .withRegion(Regions.EU_WEST_2).build();

    @Autowired
    public S3Facade(@Value("${s3.ingestion-bucket}") String gcaBucket) {
        this.gcaBucket = gcaBucket;
    }

    @Override
    public void put(String key, String json) {
        amazonS3Supplier.get().putObject(gcaBucket, key, json);
    }

    @Override
    public void put(String bucket, String key, String csv) {
        amazonS3Supplier.get().putObject(bucket, key, csv);
    }

    @Override
    public void get(String key) {

    }

    @Override
    public List<S3ObjectSummary> list(String folder) {
        final AmazonS3 s3 = amazonS3Supplier.get();
        final List<S3ObjectSummary> objectSummaries = new ArrayList<>();

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(gcaBucket)
                .withPrefix(folder);

        ListObjectsV2Result result;
        do {
            result = s3.listObjectsV2(request);
            objectSummaries.addAll(result.getObjectSummaries());
        } while (result.isTruncated());


        return objectSummaries;
    }

}
