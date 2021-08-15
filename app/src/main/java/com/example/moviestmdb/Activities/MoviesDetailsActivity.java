package com.example.moviestmdb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestmdb.Adapters.CastAdapter;
import com.example.moviestmdb.Adapters.MoviesPostAdapter;
import com.example.moviestmdb.BaseApplication;
import com.example.moviestmdb.BuildConfig;
import com.example.moviestmdb.Models.CastModel;
import com.example.moviestmdb.Models.GenreModel;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.Models.TMDBDataModel;
import com.example.moviestmdb.R;
import com.example.moviestmdb.RestApiService.APIServiceClient;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviesDetailsActivity extends AppCompatActivity {
    private String movieTmdbId;
    @Inject
    APIServiceClient apiServiceClient;
    private PostModel postModelData;
    private ArrayList<PostModel> similarArrayList;
    private ArrayList<CastModel> castArrayList;
    private ImageView imgBack, imgPoster, imgShare, imgInstagram;
    private TextView txtTitle, txtOverview, txtRate, txtReleaseYear, txtRuntime, txtGenres, txtLoading;
    private LinearLayout lnPlayTrailer, lnMoreInfo, lnRetry, lnRelated, lnCast;
    private RelativeLayout rlLoading;
    private LottieAnimationView animationView;
    private RecyclerView recyclerViewCast, recyclerViewRelated;
    private Disposable disposable;
    private MoviesPostAdapter similarMoviesAdapter;
    private CastAdapter castAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_movies_details);
        Intent intent = getIntent();
        movieTmdbId = !intent.getExtras().equals(null) ? intent.getStringExtra("postId") : "1";
        Log.d("getPostTitle", movieTmdbId);
        ((BaseApplication) getApplication()).getRetrofitComponent().injectRetrofit(this);
        setUpMovieView();
    }
    private void setUpMovieView(){
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgInstagram = findViewById(R.id.img_instagram);
        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/cirilobido"));
                startActivity(instagramIntent);
            }
        });
        imgShare = findViewById(R.id.img_share);
        imgPoster = findViewById(R.id.img_poster);

        txtTitle = findViewById(R.id.txt_title);
        txtOverview = findViewById(R.id.txt_overview);
        txtRate = findViewById(R.id.txt_rating);
        txtReleaseYear = findViewById(R.id.txt_year);
        txtRuntime = findViewById(R.id.txt_runtime);
        txtGenres = findViewById(R.id.txt_genres);
        txtLoading = findViewById(R.id.txt_loading);

        lnPlayTrailer = findViewById(R.id.ln_play);
        lnMoreInfo = findViewById(R.id.ln_info);
        lnRetry = findViewById(R.id.ln_retry);
        lnRelated = findViewById(R.id.ln_related);
        lnCast = findViewById(R.id.ln_cast);

        recyclerViewRelated = findViewById(R.id.recycler_view_related);
        recyclerViewCast = findViewById(R.id.recycler_view_cast);

        rlLoading = findViewById(R.id.rl_loading);
        animationView = findViewById(R.id.animation_view);

        getMovieDetails();
    }

    private void getMovieDetails() {
        similarArrayList = new ArrayList<>();
        castArrayList = new ArrayList<>();
        apiServiceClient.getMovieDetails(movieTmdbId, BuildConfig.TMDB_KEY)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<PostModel, ObservableSource<TMDBDataModel>>) response -> {
                    postModelData = response;
                    return apiServiceClient.getMovieSimilar(response.getPostId(), BuildConfig.TMDB_KEY)
                            .subscribeOn(Schedulers.io());
                })
                .flatMap((Function<TMDBDataModel, ObservableSource<CastModel>>) responseSimilar -> {
                    similarArrayList.addAll(responseSimilar.getResults());
                    return apiServiceClient.getMovieCast(movieTmdbId, BuildConfig.TMDB_KEY)
                            .subscribeOn(Schedulers.io());
                })
                .flatMap((Function<CastModel, ObservableSource<?>>) responseCast -> {
                    castArrayList.addAll(responseCast.getCastResult());
                    return apiServiceClient.getMovieCast(movieTmdbId, BuildConfig.TMDB_KEY)
                            .subscribeOn(Schedulers.io());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                        animationView.setVisibility(View.VISIBLE);
                        rlLoading.setVisibility(View.VISIBLE);
                        txtLoading.setText(R.string.text_loading);
                        lnRetry.setVisibility(View.GONE);
                        lnRetry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getMovieDetails();
                            }
                        });
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        setUpMovieResults();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("onError", e.getMessage());
                        setErrorView();
                    }

                    @Override
                    public void onComplete() {
                        rlLoading.setVisibility(View.GONE);
                    }
                });
    }

    private void setErrorView() {
        rlLoading.setVisibility(View.VISIBLE);
        animationView.setVisibility(View.GONE);
        txtLoading.setText(R.string.text_error_loading);
        lnRetry.setVisibility(View.VISIBLE);
    }

    private void setUpMovieResults() {
        try {
            if (postModelData != null){
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w342" + postModelData.getPosterPath())
                        .thumbnail(0.25f)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .error(getResources().getDrawable(R.drawable.ic_muvi_name))
                        .into(imgPoster);
                txtTitle.setText(postModelData.getPostTitle());
                txtOverview.setText(Html.fromHtml((String) postModelData.getPostOverview()).toString());
                txtRate.setText(String.valueOf(postModelData.getVoteAverage()));
                txtRuntime.setText(postModelData.getRuntime() + " min");
                String[] date = postModelData.getReleaseDate().split("-");
                txtReleaseYear.setText(date[0]);
                for (GenreModel genre : postModelData.getGenreModelList()){
                    txtGenres.setText(txtGenres.getText() + " " + genre.getGenreName());
                }

                imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TITLE, postModelData.getPostTitle());
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Want to see " + postModelData.getPostOverview() + "?");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, postModelData.getPostOverview());
                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                    }
                });

                lnPlayTrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="
                                + postModelData.getPostTitle()
                                + " Trailer"));
                        startActivity(trailerIntent);
                    }
                });

                lnMoreInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent infoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postModelData.getHomepage()));
                        startActivity(infoIntent);
                    }
                });

                if (castArrayList != null) {
                    castAdapter = new CastAdapter(this, castArrayList);
                    recyclerViewCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewCast.setAdapter(castAdapter);
                    lnCast.setVisibility(View.VISIBLE);
                }

                if (similarArrayList != null) {
                    similarMoviesAdapter = new MoviesPostAdapter(this, similarArrayList);
                    recyclerViewRelated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewRelated.setAdapter(similarMoviesAdapter);
                    lnRelated.setVisibility(View.VISIBLE);
                }

            } else {
                setErrorView();
            }
        } catch (Exception e){
            setErrorView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}