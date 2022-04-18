package com.nts.youtubemusic.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nts.youtubemusic.data.model.table.Recent;

@Database(entities = {Recent.class}, version = 1)
public abstract class RecentDatabase extends RoomDatabase {
    public abstract RecentDAO recentDAO();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

}