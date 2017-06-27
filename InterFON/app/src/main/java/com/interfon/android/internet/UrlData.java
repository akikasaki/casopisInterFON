package com.interfon.android.internet;

/**
 * Data for REST api at 'casopisinterfon.org' - see more at https://wordpress.org/plugins/json-api/other_notes/
 */
class UrlData {
    private static final String BASE_URL = "http://casopisinterfon.org/api/";

    // Posts url data
    static final String GET_POST = BASE_URL.concat("get_post/");
    static final String GET_POSTS = BASE_URL.concat("get_posts/");
    static final String GET_POSTS_BY_CAT = BASE_URL.concat("get_category_posts/");

    static final String PARAM_ARTICLE_ID = "id";
    static final String PARAM_CAT_ID = "category_id";
    static final String PARAM_PAGE = "page";
    static final String PARAM_COUNT = "count";
    static final String PARAM_UNESCAPE_UNICODE_OPTION = "json_unescaped_unicode";
    // Param which indicates which object to exclude from response
    static final String PARAM_EXCLUDE_OPTION = "exclude";

}
