package com.nts.youtubemusic.data.remote;

import com.nts.youtubemusic.data.model.channel.Channel;
import com.nts.youtubemusic.data.model.comment.Comment;
import com.nts.youtubemusic.data.model.recent.Search;
import com.nts.youtubemusic.data.model.video.VideoYoutube;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeAPI {
    @GET("search/")
    Single<Search> getVideoSearch(@Query("key") String key,
                                  @Query("part") String part,
                                  @Query("q") String q,
                                  @Query("type") String type,
                                  @Query("maxResults") int maxResults);

    @GET("channels?part=snippet&part=statistics")
    Single<Channel> getChannel(@Query("id") String id,
                               @Query("key") String key);

    @GET("videos?part=snippet,contentDetails,statistics&chart=mostPopular")
    Single<VideoYoutube> getVideoYoutube(@Query("maxResults") int maxResults,
                                         @Query("key") String key,
                                         @Query("regionCode") String regionCode);



    @GET("commentThreads?part=snippet")
    Single<Comment> getCommentYoutube(@Query("maxResults") int maxResults, @Query("videoId") String videoId, @Query("key") String key);

    @GET("videos?part=snippet&part=contentDetails&part=statistics")
    Single<VideoYoutube> getVideo(@Query("id") String videoId,
                                  @Query("key") String key
    );

}
