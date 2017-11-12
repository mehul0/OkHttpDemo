package com.mehul.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mehul.okhttpdemo.adapter.MyAdapter;
import com.mehul.okhttpdemo.async_task.MovieTask;
import com.mehul.okhttpdemo.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterEvent, MovieTask.MovieTaskEvent {

    private static final String URI = "https://api.androidhive.info/json/movies.json";
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Movie> movieList;
    private MovieTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        task = new MovieTask(this);
        task.execute(URI);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MyAdapter();
        movieList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //adapter = new MyAdapter(this,this.movieList);

        //adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "Movie Details \n " +
                "Movie  : " + movieList.get(position).getTitle() + "\n" +
                "Rating : " + movieList.get(position).getRating() + "\n" +
                "Year   : " + movieList.get(position).getReleaseYear(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMovieTaskResult(List<Movie> movieList) {
        this.movieList = movieList;
        adapter = new MyAdapter(this,this, this.movieList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
