package com.kaamel.flicks;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by kaamel on 9/15/17.
 */

public abstract class RemoteMovieConnection {

    protected static List<Movie> movies;
    protected static WeakReference<OnMovieListChanged> onMovieListChangedRef;
    protected static boolean inProgress;

    public RemoteMovieConnection(OnMovieListChanged onMovieListChanged) {
        onMovieListChangedRef = new WeakReference<OnMovieListChanged>(onMovieListChanged);
        if (movies == null || movies.size() == 0) {
            initMovies();
        }
    }

    private void initMovies() {
        if (!inProgress)
            refreshMovies();
    }

    protected double scaleRating(double raw, double oldMin, double oldMax, double newMin, double newMax) {
        double range = newMax - newMin;
        return (((raw - oldMin)/(oldMax - oldMin)) * range) + newMin;
    }

    public abstract void refreshMovies();

    public interface OnMovieListChanged {
        public void onChange(List<Movie> movies);
    }
}
