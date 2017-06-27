package com.interfon.android.activity.article_view;


import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;

import com.interfon.android.internet.URLImageParser;
import com.interfon.android.utils.Util;

import org.xml.sax.XMLReader;

/**
 * Class will be used to format article text retrieved from the server with all its html code.
 */
class ArticleTextStyle implements TagHandler {

    private final URLImageParser imageParser;
    private final StringBuilder builder;

    ArticleTextStyle(String articleText, URLImageParser parser) {
        this.builder = new StringBuilder(articleText);
        this.imageParser = parser;
    }

    /**
     * Format the provided text.
     *
     * @return formatted string.
     */
    Spanned format() {
        int indexToLookFrom = -1;
        // Deletes the first image tag because they appear to be two <img> tags for one same picture.
        while ((indexToLookFrom = builder.indexOf("<img", indexToLookFrom + 1)) != -1) {
            // Delete first image tag
            builder.delete(indexToLookFrom, builder.indexOf("<img", indexToLookFrom + 1));
            // Check if this <img> is a smiley face
            int end = builder.indexOf(">", indexToLookFrom);
            String imageString = builder.substring(indexToLookFrom, end);
            if (imageString.contains("images/smilies")) {
                // Just delete <img> containing smiley face for now
                // TODO - parse small smiley
                builder.delete(indexToLookFrom, end + 1);
            }
        }


        Spanned htmlSpan = Util.fromHtml(builder.toString(), imageParser, this);
        return htmlSpan;
    }


    boolean first = true;
    String parent = null;
    int index = 1;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {


        if (tag.equals("ul")) parent = "ul";
        else if (tag.equals("ol")) parent = "ol";
        if (tag.equals("li")) {
            if (parent.equals("ul")) {
                if (first) {
                    output.append("\n\t•");
                    first = false;
                } else {
                    first = true;
                }
            } else {
                if (first) {
                    output.append("\n\t" + index + ". ");
                    first = false;
                    index++;
                } else {
                    first = true;
                }
            }
        }
    }
}
