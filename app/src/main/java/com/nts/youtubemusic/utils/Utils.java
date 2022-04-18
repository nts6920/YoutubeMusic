package com.nts.youtubemusic.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static int pxToDp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static boolean isAtLeastSdkVersion(int versionCode) {
        return Build.VERSION.SDK_INT >= versionCode;
    }

    public static Bitmap getIcon(String src) {
        Bitmap bitmap = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(src);
            bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static class NetworkUtil {
        public static final int TYPE_NOT_CONNECTED = 0;
        public static final int TYPE_WIFI = 1;
        public static final int TYPE_MOBILE = 2;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_WIFI;
                }
                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }

    }

    public static List<String> setListApi(){
        List<String>list = new ArrayList<>();
        list.add("AIzaSyDlPIAkQSGQjMKLWPBEdP6KmkZZihrNJWk");
        list.add("AIzaSyBMUjzN4CKSHtxuFKguTksxpK8MIyg3I2Y");
        list.add("AIzaSyC9B8dYnwcarkCzSP5nSSJxkGTOczm1_k0");
        list.add("AIzaSyAxSma_MMh9WuQQULrmXscvSVQuvtthdMY");
        list.add("AIzaSyCrX6RDsVucuNNeewOIOgOxwd9UJg9ZaoU");
        list.add("AIzaSyCcB3eiJ9QTZtgV60_jeuaRn0K0HStMXWw");
        list.add("AIzaSyBerVzUR5s4OiFU-waj5noE3TGJKZFnPaY");
        list.add("AIzaSyAycjpTgl0dllejFmLHqhnkezF3yh227H4");
        list.add("AIzaSyC9B8dYnwcarkCzSP5nSSJxkGTOczm1_k0");
        list.add("AIzaSyBMUjzN4CKSHtxuFKguTksxpK8MIyg3I2Y");

        return list;
    }
}

