package com.kaamel.flicks;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaamel on 9/11/17.
 */

public class Movie {

    /*
    vote_count: 749,
    id: 396422,
    video: false,
    vote_average: 6.4,
    title: "Annabelle: Creation",
    popularity: 408.569783,
    poster_path: "/tb86j8jVCVsdZnzf8I6cIi65IeM.jpg",
    original_language: "en",
    original_title: "Annabelle: Creation",
    genre_ids: [
    53,
    27
    ],
    backdrop_path: "/o8u0NyEigCEaZHBdCYTRfXR8U4i.jpg",
    adult: false,
    overview: "Several years after the tragic death of their little girl, a dollmaker and his wife welcome a nun and several girls from a shuttered orphanage into their home, soon becoming the target of the dollmaker's possessed creation, Annabelle.",
    release_date: "2017-08-03"
     */

    private static final String VOTE_COUNT = "vote_count";
    private static final String ID = "id";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String LANGUAGE = "original_language";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    private int id;
    private int voteCount;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String language;
    private String backdropPath;
    private String overview;
    private String releaseDate;

    public Movie(@NonNull JSONObject movie) throws JSONException, NullPointerException {
        id = movie.getInt(ID);
        voteCount = movie.getInt(VOTE_COUNT);
        voteAverage = movie.getDouble(VOTE_AVERAGE);
        title = movie.getString(TITLE);
        popularity = movie.getDouble(POPULARITY);
        posterPath = movie.getString(POSTER_PATH);
        language = movie.getString(LANGUAGE);
        backdropPath = movie.getString(BACKDROP_PATH);
        overview = movie.getString(OVERVIEW);
        releaseDate = movie.getString(RELEASE_DATE);
    }

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getLanguage() {
        return language;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
