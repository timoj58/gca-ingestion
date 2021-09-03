package com.tabiiki.gca.gcaingestion.util;

import com.amazonaws.services.s3.event.S3EventNotification;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.Arrays;
import java.util.UUID;

public class SqsTestMessageBuilder {

    public static Message testMessage(UUID key) {
        return Message.builder().body(
                new S3EventNotification(
                        Arrays.asList(
                                createRecord(key),
                                createRecord(UUID.randomUUID()),
                                createRecord(UUID.randomUUID())
                        )
                ).toJson()
        ).build();
    }

    private static S3EventNotification.S3EventNotificationRecord createRecord(UUID key){
       return  new S3EventNotification.S3EventNotificationRecord(
                "test",
                "test",
                "test",
                null,
                "test",
                null,
                null,
                new S3EventNotification.S3Entity(
                        "test",
                        null,
                        new S3EventNotification.S3ObjectEntity(
                                key.toString(),
                                0L,
                                "test",
                                "test",
                                "test"
                        ),
                        "test"),
                null,
                null
        );

    }
}
