package com.android.casopisinterfon.interfon.internet.events;


import android.support.annotation.NonNull;

public abstract class DownloadedEvent {
    public boolean isSuccess;

    @NonNull
    public Object eventType;
}
