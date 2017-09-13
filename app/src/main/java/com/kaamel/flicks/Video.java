package com.kaamel.flicks;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaamel on 9/11/17.
 */

public class Video {
    /*
        id: "571c4307c3a36864e00042ac",
        iso_639_1: "en",
        iso_3166_1: "US",
        key: "IwfUnkBfdZ4",
        name: "Official Teaser Trailer",
        site: "YouTube",
        size: 1080,
        type: "Trailer"
     */

    private static final String ID = "id";
    private static final String LANGUAGE = "iso_639_1";
    private static final String CONUNTRY = "iso_3166_1";
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String SITE = "site";
    private static final String FORMAT = "size";
    private static final String TYPE = "type";

    private String id;
    private String language;
    private String country;
    private String key;
    private String name;
    private String site;
    private int format;
    private String type;

    public Video(JSONObject video) throws JSONException, NullPointerException {
        id = video.getString(ID);
        language = video.getString(LANGUAGE);
        country = video.getString(CONUNTRY);
        key = video.getString(KEY);
        name = video.getString(NAME);
        site = video.getString(SITE);
        format = video.getInt(FORMAT);
        type = video.getString(TYPE);
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
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
