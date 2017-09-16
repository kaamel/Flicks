package com.kaamel.flicks;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kaamel.flicks.RemoteMovieConnection.movies;

public class MainActivity extends Activity {

	@BindView(R.id.lvItem) ListView listView;
	MoviesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Butterknife
		ButterKnife.bind(this);
		adapter = new MoviesAdapter(this, getMovies());
		listView.setAdapter(adapter);

	}

	private List<Movie> getMovies() {
		new MovieDatabaseConnection(new MovieDatabaseConnection.OnMovieListChanged() {
			@Override
			public void onChange(final List<Movie> ms) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
//						movies.clear();
//						movies.addAll(ms);
						adapter.notifyDataSetChanged();
					}
				});
			}
		});
		return movies;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
