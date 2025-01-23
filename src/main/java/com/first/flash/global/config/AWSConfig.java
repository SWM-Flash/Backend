package com.first.flash.global.config;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.DescribeEndpointsRequest;
import software.amazon.awssdk.services.mediaconvert.model.DescribeEndpointsResponse;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Slf4j
public class AWSConfig {

    @Bean
    public S3Client s3Client(final AwsCredentialsProvider credentialsProvider,
        final AwsRegionProvider regionProvider) {
        return S3Client.builder()
                       .credentialsProvider(credentialsProvider)
                       .region(regionProvider.getRegion())
                       .build();
    }

    @Bean
    public MediaConvertClient mediaConvertClient(final AwsCredentialsProvider credentialsProvider,
        final AwsRegionProvider regionProvider) {
        DescribeEndpointsResponse describeEndpointsResponse = MediaConvertClient.builder()
                                                                                .credentialsProvider(
                                                                                    credentialsProvider)
                                                                                .region(
                                                                                    regionProvider.getRegion())
                                                                                .build()
                                                                                .describeEndpoints(
                                                                                    DescribeEndpointsRequest.builder()
                                                                                                            .maxResults(
                                                                                                                20)
                                                                                                            .build()
                                                                                );

        String endPointUrl = describeEndpointsResponse.endpoints().get(0).url();
        return MediaConvertClient.builder()
                                 .credentialsProvider(
                                     credentialsProvider)
                                 .region(
                                     regionProvider.getRegion())
                                 .endpointOverride(URI.create(endPointUrl))
                                 .build();
    }
}
