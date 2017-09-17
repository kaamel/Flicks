package com.kaamel.flicks;

/**
 * Created by kaamel on 9/11/17.
 */

public class Video {



    private String key;
    private String site;
    private int format;
    private String type;

    public Video(String key, String site, int format, String type) {
        this.key = key;
        this.site = site;
        this.format = format;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public int getFormat() {
        return format;
    }

    public String getType() {
        return type;
    }
}
