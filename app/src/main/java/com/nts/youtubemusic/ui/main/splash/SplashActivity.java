package com.nts.youtubemusic.ui.main.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.annotation.RequiresApi;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.ui.base.BaseActivity;
import com.nts.youtubemusic.ui.main.MainActivity;

public class SplashActivity extends BaseActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_splash);
        handler = new Handler(this.getMainLooper());
        handler.postDelayed(this::startActivity, 2000);
        //
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
        window.setStatusBarColor(Color.TRANSPARENT);

    }

    public void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @SuppressLint("InlinedApi")
    @Override
    public void onDestroy() {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onDestroy();
    }

}
