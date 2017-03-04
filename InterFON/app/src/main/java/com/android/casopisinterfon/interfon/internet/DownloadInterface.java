package com.android.casopisinterfon.interfon.internet;


import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Interface for success and failed downloads
 */
public interface DownloadInterface {
    void onDownloadSuccess(JSONObject response);
    void onDownloadFailed(String error);
}
