package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;
import com.amazonaws.services.lambda.runtime.Context;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetPublishingStatusActivity {

    private PublishingStatusDao publishingStatusDao;

    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    /**
     *
     * @param publishingStatusRequest
     * @return
     */
    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest) {

        String recordId = publishingStatusRequest.getPublishingRecordId();
        List<PublishingStatusItem> statusItems = publishingStatusDao.getPublishingStatuses(recordId);

        // convert to List<PublishingStatusRecord>
        List<PublishingStatusRecord> publishingStatusHistory = new ArrayList<>();
        for (PublishingStatusItem item : statusItems) {
            publishingStatusHistory.add(new PublishingStatusRecord(item.getStatus().toString(), item.getStatusMessage(), item.getBookId()));
        }

        return new GetPublishingStatusResponse(publishingStatusHistory);
    }
}
