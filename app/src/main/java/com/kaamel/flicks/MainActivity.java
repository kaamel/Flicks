package com.kaamel.flicks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

	@BindView(R.id.lvItem) ListView listView;
	@BindView(R.id.progressBar) ProgressBar progressBar;
	MoviesAdapter adapter;

	MovieDatabaseConnection.OnMovieListChanged callback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Butterknife
		ButterKnife.bind(this);


		callback = new MovieDatabaseConnection.OnMovieListChanged() {
			@Override
			public void onChange(final List<Movie> ms) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressBar.setVisibility(View.GONE);
						adapter.notifyDataSetChanged();
					}
				});
			}
		};

		adapter = new MoviesAdapter(this, new MovieDatabaseConnection().getMovies(callback));
		if (RemoteMovieConnection.getCurrentMovieList().size()>0 || !RemoteMovieConnection.isInProgress()) {
			progressBar.setVisibility(View.GONE);
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (adapter.getItemViewType(position) == 1) {
					//Play movie
					Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
					intent.putExtra("position", position);
					startActivity(intent);
				}
				else {
					//Show details
					Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("force_fullscreen",true);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
