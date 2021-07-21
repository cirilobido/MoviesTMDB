package com.example.moviestmdb.DependecyInjections;

import com.example.moviestmdb.BuildConfig;
import com.example.moviestmdb.RestApiService.APIServiceClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoginInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory gsonConverterFactory){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build();
    }
    @Singleton
    @Provides
    APIServiceClient provideApiServiceClient(Retrofit retrofit){
        return retrofit.create(APIServiceClient.class);
    }
}
