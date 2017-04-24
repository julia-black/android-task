package com.example.juli.myapplication;

/**
 * Created by Juli on 17.04.2017.
 */

public class Article {
    private String title;
    private String description;
    private String pubDate;
    private String link;

    public Article(String title, String description, String pubData, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubData;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String toString(){
        return "Title: "+ title + "\nDate: "+ pubDate +"\nLink: " + link;
    }
}
