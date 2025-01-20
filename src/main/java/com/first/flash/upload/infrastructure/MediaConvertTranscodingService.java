package com.first.flash.upload.infrastructure;

import com.first.flash.upload.application.TranscodingService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.AacCodingMode;
import software.amazon.awssdk.services.mediaconvert.model.AacSettings;
import software.amazon.awssdk.services.mediaconvert.model.AccelerationMode;
import software.amazon.awssdk.services.mediaconvert.model.AccelerationSettings;
import software.amazon.awssdk.services.mediaconvert.model.AudioCodec;
import software.amazon.awssdk.services.mediaconvert.model.AudioCodecSettings;
import software.amazon.awssdk.services.mediaconvert.model.AudioDefaultSelection;
import software.amazon.awssdk.services.mediaconvert.model.AudioDescription;
import software.amazon.awssdk.services.mediaconvert.model.AudioSelector;
import software.amazon.awssdk.services.mediaconvert.model.ContainerSettings;
import software.amazon.awssdk.services.mediaconvert.model.ContainerType;
import software.amazon.awssdk.services.mediaconvert.model.CreateJobRequest;
import software.amazon.awssdk.services.mediaconvert.model.CreateJobResponse;
import software.amazon.awssdk.services.mediaconvert.model.H264RateControlMode;
import software.amazon.awssdk.services.mediaconvert.model.H264SceneChangeDetect;
import software.amazon.awssdk.services.mediaconvert.model.H264Settings;
import software.amazon.awssdk.services.mediaconvert.model.HlsGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.HlsSettings;
import software.amazon.awssdk.services.mediaconvert.model.Input;
import software.amazon.awssdk.services.mediaconvert.model.InputRotate;
import software.amazon.awssdk.services.mediaconvert.model.InputTimecodeSource;
import software.amazon.awssdk.services.mediaconvert.model.JobSettings;
import software.amazon.awssdk.services.mediaconvert.model.M3u8Settings;
import software.amazon.awssdk.services.mediaconvert.model.Output;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroup;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.OutputSettings;
import software.amazon.awssdk.services.mediaconvert.model.StatusUpdateInterval;
import software.amazon.awssdk.services.mediaconvert.model.TimecodeConfig;
import software.amazon.awssdk.services.mediaconvert.model.TimecodeSource;
import software.amazon.awssdk.services.mediaconvert.model.VideoCodec;
import software.amazon.awssdk.services.mediaconvert.model.VideoCodecSettings;
import software.amazon.awssdk.services.mediaconvert.model.VideoDescription;
import software.amazon.awssdk.services.mediaconvert.model.VideoSelector;

@Service
@RequiredArgsConstructor
public class MediaConvertTranscodingService implements TranscodingService {

    private final MediaConvertClient mediaConvertClient;

    @Value("${aws.s3.output-bucket.video}")
    private String videoOutputBucketName;

    @Value("${aws.media-convert.role-arn}")
    private String mediaConvertRoleArn;

    @Value("${aws.media-convert.queue-arn}")
    private String mediaConvertQueueArn;

    @Override
    public String transcodeVideo(final String inputStorageUrl) {
        String outputDestination = "s3://" + videoOutputBucketName + "/videos/";
        OutputGroup hlsOutputGroup = OutputGroup.builder()
                                                .name("Apple HLS")
                                                .outputGroupSettings(
                                                    OutputGroupSettings.builder()
                                                                       .type("HLS_GROUP_SETTINGS")
                                                                       .hlsGroupSettings(
                                                                           HlsGroupSettings.builder()
                                                                                           .segmentLength(
                                                                                               4)
                                                                                           .destination(
                                                                                               outputDestination)
                                                                                           .minSegmentLength(
                                                                                               0)
                                                                                           .directoryStructure(
                                                                                               "SINGLE_DIRECTORY")
                                                                                           .build())
                                                                       .build())
                                                .outputs(
                                                    createHlsOutput("640x360_1.2mbps_qvbr", 360,
                                                        1200000),
                                                    createHlsOutput("720x480_1.5mbps_qvbr", 480,
                                                        1500000),
                                                    createHlsOutput("1280x720_4mbps_qvbr", 720,
                                                        4000000)
                                                )
                                                .build();

        JobSettings jobSettings = JobSettings.builder()
                                             .inputs(
                                                 Input.builder()
                                                      .fileInput(inputStorageUrl)
                                                      .audioSelectors(
                                                          Map.of("Audio Selector 1",
                                                              AudioSelector.builder()
                                                                           .defaultSelection(
                                                                               AudioDefaultSelection.DEFAULT)
                                                                           .build())
                                                      )
                                                      .videoSelector(
                                                          VideoSelector.builder()
                                                                       .rotate(InputRotate.AUTO)
                                                                       .build())
                                                      .timecodeSource(InputTimecodeSource.ZEROBASED)
                                                      .build()
                                             )
                                             .outputGroups(hlsOutputGroup)
                                             .timecodeConfig(TimecodeConfig.builder().source(
                                                 TimecodeSource.ZEROBASED).build())
                                             .build();

        CreateJobRequest createJobRequest = CreateJobRequest.builder()
                                                            .role(
                                                                mediaConvertRoleArn)
                                                            .queue(mediaConvertQueueArn)
                                                            .settings(jobSettings)
                                                            .accelerationSettings(
                                                                AccelerationSettings.builder().mode(
                                                                                        AccelerationMode.DISABLED)
                                                                                    .build())
                                                            .statusUpdateInterval(
                                                                StatusUpdateInterval.SECONDS_60)
                                                            .priority(0)
                                                            .build();

        CreateJobResponse response = mediaConvertClient.createJob(createJobRequest);

        return response.job().id();
    }

    private Output createHlsOutput(final String nameModifier, final int height,
        final int maxBitrate) {
        return Output.builder()
                     .nameModifier(nameModifier)
                     .containerSettings(
                         ContainerSettings.builder()
                                          .container(ContainerType.M3_U8)
                                          .m3u8Settings(M3u8Settings.builder().build())
                                          .build()
                     )
                     .videoDescription(
                         VideoDescription.builder()
                                         .height(height)
                                         .codecSettings(
                                             VideoCodecSettings.builder()
                                                               .codec(VideoCodec.H_264)
                                                               .h264Settings(
                                                                   H264Settings.builder()
                                                                               .maxBitrate(
                                                                                   maxBitrate)
                                                                               .rateControlMode(
                                                                                   H264RateControlMode.QVBR)
                                                                               .sceneChangeDetect(
                                                                                   H264SceneChangeDetect.TRANSITION_DETECTION)
                                                                               .build()
                                                               )
                                                               .build()
                                         )
                                         .build()
                     )
                     .audioDescriptions(
                         AudioDescription.builder()
                                         .codecSettings(
                                             AudioCodecSettings.builder()
                                                               .codec(AudioCodec.AAC)
                                                               .aacSettings(
                                                                   AacSettings.builder()
                                                                              .bitrate(96000)
                                                                              .codingMode(
                                                                                  AacCodingMode.CODING_MODE_2_0)
                                                                              .sampleRate(48000)
                                                                              .build()
                                                               )
                                                               .build()
                                         )
                                         .build()
                     )
                     .outputSettings(
                         OutputSettings.builder()
                                       .hlsSettings(HlsSettings.builder().build())
                                       .build()
                     )
                     .build();
    }
}
