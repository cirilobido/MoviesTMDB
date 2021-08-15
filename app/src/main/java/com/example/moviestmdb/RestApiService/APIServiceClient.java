package com.example.moviestmdb.RestApiService;

import com.example.moviestmdb.Models.CastModel;
import com.example.moviestmdb.Models.PostModel;
import com.example.moviestmdb.Models.TMDBDataModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServiceClient {

    @GET("trending/movie/week")
    Observable<TMDBDataModel> getTMDBTrendingObservable(@Query("api_key") String apiKey);

    @GET("trending/movie/day")
    Call<TMDBDataModel> getTMDBLatest(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<TMDBDataModel> getTMDBDiscover(@Query("api_key") String apiKey);

    @GET("movie/{movieId}")
    Observable<PostModel> getMovieDetails(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movieId}/credits")
    Observable<CastModel> getMovieCast(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("movie/{movieId}/similar")
    Observable<TMDBDataModel> getMovieSimilar(@Path("movieId") String movieId, @Query("api_key") String apiKey);
}
