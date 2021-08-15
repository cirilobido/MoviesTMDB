package com.example.moviestmdb;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.example.moviestmdb.DependecyInjections.DaggerRetrofitComponent;
import com.example.moviestmdb.DependecyInjections.RetrofitComponent;
import com.example.moviestmdb.DependecyInjections.RetrofitModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class BaseApplication extends Application {
    private RetrofitComponent retrofitComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule())
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public RetrofitComponent getRetrofitComponent() {
        return retrofitComponent;
    }
}
