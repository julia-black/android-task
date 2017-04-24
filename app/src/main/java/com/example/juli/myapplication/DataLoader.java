package com.example.juli.myapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTaskLoader<List<Article>>{
    private List<Article> data;

    @Override
    protected void onStartLoading() {
        if(data == null)
            forceLoad();
        else
            deliverResult(data);
    }

    public DataLoader(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<Article> loadInBackground() {
        data = null;
        try {
            URL url = new URL("http://sgu.ru/news.xml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            try {
                InputStream istream = conn.getInputStream();
                try {
                    istream.read(new byte[1]);
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(istream, null);
                    XmlParser xmlParser = new XmlParser();
                    data = xmlParser.readFeed(parser);
                } finally {
                    istream.close();
                }

            } finally {
                conn.disconnect();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
