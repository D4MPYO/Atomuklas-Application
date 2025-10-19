package com.reviling.filamentandroid;

public class ScreenItem {
    private String title, description, description2;
    private int screenImg, textColor, shadowColor, backgroundImg;

    public ScreenItem(String title, String description, int screenImg, int textColor, int shadowColor, int backgroundImg) {
        this.title = title;
        this.description = description;
        this.screenImg = screenImg;
        this.textColor = textColor;
        this.shadowColor = shadowColor;
        this.backgroundImg = backgroundImg;
        this.description2 = null; // Default to null for slides that don't need it
    }

    public ScreenItem(String title, String description, String description2, int screenImg, int textColor, int shadowColor, int backgroundImg) {
        this.title = title;
        this.description = description;
        this.description2 = description2; // Only set for slides that need it
        this.screenImg = screenImg;
        this.textColor = textColor;
        this.shadowColor = shadowColor;
        this.backgroundImg = backgroundImg;
    }

    public int getBackgroundImg() { return backgroundImg; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDescription2() { return description2; }
    public int getScreenImg() { return screenImg; }
    public int getTextColor() { return textColor; }
    public int getShadowColor() { return shadowColor; }
}
