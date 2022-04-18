package com.nts.youtubemusic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.nts.youtubemusic.utils.DebugTree;
import com.nts.youtubemusic.utils.LocaleUtils;
import com.tuanhav95.drag.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import timber.log.Timber;

@HiltAndroidApp
public class App extends MultiDexApplication {
    public static Context appContext;
    private static App instance;
    public SharedPreferences mPrefs;

    public static Context getContext() {
        return appContext;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        RxJavaPlugins.setErrorHandler(Timber::w);
        initLog();

    }

    private void initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.applyLocale(this);
    }


}



