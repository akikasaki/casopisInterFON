package com.android.casopisinterfon.interfon.utils;

import android.os.Environment;

/**
 * Created by Aleksa on 15.3.2017.
 */

public class ExternalStorageCheck {


    /**
     * Method to check if the device has a SD card mounted
     * @return state of external storage
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    //TODO implement saving articles on sd card if present

}
