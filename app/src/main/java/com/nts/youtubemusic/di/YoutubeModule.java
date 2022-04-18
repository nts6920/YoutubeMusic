package com.nts.youtubemusic.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.room.Room;

import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.data.local.db.RecentDAO;
import com.nts.youtubemusic.data.local.db.RecentDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@SuppressWarnings("ALL")
@Module
@InstallIn(SingletonComponent.class)
public class YoutubeModule {
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreference(Application context) {
        return PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Provides
    @Singleton
    public RecentDatabase provideRoomDb(Application context) {
        return Room.databaseBuilder(context, RecentDatabase.class, Constant.DB_NAME)
                .fallbackToDestructiveMigration()
                .addMigrations(RecentDatabase.MIGRATION_1_2).allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    public RecentDAO pRecentDAO(RecentDatabase db) {
        return db.recentDAO();
    }

}
