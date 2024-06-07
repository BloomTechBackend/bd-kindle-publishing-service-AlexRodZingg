package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;

import javax.inject.Inject;

public class BookPublishTask implements Runnable {

    private BookPublishRequestManager bookPublishRequestManager;
    private PublishingStatusDao publishingStatusDao;
    private CatalogDao catalogDao;

    // processes a publish request form the BookPublishRequestManager
    // Return immediately if no requests available
    // Use CatalogDao to publish new books to kindle catalog
    // and PublishingDao
    // exceptions may be thrown in run method, figure them out

    @Inject
    public BookPublishTask(BookPublishRequestManager bookPublishRequestManager, PublishingStatusDao publishingStatusDao, CatalogDao catalogDao) {
        this.bookPublishRequestManager = bookPublishRequestManager;
        this.publishingStatusDao = publishingStatusDao;
        this.catalogDao = catalogDao;
    }

    @Override
    public void run() {
        while (!bookPublishRequestManager.isQueueEmpty()) {
            BookPublishRequest request = bookPublishRequestManager.getBookPublishRequestToProcess();
            if (request == null) continue;

            publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.IN_PROGRESS,
                    request.getBookId());

            KindleFormattedBook book = KindleFormatConverter.format(request);

            try {
                catalogDao.createOrUpdateBook(book);
            } catch (BookNotFoundException e) {
                publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.FAILED, request.getBookId(),
                        e.getMessage());

            }
        }
    }
}
