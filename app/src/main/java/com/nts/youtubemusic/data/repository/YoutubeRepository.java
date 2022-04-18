package com.nts.youtubemusic.data.repository;

import com.nts.youtubemusic.data.model.channel.Channel;
import com.nts.youtubemusic.data.model.comment.Comment;
import com.nts.youtubemusic.data.model.recent.Search;
import com.nts.youtubemusic.data.model.video.VideoYoutube;
import com.nts.youtubemusic.data.remote.YoutubeAPI;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class YoutubeRepository {
    public final YoutubeAPI youtubeAPI;

    @Inject
    public YoutubeRepository(YoutubeAPI youtubeAPI) {
        this.youtubeAPI = youtubeAPI;
    }

    public Single<Channel> getChannel(String id, String key) {
        return youtubeAPI.getChannel(id, key);
    }

    public Single<VideoYoutube> getVideoTrending(int maxResults, String key, String regionCode) {
        return youtubeAPI.getVideoYoutube(maxResults, key, regionCode);
    }

    public Single<Search> getVideoSearch(String key, String part, String q, String type,
                                         int maxResult) {
        return youtubeAPI.getVideoSearch(key, part, q, type, maxResult);
    }

    public Single<Comment> getComment(int maxResult, String videoId, String key) {
        return youtubeAPI.getCommentYoutube(maxResult, videoId, key);
    }

}

