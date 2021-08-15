package com.example.moviestmdb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.moviestmdb.Adapters.MoviesPostAdapter;
import com.example.moviestmdb.BaseApplication;
import com.example.moviestmdb.BuildConfig;
import com.example.moviestmdb.EndlessRecyclerViewScrollListener;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.Models.TMDBDataModel;
import com.example.moviestmdb.R;
import com.example.moviestmdb.RestApiService.APIServiceClient;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<PostModel> arrayListSearch;
    private LinearLayout lnBtnSearch, lnSearch, lnNoData;
    private RecyclerView recyclerViewSearch;
    private Boolean isOver = false;
    private int pagePosition = 1;
    private LottieAnimationView lottieAnimationView;
    private TextView txt_result;
    private EditText editTextSearch;
    private MoviesPostAdapter moviesPostAdapter;
    private Disposable disposable;

    @Inject
    APIServiceClient apiServiceClient;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ((BaseApplication) getApplication()).getRetrofitComponent().injectRetrofit(this);
        setView();
    }

    private void setView() {
        lottieAnimationView = findViewById(R.id.animation_view);
        lnSearch = findViewById(R.id.ln_search);
        lnBtnSearch = findViewById(R.id.btn_search);
        lnNoData = findViewById(R.id.ln_no_data);
        txt_result = findViewById(R.id.txt_result);
        recyclerViewSearch = findViewById(R.id.recycler_view_search);

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0){
                    lnSearch.setVisibility(View.GONE);
                    lnNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lnBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnNoData.setVisibility(View.GONE);
                moviesPostAdapter = null;
                getSearch(editTextSearch.getText().toString());
            }
        });
    }

    private void getSearch(String searchParam) {
        txt_result.setText("'"+searchParam+"'");
        if (moviesPostAdapter == null) {
            pagePosition = 1;
            arrayListSearch = new ArrayList<>();
            arrayListSearch.clear();
        } else {
            lottieAnimationView.setVisibility(View.VISIBLE);
        }

        apiServiceClient.getSearch(BuildConfig.TMDB_KEY, searchParam, pagePosition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TMDBDataModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull TMDBDataModel tmdbDataModel) {
                        arrayListSearch.addAll(tmdbDataModel.getResults());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        lottieAnimationView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        setUpSearchResults(searchParam);
                    }
                });
    }

    private void setUpSearchResults(String searchParam) {
        if (moviesPostAdapter == null) {
            if (arrayListSearch.size() != 0) {
                lnSearch.setVisibility(View.VISIBLE);
                lnNoData.setVisibility(View.GONE);
                moviesPostAdapter = new MoviesPostAdapter(this, arrayListSearch);
                recyclerViewSearch.setAdapter(moviesPostAdapter);
                moviesPostAdapter.notifyDataSetChanged();
                recyclerViewSearch.setVisibility(View.VISIBLE);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
                recyclerViewSearch.setLayoutManager(gridLayoutManager);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
                {
                    @Override
                    public int getSpanSize(int position)
                    {
                        if(arrayListSearch.get(position) == null){
                            return 3;
                        } else {
                            return 1;
                        }
                    }
                });

                recyclerViewSearch.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        if (!isOver) {
                            lottieAnimationView.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pagePosition++;
                                    getSearch(searchParam);
                                }
                            }, 500);
                        }
                    }
                });
            } else {
                lnSearch.setVisibility(View.GONE);
                lnNoData.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
            }
        } else {
            lnSearch.setVisibility(View.VISIBLE);
            lnNoData.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.GONE);
            moviesPostAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}