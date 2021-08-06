package com.example.moviestmdb.DependecyInjections;

import com.example.moviestmdb.Activities.MainActivity;
import com.example.moviestmdb.Activities.MoviesDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void injectRetrofit(MainActivity mainActivity);
    void injectRetrofit(MoviesDetailsActivity moviesDetailsActivity);
}
