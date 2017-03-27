package com.android.casopisinterfon.interfon.internet.events;


/**
 * Event class for EventBus that indicates that one article download is finished.
 */
public class ItemDownloadedEvent extends DownloadedEvent{

    public ItemDownloadedEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ItemDownloadedEvent(boolean isSuccess, Object eventType) {
        this.isSuccess = isSuccess;
        this.eventType = eventType;
    }
}
