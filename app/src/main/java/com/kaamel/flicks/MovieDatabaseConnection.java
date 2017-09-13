package com.kaamel.flicks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kaamel on 9/13/17.
 */

public class MovieDatabaseConnection {

    private static final String ALL_MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String POSTER_IMAGE = "poster_path";
    private static final String BACKDROP_IMAGE = "backdrop_path";
    private static final String RESULTS = "results"; // poster_path and backdrop_path

    private OnMovieListChanged onMovieListChanged;
    public static List<Movie> downloadAllMovies(final List<Movie> movies, final OnMovieListChanged onMovieListChanged) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ALL_MOVIES_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                try {
                    extractMovies(response.body().string(), movies);
                    onMovieListChanged.onChange();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return movies;
    }

    private static void extractMovies(String message, List<Movie> movies) throws JSONException {
        JSONObject jList = new JSONObject(message);

        JSONArray jMovies = jList.getJSONArray(RESULTS);
        int n = jMovies.length();
        for (int i=0; i<n; i++) {
            JSONObject jMovie = jMovies.getJSONObject(i);
            jMovie.put(POSTER_IMAGE, BASE_IMAGE_URL + jMovie.get(POSTER_IMAGE));
            jMovie.put(BACKDROP_IMAGE, BASE_IMAGE_URL + jMovie.get(BACKDROP_IMAGE));
            Movie movie = new Movie(jMovie);
            movies.add(movie);
        }
    }

    public interface OnMovieListChanged {
        public void onChange();
    }
}
