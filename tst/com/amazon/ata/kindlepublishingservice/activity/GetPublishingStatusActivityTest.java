package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.dagger.DaggerApplicationComponent;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class GetPublishingStatusActivityTest {

    private GetPublishingStatusActivity getPublishingStatusActivity;

    private String successfulPublishingProcessId = "publishingstatus.bdd319cb-05eb-494b-983f-6e1b983c4c46";
    private String failedPublishingProcessId = "publishingstatus.4bd41646-b1b2-4627-8304-5180c9b54e00";
    private String successfulNewVersionPublishedId = "publishingstatus.2bc206a1-5b41-4782-a260-976c0a291825";

    @BeforeEach
    public void setUp() {
        getPublishingStatusActivity = DaggerApplicationComponent.create().provideGetPublishingStatusActivity();
    }

    @Test
    public void execute_successfulPublishingProcessId_returnsSuccessfulStatus() {

        GetPublishingStatusRequest request = GetPublishingStatusRequest.builder()
                .withPublishingRecordId(successfulPublishingProcessId)
                .build();

        GetPublishingStatusResponse response = getPublishingStatusActivity.execute(request);
        List<PublishingStatusRecord> responseRecords = response.getPublishingStatusHistory();

        assertNotNull(responseRecords);
        assertTrue(responseRecords.size() == 3);

        boolean successfulStatus = false;
        for (PublishingStatusRecord record : responseRecords) {
            if (record.getStatus() == "SUCCESSFUL") {
                successfulStatus = true;
            }
        }

        assertTrue(successfulStatus);
    }

    @Test
    public void execute_failedPublishingProcess_containsFailedMessageResponse() {

        GetPublishingStatusRequest failedProcessRequest = GetPublishingStatusRequest.builder()
                .withPublishingRecordId(failedPublishingProcessId)
                .build();

        GetPublishingStatusResponse failedResponse = getPublishingStatusActivity.execute(failedProcessRequest);
        List<PublishingStatusRecord> failedResponseRecords = failedResponse.getPublishingStatusHistory();

        boolean failedResponseExists = false;
        for (PublishingStatusRecord record : failedResponseRecords) {
            if (record.getStatus() == "FAILED") {
                failedResponseExists = true;
            }
        }

        assertTrue(failedResponseExists);
        assertTrue(failedResponseRecords.size() == 3);
    }
}
