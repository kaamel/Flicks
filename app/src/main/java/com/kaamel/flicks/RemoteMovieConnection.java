package com.kaamel.flicks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaamel on 9/15/17.
 */

public abstract class RemoteMovieConnection {

    protected static final List<Movie> movies = new ArrayList<>();
    protected static boolean inProgress;
    protected static final Map<Integer, List<Video>> videoMap = new HashMap<>();

    public RemoteMovieConnection() {

    }

    public static List<Movie> getCurrentMovieList() {
        return movies;
    }

    public static boolean isInProgress() {
        return inProgress;
    }

    /**
     * Get the list of currently showing movies
     * @param onMovieListChanged callback; only called if the movies list is not already available
     * @return the list of currently showing movies, if previously downloaded, otheerwise a referenced is returned,
     * and the onMovieListChanged will be called when the movies are downloaded
     */
    public List<Movie> getMovies(OnMovieListChanged onMovieListChanged) {
        synchronized (movies) {
            if (!inProgress && movies.size() == 0) {
                refreshMovies(onMovieListChanged);
            }
            return movies;
        }
    }

    /**
     * Get the available trailers for a given movie
     * @param videoId the movie ID
     * @param onVideoListChanged callback; only called if the trailers list is not already downloaded
     * @return the list of trailers for the movie, if previously downloaded, otheerwise a referenced is returned,
     * and the onVideoListChanged will be called when the trailers are downloaded
     * @return
     */
    public List<Video> getTrailers(int videoId, OnVideoListChanged onVideoListChanged) {
        synchronized (videoMap) {
            List<Video> trailers = videoMap.get(videoId);
            if (trailers == null || trailers.size() == 0) {
                trailers = new ArrayList<>();
                videoMap.put(videoId, trailers);
                downloadTrailers(videoId, onVideoListChanged);
            }
            return trailers;
        }
    }

    protected double scaleRating(double raw, double oldMin, double oldMax, double newMin, double newMax) {
        double range = newMax - newMin;
        return (((raw - oldMin)/(oldMax - oldMin)) * range) + newMin;
    }

    public void refreshMovies(OnMovieListChanged onMovieListChanged) {
        inProgress = true;
        downloadMovies(onMovieListChanged);
    }

    /**
     * Download all the currently showing movies into  {@link #movies}.
     * The implementation must set the {@link #inProgress} to false after {@link #movies} is updated
     * @param onMovieListChanged callback when complete
     */
    public abstract void downloadMovies(OnMovieListChanged onMovieListChanged);

    /**
     *
     * @param videoId the video ID of the movie
     * @param onVideoListChanged callback when the download is completed
     */
    public abstract void downloadTrailers(int videoId, OnVideoListChanged onVideoListChanged);


    public interface OnMovieListChanged {
        public void onChange(List<Movie> movies);
    }

    public interface OnVideoListChanged {
        public void onChange(List<Video> videos);
    }
}
