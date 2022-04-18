package com.nts.youtubemusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.ui.main.MainActivity;
import com.nts.youtubemusic.utils.Utils;

public class NetworkChange extends BroadcastReceiver {

    private final MainActivity mainActivity;

    public NetworkChange(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = Utils.NetworkUtil.getConnectivityStatus(context);
        if (Constant.CONNECTIVITY_CHANGE.equals(intent.getAction())) {
            switch (status) {
                case Utils.NetworkUtil.TYPE_WIFI:
                    mainActivity.viewModel.liveEventInternet.postValue(new MessageEvent(Constant.CONNECT_INTERNET_HOME));
                    mainActivity.binding.tvInternet.setBackgroundColor(Color.GREEN);
                    mainActivity.binding.tvInternet.setText(context.getString(R.string.go_back_live));
                    new Handler().postDelayed(() -> mainActivity.binding.tvInternet.setVisibility(View.GONE), 2000);
                    break;
                case Utils.NetworkUtil.TYPE_NOT_CONNECTED:
                    mainActivity.viewModel.liveEventInternet.postValue(new MessageEvent(Constant.DISCONET_INTERNET_HOME));
                    mainActivity.binding.tvInternet.setVisibility(View.VISIBLE);
                    mainActivity.binding.tvInternet.setText(context.getString(R.string.no_connected));
                    mainActivity.binding.tvInternet.setBackgroundColor(Color.BLACK);
                    break;
            }

        }
    }
}
