package com.kaamel.flicks;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by kaamel on 9/15/17.
 */

public abstract class RemoteMovieConnection {

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

    protected static List<Movie> movies;
    protected static WeakReference<OnMovieListChanged> onMovieListChangedRef;

    public RemoteMovieConnection(OnMovieListChanged onMovieListChanged) {
        onMovieListChangedRef = new WeakReference<OnMovieListChanged>(onMovieListChanged);
        if (movies == null || movies.size() == 0) {
            refreshMovies();
        }
    }

    public abstract void refreshMovies();


    public interface OnMovieListChanged {
        public void onChange(List<Movie> movies);
    }
}
