package com.nts.youtubemusic.common;

import com.nts.youtubemusic.data.model.recent.Item;

public  class MessageEvent {
    private int typeEvent =0;
    private String stringValue ="";
    private Item item;
    private String str;

    public MessageEvent(String str) {
        this.str = str;
    }

    public MessageEvent(Item item) {
        this.item = item;
    }

    public MessageEvent(int typeEvent, String stringValue) {
        this.typeEvent = typeEvent;
        this.stringValue = stringValue;
    }

    public MessageEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
