package com.amazon.ata.kindlepublishingservice.publishing;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;

public class BookPublishRequestManager {

    Queue<BookPublishRequest> publishRequestQueue;

    @Inject
    public BookPublishRequestManager() {
        publishRequestQueue = new LinkedList<>();
    }

    public void addBookPublishRequest(BookPublishRequest request) {
        publishRequestQueue.offer(request);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        if (publishRequestQueue.isEmpty()) {
            return null;
        }
        return publishRequestQueue.poll();
    }
}
