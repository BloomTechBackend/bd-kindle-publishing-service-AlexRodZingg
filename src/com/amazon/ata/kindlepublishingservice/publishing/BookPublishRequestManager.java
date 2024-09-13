package com.amazon.ata.kindlepublishingservice.publishing;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public final class BookPublishRequestManager {

    private final Queue<BookPublishRequest> publishRequestQueue;

    @Inject
    public BookPublishRequestManager() {
        publishRequestQueue = new ConcurrentLinkedQueue<>();
    }

    public void addBookPublishRequest(final BookPublishRequest request) {
        publishRequestQueue.offer(request);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        if (publishRequestQueue.isEmpty()) {
            return null;
        }
        return publishRequestQueue.poll();
    }

    public boolean isQueueEmpty() {
        return publishRequestQueue.isEmpty();
    }
}
