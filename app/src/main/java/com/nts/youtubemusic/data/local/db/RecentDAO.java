package com.nts.youtubemusic.data.local.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nts.youtubemusic.data.model.table.Recent;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@SuppressWarnings("unused")
@Dao
public interface RecentDAO {

    @Query("Select * from Recent")
    Single<List<Recent>> getRecents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecent(Recent... recents);

    @Delete
    void deleteRecent(Recent... recents);

    @Query("Select *from Recent where name like :mName")
    Single<List<Recent>> getRecentSearch(String mName);

}