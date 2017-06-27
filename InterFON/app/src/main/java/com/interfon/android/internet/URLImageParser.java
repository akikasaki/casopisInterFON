package com.interfon.android.internet;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import com.interfon.android.utils.URLDrawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLImageParser implements Html.ImageGetter {
    Context c;
    TextView container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public URLImageParser(TextView t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {

            if (result == null) return;
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(0, 0, 0 + result.getBounds().right, 0
                    + result.getBounds().bottom);

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            //For drawing the image correctly, can overlap text if not present
            URLImageParser.this.container.invalidate();
            URLImageParser.this.container.setHeight((URLImageParser.this.container.getHeight()
                    + result.getBounds().bottom));
            //For setting the image gravity within the textView

            //Nijedan gravity ne sredjuje velicinu slike vec samo njenu poziciju
            //Menjanje velicine container-a ne menja velicinu slike
            //Moguce je da se slika menja preko Drawable ali ne znam metodu/nacin za to
            //Takodje neki artikli ne ispadaju kako treba jer postoji dupla slika, pa je mozda to bolje prvo resiti
            // TODO resizing images if needed
//            URLImageParser.this.container.setGravity(Gravity.CENTER);
            // Pre ICS
            URLImageParser.this.container.setEllipsize(null);
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");

                float ratio = 1;
                try {
                    ratio = (float) container.getWidth() / (float) drawable.getIntrinsicWidth();
                } catch (Exception e) {
                }

                drawable.setBounds(0, 0, container.getWidth() == 0 ? drawable.getIntrinsicWidth() : container.getWidth(),
                        (int) (drawable.getIntrinsicHeight() * ratio));
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {

            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream stream = urlConnection.getInputStream();

            return stream;

        }
    }
}
