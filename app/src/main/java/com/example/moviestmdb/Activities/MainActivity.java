package com.example.moviestmdb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moviestmdb.Adapters.SliderAdapter;
import com.example.moviestmdb.BuildConfig;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.Models.TMDBDataModel;
import com.example.moviestmdb.R;
import com.example.moviestmdb.RestApiService.APIServiceClient;
import com.opensooq.pluto.PlutoIndicator;
import com.opensooq.pluto.PlutoView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PlutoView sliderHome;
    private PlutoIndicator sliderIndicator;
    private SliderAdapter sliderAdapter;
    private List<PostModel> postModelList;

    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        getTranding();
    }

    private void getTranding(){
        postModelList = new ArrayList<>();
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        APIServiceClient apiServiceClient = retrofit.create(APIServiceClient.class);
        Call<TMDBDataModel> call = apiServiceClient.getTMDBTrending(BuildConfig.TMDB_KEY);
        call.enqueue(new Callback<TMDBDataModel>() {
            @Override
            public void onResponse(Call<TMDBDataModel> call, Response<TMDBDataModel> response) {
                sliderAdapter = new SliderAdapter(MainActivity.this, response.body().getResults());
//                sliderAdapter.setData(response.body().getResults());
                setUpSliderView();
            }

            @Override
            public void onFailure(Call<TMDBDataModel> call, Throwable t) {
                t.printStackTrace();
                animationView.setVisibility(View.GONE);

            }
        });
    }
    private void setUpSliderView(){
        sliderHome.create(sliderAdapter, getLifecycle());
        sliderHome.startAutoCycle(5000, true);
        sliderHome.setCustomIndicator(sliderIndicator);
        animationView.setVisibility(View.GONE);
    }
}