package com.android.casopisinterfon.interfon.internet.events;


/**
 * Event class for EventBus that indicates that data list download is finished.
 */
public class ListDownloadedEvent {
    boolean isSuccess;

    public ListDownloadedEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
