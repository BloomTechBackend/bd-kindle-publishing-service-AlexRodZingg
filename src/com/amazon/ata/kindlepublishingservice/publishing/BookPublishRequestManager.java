package com.amazon.ata.kindlepublishingservice.publishing;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class BookPublishRequestManager {

    private final Queue<BookPublishRequest> publishRequestQueue;

    @Inject
    public BookPublishRequestManager() {
        publishRequestQueue = new ConcurrentLinkedQueue<>();
    }

    public void addBookPublishRequest(final BookPublishRequest request) {
        BookPublishRequest newRequest = BookPublishRequest.builder()
                                .withPublishingRecordId(request.getPublishingRecordId())
                                .withBookId(request.getBookId())
                                .withTitle(request.getTitle())
                                .withAuthor(request.getAuthor())
                                .withText(request.getText())
                                .withGenre(request.getGenre())
                                .build();
        publishRequestQueue.offer(newRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        if (publishRequestQueue.isEmpty()) {
            return null;
        }
        return publishRequestQueue.poll();
    }
}
