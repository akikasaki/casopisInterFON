package com.vesti.fonis.fonisvesti.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Sarma on 4/5/2016.
 */
public class Preferences {
    private final static String FONT_STYLE = "FONT_STYLE";
    private static int stateChanged = 0;

    private final Context context;

    public Preferences(Context context) {
        this.context = context;
    }

    protected SharedPreferences open() {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor edit() {
        return open().edit();
    }

    public FontStyle getFontStyle() {
        return FontStyle.valueOf(open().getString(FONT_STYLE,
                FontStyle.Large.name()));
    }

    public void setFontStyle(FontStyle style) {
        edit().putString(FONT_STYLE, style.name()).commit();
    }

    public static void notifyStateChanged() {
        stateChanged = 1;
    }

    public static boolean isChanged() {
        if (1 == stateChanged) {
            Log.d(Util.TAG, String.valueOf(stateChanged));
            stateChanged = 0;
            return true;
        } else return false;
    }
}

