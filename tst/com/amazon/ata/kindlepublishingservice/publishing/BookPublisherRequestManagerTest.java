package com.amazon.ata.kindlepublishingservice.publishing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.testng.AssertJUnit.assertEquals;

public class BookPublisherRequestManagerTest {

    private BookPublishRequestManager manager;

    @BeforeEach
    public void setUp() {
        manager = new BookPublishRequestManager();
    }

    @Test
    void addBookPublishRequest_and_getBookPublishRequestToProcess_requestIsAddedToQueue_and_retrieved() {

        BookPublishRequest request = BookPublishRequest.builder().build();

        manager.addBookPublishRequest(request);

        assertEquals(request, manager.getBookPublishRequestToProcess());
    }
}
