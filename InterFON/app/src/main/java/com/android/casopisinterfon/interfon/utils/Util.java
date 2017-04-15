package com.android.casopisinterfon.interfon.utils;


import android.content.res.Resources;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;

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
}
