package com.tabiiki.gca.gcaingestion.util;

import com.amazonaws.services.s3.model.S3Object;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@UtilityClass
public class S3ObjectConverter {

    public String convert(S3Object s3Object) {
        try {
            return StreamUtils.copyToString(s3Object.getObjectContent(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("conversion error", e);
        }

        return "";
    }
}
