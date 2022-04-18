package com.nts.youtubemusic.data.model.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemChannel {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("snippet")
    @Expose
    private SnippetChannel snippet;
    @SerializedName("statistics")
    @Expose
    private StatisticsChannel statistics;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SnippetChannel getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetChannel snippet) {
        this.snippet = snippet;
    }

    public StatisticsChannel getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsChannel statistics) {
        this.statistics = statistics;
    }

}

