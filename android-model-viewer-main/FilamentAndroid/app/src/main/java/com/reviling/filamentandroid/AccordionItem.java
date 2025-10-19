package com.reviling.filamentandroid;

public class AccordionItem {
    private final String header;
    private final String subheader;
    private final String body;

    public AccordionItem(String header, String subheader, String body) {
        this.header = header;
        this.subheader = subheader;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getSubheader() {
        return subheader;
    }

    public String getBody() {
        return body;
    }
}
