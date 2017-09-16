package com.kaamel.flicks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by kaamel on 9/11/17.
 */

public class MoviesAdapter extends ArrayAdapter<Movie> {
    // View lookup cache
    static class ViewHolder {
        @Nullable
        @BindView(R.id.title) TextView title;
        @Nullable
        @BindView(R.id.overview) TextView overview;
        @Nullable
        @BindView(R.id.posterImage) ImageView posterImage;
        @Nullable
        @BindView(R.id.backdropImage) ImageView backdropImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MoviesAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.item_type1, movies);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getBackdropPath() == null)
            return 0;
        //on the scale of 0-5 a movie is populbar if it is rated 4.5 or higher
        return isPopular(position)?1:0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflate(inflater, position, parent);
            viewHolder = new ViewHolder(convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        assert movie != null;
        if (viewHolder.title != null)
            viewHolder.title.setText(movie.getTitle());
        if (viewHolder.overview != null)
            viewHolder.overview.setText(movie.getOverview());
        if (viewHolder.backdropImage != null) {
            Picasso.with(getContext())
                .load(movie.getBackdropPath())
                .fit().centerInside()
                .placeholder(R.drawable.movie_backdrop).transform(new RoundedCornersTransformation(15, 5))
                .error(R.drawable.error)
                .into(viewHolder.backdropImage);

        }
        else {
            Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .fit().centerInside()
                    .placeholder(R.drawable.movie_poster).transform(new RoundedCornersTransformation(15, 5))
                    .error(android.R.drawable.stat_notify_error)
                    .into(viewHolder.posterImage);
        }
        // Return the completed view to render on screen
        return convertView;
    }

    private View inflate(LayoutInflater inflater, int position, ViewGroup parent) {
        return inflater.inflate(getItemViewType(position) == 0?R.layout.item_type1: R.layout.item_type2, parent, false);
    }

    private boolean isPopular(int position) {
        return getItem(position).getVoteAverage()>=3.5;
    }

}
