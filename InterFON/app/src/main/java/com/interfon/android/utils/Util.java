package com.interfon.android.utils;


import android.content.res.Resources;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;

import com.interfon.android.internet.URLImageParser;

/**
 * Utility class
 */
public class Util {

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static int dp(int value, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.getDisplayMetrics());
    }

    public static int sp(int value, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, resources.getDisplayMetrics());
    }

    public static Spanned fromHtml(String articleDescription, URLImageParser p, Html.TagHandler tagHandler) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(articleDescription, Html.FROM_HTML_MODE_LEGACY, p, tagHandler);
        } else {
            return Html.fromHtml(articleDescription, p, tagHandler);
        }
    }
}
