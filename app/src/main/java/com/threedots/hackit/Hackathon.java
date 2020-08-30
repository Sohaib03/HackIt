package com.threedots.hackit;

public class Hackathon {
    public String imgUrl;
    public String title;
    public String date;
    public String url;
    public String logoUrl;

    public Hackathon(String title, String imgUrl, String date, String url, String logoUrl) {
        this.title = title;
        this.date = date;
        this.imgUrl = imgUrl;
        this.url = url;
        this.logoUrl = logoUrl;
    }
}
