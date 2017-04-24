package com.example.juli.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juli on 24.04.2017.
 */

public class XmlParser {
    public List<Article> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Article> news = new ArrayList<>();
        String title = null;
        String description = null;
        String pubDate;
        String link = null;

        parser.next();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                title = null;
                description = null;
                link = null;
            } else if (name.equals("title")) {
                title = read(parser, "title");
            } else if (name.equals("link")) {
                link = read(parser,"link");
            } else if (name.equals("description")) {
                description = read(parser, "description");
            } else if (name.equals("pubDate")) {
                pubDate = read(parser, "pubDate");
                news.add(new Article(title, description, pubDate, link));
            } else
                skip(parser);
        }
        return news;
    }

    private String read(XmlPullParser parser, String text) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, text);
        String title = parser.nextText();
        parser.require(XmlPullParser.END_TAG, null, text);
        return title;
    }

    private void skip(XmlPullParser parser)  throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            continue;
        }
    }
}
