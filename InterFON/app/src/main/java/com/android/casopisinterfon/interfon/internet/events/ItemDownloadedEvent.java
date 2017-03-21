package com.android.casopisinterfon.interfon.internet.events;


/**
 * Event class for EventBus that indicates that one article download is finished.
 */
public class ItemDownloadedEvent {
    boolean isSuccess;

    public ItemDownloadedEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
