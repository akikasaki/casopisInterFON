package com.interfon.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper class for working with shared preferences. Store font style preferences.
 */
public class FontPreferences {
    private static final String TAG = "FontPreferences";

    private final static String FONT_STYLE = "FONT_STYLE";
    private static int stateChanged = 0;

    private final Context context;

    public FontPreferences(Context context) {
        this.context = context;
    }

    protected SharedPreferences open() {
        return context.getSharedPreferences("fontStylePrefs", Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor edit() {
        return open().edit();
    }

    public FontStyle getFontStyle() {
        return FontStyle.valueOf(open().getString(FONT_STYLE,
                FontStyle.Small.name()));
    }

    public void setFontStyle(FontStyle style) {
        edit().putString(FONT_STYLE, style.name()).commit();
    }

    public static void notifyStateChanged() {
        stateChanged = 1;
    }

    public static boolean isChanged() {
        if (1 == stateChanged) {
            stateChanged = 0;
            return true;
        } else return false;
    }
}

