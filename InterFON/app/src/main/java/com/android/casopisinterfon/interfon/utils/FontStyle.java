package com.vesti.fonis.fonisvesti.utils;

import com.vesti.fonis.fonisvesti.R;

/**
 * Created by Sarma on 4/5/2016.
 */

public enum FontStyle {
    Small(R.style.FontStyle_Small, "Small"),
    Medium(R.style.FontStyle_Medium, "Medium"),
    Large(R.style.FontStyle_Large, "Large");

    private int resId;
    private String title;

    public int getResId() {
        return resId;
    }

    public String getTitle() {
        return title;
    }

    FontStyle(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }
}