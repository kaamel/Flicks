package com.kaamel.flicks;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kaamel on 9/13/17.
 */

public class MovieDatabaseConnection extends RemoteMovieConnection {

    private static final String ALL_MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String MOVIE_TRAILER_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_TRAILER__SUF_URL = "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String RESULTS = "results";

    private static final String VOTE_COUNT = "vote_count";
    private static final String ID = "id";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    private static final String KEY = "key";
    private static final String SITE = "site";
    private static final String FORMAT = "size";
    private static final String TYPE = "type";

    public MovieDatabaseConnection() {
        super();
    }

    @Override
    public void downloadMovies(OnMovieListChanged onMovieListChanged) {

        final WeakReference<OnMovieListChanged> onMovieListChangedRef = new WeakReference<OnMovieListChanged>(onMovieListChanged);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ALL_MOVIES_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnMovieListChanged onMovieListChanged = onMovieListChangedRef.get();
                if (onMovieListChanged != null)
                    onMovieListChanged.onChange(null);
                inProgress = false;
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    OnMovieListChanged onMovieListChanged = onMovieListChangedRef.get();
                    if (onMovieListChanged != null)
                        onMovieListChanged.onChange(movies);
                    inProgress = false;
                    throw new IOException("Unexpected code " + response);
                }
                try {
                    extractMovies(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OnMovieListChanged onMovieListChanged = onMovieListChangedRef.get();
                if (onMovieListChanged != null)
                    onMovieListChanged.onChange(movies);
                inProgress = false;
            }
        });
    }

    public void downloadTrailers(final int videoId, OnVideoListChanged onVideoListChanged) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(MOVIE_TRAILER_BASE_URL + videoId + MOVIE_TRAILER__SUF_URL)
                .build();

        final WeakReference<OnVideoListChanged> listenerRef = new WeakReference<OnVideoListChanged>(onVideoListChanged);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                OnVideoListChanged onVideolistener = listenerRef.get();
                if (onVideolistener != null)
                    onVideolistener.onChange(videoMap.get(videoId));
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    OnVideoListChanged onVideolistener = listenerRef.get();
                    if (onVideolistener != null)
                        onVideolistener.onChange(videoMap.get(videoId));
                    throw new IOException("Unexpected code " + response);
                }
                try {
                    List<Video> videos = videoMap.get(videoId);
                    videos.addAll(videoMap.put(videoId, extractTrailers(response.body().string())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                OnVideoListChanged onVideolistener = listenerRef.get();
                if (onVideolistener != null)
                    onVideolistener.onChange(videoMap.get(videoId));
            }
        });
    }

    private List<Video> extractTrailers(String message) throws JSONException {
        JSONObject jList = new JSONObject(message);

        List<Video> trailers = new ArrayList<>();
        JSONArray jTrailers = jList.getJSONArray(RESULTS);
        int n = jTrailers.length();
        for (int i=0; i<n; i++) {
            JSONObject jTrailer = jTrailers.getJSONObject(i);
            Video trailer = extractTrailer(jTrailer);
            trailers.add(trailer);
        }
        return trailers;
    }

    private Video extractTrailer(@NonNull JSONObject trailer) throws JSONException, NullPointerException {
        String key = trailer.getString(KEY);
        String site = trailer.getString(SITE);
        int format = trailer.getInt(FORMAT);
        String type = trailer.getString(TYPE);
        return new Video(key, site, format, type);
    }

    private void extractMovies(String message) throws JSONException {
        JSONObject jList = new JSONObject(message);

        JSONArray jMovies = jList.getJSONArray(RESULTS);
        int n = jMovies.length();
        for (int i=0; i<n; i++) {
            JSONObject jMovie = jMovies.getJSONObject(i);
            Movie movie = extractMovie(jMovie);
            movies.add(movie);
        }
    }

    private Movie extractMovie(@NonNull JSONObject movie) throws JSONException, NullPointerException {
        int id = movie.getInt(ID);
        int voteCount = movie.getInt(VOTE_COUNT);
        double voteAverage = scaleRating(movie.getDouble(VOTE_AVERAGE));
        String title = movie.getString(TITLE);
        double popularity = movie.getDouble(POPULARITY);
        String posterPath = BASE_IMAGE_URL + movie.getString(POSTER_PATH);
        String backdropPath = backdropPath = BASE_IMAGE_URL + movie.getString(BACKDROP_PATH);
        String overview = movie.getString(OVERVIEW);
        String releaseDate = movie.getString(RELEASE_DATE);
        return new Movie(id, voteCount, voteAverage, title, popularity, posterPath, backdropPath, overview, releaseDate);
    }

    private double scaleRating(double raw) {
        double newMin = 0;
        double newMax = 5;
        return super.scaleRating(raw, 0.5, 10.0, newMin, newMax);
    }
}
