package com.android.casopisinterfon.interfon.internet.events;


import com.android.casopisinterfon.interfon.model.Category;

/**
 * Event class for EventBus that indicates that data list download is finished.
 */
public class ListDownloadedEvent extends DownloadedEvent{

    public ListDownloadedEvent(boolean isSuccess) {
        super.isSuccess = isSuccess;
    }

    public ListDownloadedEvent(boolean isSuccess, Category eventType) {
        this.isSuccess = isSuccess;
        this.eventType = eventType;
    }
}
