package com.itau.challenge.balanceapi.adapters.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SqsConfigTest {

    private final SqsConfig sqsConfig = new SqsConfig();

    @Test
    void shouldCreateSqsAsyncClientBeanSuccessfully() {
        // Manually injects the values, simulating Spring's @Value behavior
        ReflectionTestUtils.setField(sqsConfig, "region", "sa-east-1");
        ReflectionTestUtils.setField(sqsConfig, "accessKey", "test");
        ReflectionTestUtils.setField(sqsConfig, "secretKey", "test");
        ReflectionTestUtils.setField(sqsConfig, "sqsEndpoint", "http://localhost:4566");

        SqsAsyncClient client = sqsConfig.sqsAsyncClient();

        assertNotNull(client);
        client.close();
    }
}