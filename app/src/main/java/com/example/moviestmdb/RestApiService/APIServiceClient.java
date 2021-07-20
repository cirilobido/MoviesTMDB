package com.example.moviestmdb.RestApiService;

import com.example.moviestmdb.Models.TMDBDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceClient {

    @GET("trending/movie/day")
    Call<TMDBDataModel> getTMDBTrending(@Query("api_key") String apiKey);
}
