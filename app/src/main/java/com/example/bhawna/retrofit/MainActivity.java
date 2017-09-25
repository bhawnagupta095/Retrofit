package com.example.bhawna.retrofit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "ee343711c76936fe87dca8e7c67022ca";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Log.d("ffffffffffff1",apiService.toString());
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        Log.d("ffffffffffff2",call.toString());

       call.enqueue(new Callback<MovieResponse>() {
           @Override
           public void onResponse(@NonNull Call<MovieResponse> call, Response<MovieResponse> response) {
               List<Movie> movies = response.body().getResults();
               Log.d("ffffffff",movies.toString());
               MoviesAdapter moviesAdapter=new MoviesAdapter(movies,R.layout.row_item,getApplicationContext());
               RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
               recyclerView.setLayoutManager(mLayoutManager);
               recyclerView.setItemAnimator(new DefaultItemAnimator());
               recyclerView.setAdapter(moviesAdapter);


           }

           @Override
           public void onFailure(@NonNull Call<MovieResponse> call, Throwable t) {

               Log.e(TAG, t.toString());

           }
       });
    }
}
