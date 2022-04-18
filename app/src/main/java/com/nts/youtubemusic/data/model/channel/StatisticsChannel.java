package com.nts.youtubemusic.data.model.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsChannel {
    @SerializedName("viewCount")
    @Expose
    private String viewCount;
    @SerializedName("subscriberCount")
    @Expose
    private String subscriberCount;
    @SerializedName("hiddenSubscriberCount")
    @Expose
    private Boolean hiddenSubscriberCount;
    @SerializedName("videoCount")
    @Expose
    private String videoCount;

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public Boolean getHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }

    public void setHiddenSubscriberCount(Boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }
}
