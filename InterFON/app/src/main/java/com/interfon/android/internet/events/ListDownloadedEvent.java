package com.interfon.android.internet.events;


import com.interfon.android.model.Category;

/**
 * Event class for EventBus that indicates that data list download is finished.
 */
public class ListDownloadedEvent extends DownloadedEvent{

    public ListDownloadedEvent(boolean isSuccess) {
        super.isSuccess = isSuccess;
        super.eventType = Category.getCategoryByPagePos(0);
    }

    public ListDownloadedEvent(boolean isSuccess, Category eventType) {
        this.isSuccess = isSuccess;
        this.eventType = eventType;
    }
}
