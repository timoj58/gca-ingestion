package com.tabiiki.gca.gcaingestion.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ApiService {

    List<S3ObjectSummary> objectSummaries(@RequestParam String folder);
}
