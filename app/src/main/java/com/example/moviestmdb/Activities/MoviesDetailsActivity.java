package com.example.moviestmdb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moviestmdb.BaseApplication;
import com.example.moviestmdb.R;
import com.example.moviestmdb.RestApiService.APIServiceClient;

import javax.inject.Inject;

public class MoviesDetailsActivity extends AppCompatActivity {
    private String movieTmdbId;
    @Inject
    APIServiceClient apiServiceClient;

    private ImageView imgBack, imgPoster, imgShare, imgInstagram;
    private TextView txtTitle, txtOverview, txtRate, txtReleaseYear, txtRuntime, txtGenres;
    private LinearLayout lnPlayTrailer, lnMoreInfo, lnRetry;
    private RelativeLayout rlLoading;
    private RecyclerView recyclerViewCast, recyclerViewRelated;

    @Override
    protected void onStart() {
        super.onStart();
        ((BaseApplication) getApplication()).getRetrofitComponent().injectRetrofit(this);
        Intent intent = getIntent();
        this.movieTmdbId = !intent.getExtras().equals(null) ? intent.getStringExtra("postId") : "1";
        Log.d("moviePostId", this.movieTmdbId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
    }
    private void setUpMovieView(){
        getMovieDetails();
    }

    private void getMovieDetails() {

    }
}