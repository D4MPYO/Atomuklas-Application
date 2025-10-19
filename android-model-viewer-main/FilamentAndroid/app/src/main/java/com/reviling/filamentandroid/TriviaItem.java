// TriviaItem.java
package com.reviling.filamentandroid;

public class TriviaItem {
    private final String text;
    private final int imageResId;

    public TriviaItem(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean hasImage() {
        return imageResId != 0;
    }
}
