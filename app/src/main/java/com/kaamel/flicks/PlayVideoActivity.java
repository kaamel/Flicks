package com.kaamel.flicks;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoActivity extends YouTubeBaseActivity {

    @BindView(R.id.player)
    YouTubePlayerView youTubePlayerView;

    int screenSize;

    int position;

    Boolean started = false;

    RemoteMovieConnection.OnVideoListChanged callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_video);

        ButterKnife.bind(this);
        position = getIntent().getExtras().getInt("position");
        Movie movie = RemoteMovieConnection.movies.get(position);

        callback = new RemoteMovieConnection.OnVideoListChanged() {
            @Override
            public void onChange(List<Video> videos) {
                synchronized ((started)) {
                    if (!started) {
                        playTrailer(videos);
                        started = true;
                    }
                }
            }
        };

        List<Video> videos = new MovieDatabaseConnection().getTrailers(movie.getId(), callback);

        if (videos.size() > 0) {
            synchronized ((started)) {
                if (!started) {
                    playTrailer(videos);
                    started = true;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
        int size = widthPixels>heightPixels?widthPixels:heightPixels;

        if (size >= 1080)
            screenSize = 1080;
        else if (size >= 720)
            screenSize = 720;
        else
            screenSize = 480;
    }

    private void playTrailer(List<Video> videos) {
        if (videos == null || videos.size() == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PlayVideoActivity.this, "No Trailers Available", Toast.LENGTH_LONG).show();;
                }
            });
            return;
        }
        //the video that has the best size but is not a trailer -- the last choice
        String bestVideoSize = null;
        int bestAvailableSize = screenSize + 1;
        //the trailer that has the best size -- the second choice
        String bestTrailer = null;
        int bestTrailerSize = screenSize + 1;
        //the trailer that has the perfect size -- ideal
        String rightVideo = null;
        for (Video video:videos) {
            if (!video.getSite().equals("YouTube"))
                continue;
            if (video.getFormat() == screenSize) {
                bestVideoSize = video.getKey();
                if (video.getType().equals("Trailer")) {
                    rightVideo = video.getKey();
                    break;
                }
                bestAvailableSize = screenSize;
            }
            else {
                if (video.getType().equals("Trailer")) {
                    if (bestTrailer == null) {
                        bestTrailer = video.getKey();
                        bestTrailerSize = video.getFormat();
                    }
                    else if (bestTrailerSize < video.getFormat() && video.getFormat() < screenSize) {
                        bestTrailer = video.getKey();
                        bestTrailerSize = video.getFormat();
                    }
                    else if (bestTrailerSize > video.getFormat() && video.getFormat() > screenSize) {
                        bestTrailer = video.getKey();
                        bestTrailerSize = video.getFormat();
                    }
                    else if (Math.abs((bestTrailerSize - screenSize)) > Math.abs((video.getFormat() - screenSize))) {
                        bestTrailer = video.getKey();
                        bestTrailerSize = video.getFormat();
                    }
                }
                else {
                    if (bestVideoSize == null) {
                        bestVideoSize = video.getKey();
                        bestAvailableSize = video.getFormat();
                    }
                    else if (bestAvailableSize < video.getFormat() && video.getFormat() < screenSize) {
                        bestVideoSize = video.getKey();
                        bestAvailableSize = video.getFormat();
                    }
                    else if (bestAvailableSize > video.getFormat() && video.getFormat() > screenSize) {
                        bestVideoSize = video.getKey();
                        bestAvailableSize = video.getFormat();
                    }
                    else if (Math.abs((bestAvailableSize - screenSize)) > Math.abs((video.getFormat() - screenSize))) {
                        bestVideoSize = video.getKey();
                        bestAvailableSize = video.getFormat();
                    }
                }
            }

        }
        final String ideal = rightVideo!=null?rightVideo:(bestTrailer!=null?bestTrailer:bestVideoSize);
        if (ideal != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    youTubePlayerView.initialize("AIzaSyDKEYSZFvnH26_q7eEUJ1Qf2Ds3JYkhHbg",
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {

                                    // do any work here to cue video, play video, etc.
                                    //IwfUnkBfdZ4
                                    //youTubePlayer.cueVideo("5xVh-7ywKpE");
                                    youTubePlayer.loadVideo(ideal);
                                }
                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {

                                }
                            });
                }
            });
        }
    }
}
