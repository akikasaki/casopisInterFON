package com.android.casopisinterfon.interfon.internet;

/**
 * Data for REST api at 'casopisinterfon.org' - see more at https://wordpress.org/plugins/json-api/other_notes/
 */
public class UrlData {
    public static final String BASE_URL = "http://casopisinterfon.org/api/";

    // Posts url data
    public static final String GET_POSTS = BASE_URL.concat("get_posts/");
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_COUNT = "count";
    public static final String PARAM_UNESCAPE_UNICODE = "json_unescaped_unicode";
    // Param which indicates which object to exclude from response
    public static final String PARAM_EXCLUDE = "exclude";

}
