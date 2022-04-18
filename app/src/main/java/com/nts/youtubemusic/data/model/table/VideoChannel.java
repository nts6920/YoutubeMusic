package com.nts.youtubemusic.data.model.table;

import androidx.room.Ignore;

import com.nts.youtubemusic.data.model.channel.Channel;
import com.nts.youtubemusic.data.model.recent.Item;
import com.nts.youtubemusic.data.model.video.Items;

public class VideoChannel {

    private Items item;
    private Item itemSearch;
    private Channel channel;

    @Ignore
    public VideoChannel(Items item,Item itemSearch, Channel channel) {
        this.item = item;
        this.itemSearch=itemSearch;
        this.channel = channel;
    }


    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Item getItemSearch() {
        return itemSearch;
    }

    public void setItemSearch(Item itemSearch) {
        this.itemSearch = itemSearch;
    }

}

