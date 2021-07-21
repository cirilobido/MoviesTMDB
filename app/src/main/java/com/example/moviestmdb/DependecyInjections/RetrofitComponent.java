package com.example.moviestmdb.DependecyInjections;

import com.example.moviestmdb.Activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void injectRetrofit(MainActivity mainActivity);
}
