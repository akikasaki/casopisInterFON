package com.android.casopisinterfon.interfon.activity.article_view;


import android.text.Spanned;

import com.android.casopisinterfon.interfon.internet.URLImageParser;
import com.android.casopisinterfon.interfon.utils.Util;

/**
 * Class will be used to format article text retrieved from the server with all its html code.
 */
class ArticleTextStyle {

    private final URLImageParser imageParser;
    private final StringBuilder builder;

    ArticleTextStyle(String articleText, URLImageParser parser){
        this.builder = new StringBuilder(articleText);
        this.imageParser = parser;
    }

    /**
     * Format the provided text.
     * @return formatted string.
     */
    Spanned format(){
        int indexToLookFrom = -1;
        while((indexToLookFrom = builder.indexOf("<img", indexToLookFrom + 1)) != -1){
            builder.delete(indexToLookFrom, builder.indexOf("<img", indexToLookFrom+1));
        }

        Spanned htmlSpan = Util.fromHtml(builder.toString(), imageParser);
        return htmlSpan;
    }
}
