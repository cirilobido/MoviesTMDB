package com.example.moviestmdb.RestApiService;

import com.example.moviestmdb.Models.GenreModel;
import com.example.moviestmdb.Models.TMDBDataModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceClient {

    @GET("trending/movie/week")
    Observable<TMDBDataModel> getTMDBTrendingObservable(@Query("api_key") String apiKey);

    @GET("trending/movie/day")
    Call<TMDBDataModel> getTMDBLatest(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<GenreModel> getTMDBDiscover(@Query("api_key") String apiKey);
}
