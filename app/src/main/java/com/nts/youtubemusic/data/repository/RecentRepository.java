package com.nts.youtubemusic.data.repository;

import com.nts.youtubemusic.data.local.db.RecentDAO;
import com.nts.youtubemusic.data.model.table.Recent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class RecentRepository {
    public RecentDAO recentDAO;

    @Inject
    public RecentRepository(RecentDAO recentDAO) {
        this.recentDAO = recentDAO;
    }

    public Single<List<Recent>> getRecent() {
        return recentDAO.getRecents();
    }

    public void insertRecent(Recent recent) {
        recentDAO.insertRecent(recent);
    }

    public void deleteRecent(Recent recent) {
        recentDAO.deleteRecent(recent);
    }

}