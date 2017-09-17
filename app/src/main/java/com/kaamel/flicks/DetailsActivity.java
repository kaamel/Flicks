package com.kaamel.flicks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsActivity extends Activity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.movieImage)
    ImageView movieImage;
    @BindView(R.id.releaseDate)
    TextView releaseDate;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Butterknife
        ButterKnife.bind(this);

        position = getIntent().getExtras().getInt("position");
        Movie movie = RemoteMovieConnection.movies.get(position);
        Picasso.with(this)
                .load(movie.getBackdropPath())
                .fit()
                .centerInside()
                .placeholder(R.drawable.movie_backdrop).transform(new RoundedCornersTransformation(15, 5))
                .error(R.drawable.error)
                .into(movieImage);
        title.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getVoteAverage());
        releaseDate.setText("Release Date " + movie.getReleaseDate());
        summary.setText(movie.getOverview());
        setTitle(movie.getTitle());
    }

    public void playVideo(View view) {
        //Play movie
        Intent intent = new Intent(this, PlayVideoActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("force_fullscreen",true);
        startActivity(intent);
    }
}
