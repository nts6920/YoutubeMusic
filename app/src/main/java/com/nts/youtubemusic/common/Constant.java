package com.nts.youtubemusic.common;

import static com.nts.youtubemusic.App.getContext;

import androidx.core.content.ContextCompat;

import com.nts.youtubemusic.R;

public class Constant {

    //info video youtube
    public static final String HOME = "hoạt hình";
    public static final String TYPE = "VIDEO";
    public static final String NULL = "null";
    public static final String videoID = "videoID";
    public static final String PART = "snippet";
    public static final String CODE = "VN";
    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String Alarm_ID = "Alarm_ID";
    public static final String PRIORITY_CHANNEL_ID = "PRIORITY_CHANNEL_ID";
    public static final String PRIORITY_Alarm_ID = "PRIORITY_Alarm_ID";
    public static final String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    //room database
    public static final String DB_NAME = "Recent";
    //multiple language
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_VN = "vi";
    //color title main activity
    public static final int colorStart = ContextCompat.getColor(getContext(), R.color.text_red_gran_1);
    public static final int color = ContextCompat.getColor(getContext(), R.color.teal_700);
    public static final int colorEnd = ContextCompat.getColor(getContext(), R.color.text_red_gran_2);
    //SharedPreferences
    public static final String ON_PLAY_BACKGROUND = "ON_PLAY_BACKGROUND";
    public static final String OFF_PLAY_BACKGROUND = "OFF_PLAY_BACKGROUND";
    public static final String ON_NOTIFICATION = "ON_NOTIFICATION";
    public static final String OFF_NOTIFICATION = "OFF_NOTIFICATION";
    public static final String PREF_SETTING_LANGUAGE = "PREF_SETTING_LANGUAGE";
    //eventBus
    public static final int REMOVE_COMMENT_FRAGMENT = 2;
    public static final int NEXT_VIDEO = 3;
    public static final int LOAD_DATA_VIDEO = 4;
    public static final int CONNECT_INTERNET_HOME = 5;
    public static final int DISCONET_INTERNET_HOME = 6;
    public static final int MAX_RESULTS = 10;
    public static final int MAX_RESULTS_TITLE = 3;
    public static final int MAX_RESULTS_COMMENT = 50;
    public static final int TYPE_HOME = 0;
    public static final int TYPE_TRENDING = 1;
    //RequestCode
    public static final int REQUEST_CODE_1 = 1;
    public static final int CONNECT_S = 10;
    public static final int READ_S = 10;
    public static final int WRITE_S = 10;
    //notification
    public static final int NOTIFICATION_ID_1 = 1;
    //gg
    public static final String LINK_YOUTUBE = "https://www.youtube.com/";
    public static final String BASE_URL = "https://youtube.googleapis.com/youtube/v3/";
    //kill app
    public static final String SAVE_TYPE = "SAVE_TYPE";
    public static final String SAVE_ITEM = "SAVE_ITEM";
    public static final String SAVE_DRAGVIEW = "SAVE_DRAGVIEW";
    public static final String SAVE_COMMENT = "SAVE_COMMENT";
    public static final String SAVE_VIEWPAGE = "SAVE_VIEWPAGE";
    public static final String VIDEO_ID = "VIDEO_ID";
    public static final String SAVE_QUERY = "SAVE_QUERY";
    public static final int CONNECT_SLOW_HOME = 8;
    // keyAPI
    public static String API_KEY = "AIzaSyDlPIAkQSGQjMKLWPBEdP6KmkZZihrNJWk";

}
