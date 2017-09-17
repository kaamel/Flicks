package com.kaamel.flicks;

/**
 * Created by kaamel on 9/11/17.
 */

public class Movie {

    private int id;
    private int voteCount;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String backdropPath;
    private String overview;
    private String releaseDate;

    private static double popThreshold = 3.6;

    public Movie(int id, int voteCount, double voteAverage, String title, double popularity, String posterPath, String backdropPath,
                 String overview, String releaseDate) {

        this.id = id;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public boolean isPopular() {
        return voteAverage >=popThreshold;
    }

    public static void setPopThreshold(double popThreshold) {
        Movie.popThreshold = popThreshold;
    }

    public static double getPopThreshold() {
        return popThreshold;
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
