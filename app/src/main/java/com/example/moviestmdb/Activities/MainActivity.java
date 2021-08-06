package com.example.moviestmdb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moviestmdb.Adapters.LatestMoviesAdapter;
import com.example.moviestmdb.Adapters.SliderAdapter;
import com.example.moviestmdb.BaseApplication;
import com.example.moviestmdb.BuildConfig;
import com.example.moviestmdb.Models.GenreModel;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.Models.TMDBDataModel;
import com.example.moviestmdb.R;
import com.example.moviestmdb.RestApiService.APIServiceClient;
import com.opensooq.pluto.PlutoIndicator;
import com.opensooq.pluto.PlutoView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PlutoView sliderHome;
    private PlutoIndicator sliderIndicator;
    private SliderAdapter sliderAdapter;
    private LatestMoviesAdapter latestMoviesAdapter;
    private List<PostModel> postModelList;
    private RecyclerView recyclerViewLatest, recyclerViewRecommended;
    private ArrayList<PostModel> latestArray, recommendedArray;
    private Disposable disposable;

    @Inject
    APIServiceClient apiServiceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DAGGER Injection for Retrofit client
        ((BaseApplication) getApplication()).getRetrofitComponent().injectRetrofit(this);
        setView();
    }

    private void setView(){
        animationView = findViewById(R.id.animation_view);
        animationView.setVisibility(View.VISIBLE);
        sliderHome = findViewById(R.id.slider_home);
        sliderIndicator = findViewById(R.id.slider_indicator);

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                setView();
            }
        });

        recyclerViewLatest = findViewById(R.id.recycler_view_movies);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRecommended = findViewById(R.id.recycler_view_recommended);
        recyclerViewRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        getTranding();
        getLatestMovies();
        getRecommendedMovies();
    }

    private void getTranding(){
        postModelList = new ArrayList<>();
        apiServiceClient.getTMDBTrendingObservable(BuildConfig.TMDB_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TMDBDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull TMDBDataModel tmdbDataModel) {
                        postModelList.addAll(tmdbDataModel.getResults());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        animationView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        setUpTrandingView();
                    }
                });

//        Call<TMDBDataModel> call = apiServiceClient.getTMDBTrending(BuildConfig.TMDB_KEY);
//        call.enqueue(new Callback<TMDBDataModel>() {
//            @Override
//            public void onResponse(Call<TMDBDataModel> call, Response<TMDBDataModel> response) {
//                postModelList.addAll(response.body().getResults());
//                setUpSliderView();
//            }
//
//            @Override
//            public void onFailure(Call<TMDBDataModel> call, Throwable t) {
//                t.printStackTrace();
//                animationView.setVisibility(View.GONE);
//
//            }
//        });
    }

    private void setUpTrandingView(){
        sliderAdapter = new SliderAdapter(MainActivity.this, postModelList);
        sliderHome.create(sliderAdapter, getLifecycle());
        sliderHome.startAutoCycle(5000, true);
        sliderHome.setCustomIndicator(sliderIndicator);
        animationView.setVisibility(View.GONE);
    }

    private void getLatestMovies() {
        latestArray = new ArrayList<>();
        Call<TMDBDataModel> call = apiServiceClient.getTMDBLatest(BuildConfig.TMDB_KEY);
        call.enqueue(new Callback<TMDBDataModel>() {
            @Override
            public void onResponse(Call<TMDBDataModel> call, Response<TMDBDataModel> response) {
                Log.i("getLatestMovies", response.body().getResults().toString());
                latestArray.addAll(response.body().getResults());
                setUpLatestMoviesView();
            }

            @Override
            public void onFailure(Call<TMDBDataModel> call, Throwable t) {

            }
        });
    }

    private void setUpLatestMoviesView(){
        latestMoviesAdapter = new LatestMoviesAdapter(this, latestArray);
        recyclerViewLatest.setAdapter(latestMoviesAdapter);
        animationView.setVisibility(View.GONE);
    }

    private void getRecommendedMovies(){
        recommendedArray = new ArrayList<>();
        Call<TMDBDataModel> call = apiServiceClient.getTMDBDiscover(BuildConfig.TMDB_KEY);
        call.enqueue(new Callback<TMDBDataModel>() {
            @Override
            public void onResponse(Call<TMDBDataModel> call, Response<TMDBDataModel> response) {
                Log.i("getRecommendedMovies", response.body().getResults().toString());
                recommendedArray.addAll(response.body().getResults());
                setUpRecommendedView();
            }

            @Override
            public void onFailure(Call<TMDBDataModel> call, Throwable t) {

            }
        });
    }

    private void setUpRecommendedView() {
        latestMoviesAdapter = new LatestMoviesAdapter(this, recommendedArray);
        recyclerViewRecommended.setAdapter(latestMoviesAdapter);
        animationView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}