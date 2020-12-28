package com.application.radiofreq;

public class ParseItem {
    private String imgUrl;
    private String title;
    private String frequency;

    public ParseItem() {

    }

    public ParseItem(String imgUrl, String title, String frequency) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.frequency = frequency;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
