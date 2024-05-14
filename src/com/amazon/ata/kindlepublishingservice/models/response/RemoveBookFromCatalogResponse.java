package com.amazon.ata.kindlepublishingservice.models.response;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;

public class RemoveBookFromCatalogResponse {

    private CatalogItemVersion removedBook;

    public RemoveBookFromCatalogResponse(CatalogItemVersion removedBook) {
        this.removedBook = removedBook;
    }

    public CatalogItemVersion getRemovedBook() {
        return removedBook;
    }

    public void setRemovedBook(CatalogItemVersion removedBook) {
        this.removedBook = removedBook;
    }
}
