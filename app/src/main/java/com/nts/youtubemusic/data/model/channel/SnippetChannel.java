package com.nts.youtubemusic.data.model.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nts.youtubemusic.data.model.recent.Thumbnails;
import com.nts.youtubemusic.data.model.video.Localized;

public class SnippetChannel {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("customUrl")
    @Expose
    private String customUrl;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    @SerializedName("defaultLanguage")
    @Expose
    private String defaultLanguage;
    @SerializedName("localized")
    @Expose
    private Localized localized;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public Localized getLocalized() {
        return localized;
    }

    public void setLocalized(Localized localized) {
        this.localized = localized;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

