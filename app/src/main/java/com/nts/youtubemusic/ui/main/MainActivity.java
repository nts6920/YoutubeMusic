package com.nts.youtubemusic.ui.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.data.local.SharedPreferenceHelper;
import com.nts.youtubemusic.data.model.video.Items;
import com.nts.youtubemusic.data.model.video.Snippet;
import com.nts.youtubemusic.databinding.ActivityMainBinding;
import com.nts.youtubemusic.receiver.AlarmMorning;
import com.nts.youtubemusic.receiver.NetworkChange;
import com.nts.youtubemusic.ui.adapter.pager.MainViewPageAdapter;
import com.nts.youtubemusic.ui.base.BaseBindingActivity;
import com.nts.youtubemusic.ui.custom.DragSourceView;
import com.nts.youtubemusic.ui.main.playvideo.PlayBottomFragment;
import com.nts.youtubemusic.ui.main.playvideo.PlayTopFragment;
import com.nts.youtubemusic.utils.Utils;
import com.tuanhav95.drag.DragView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding, MainViewModel> {
    public boolean checkShowComment = false;
    public List<Items> listIdVideo = new ArrayList<>();
    private PlayTopFragment playTopFragment;
    private PlayBottomFragment playBottomFragment;
    private boolean checkMaxScreen = false;
    private String titleVideo;
    private String channelVideo;
    private MenuItem prevMenuItem;
    private String videoId;
    private NotificationManager notificationManager;
    private String imageVideo;
    private Items itemSave;
    private NetworkChange networkChange;
    private int orientation;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.SAVE_ITEM, new Gson().toJson(itemSave));
        outState.putBoolean(Constant.SAVE_DRAGVIEW, checkMaxScreen);
        outState.putBoolean(Constant.SAVE_COMMENT, checkShowComment);
        outState.putInt(Constant.SAVE_VIEWPAGE, binding.vpMain.getCurrentItem());
    }

    @Override
    public Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void setupView(Bundle savedInstanceState) {
        networkChange = new NetworkChange(this);
        registerNetworkBroadcastForNougat();
        showGetNotificationMorning();
        disableMenuTooltip();
        playTopFragment = new PlayTopFragment();
        playBottomFragment = new PlayBottomFragment();
        setupViewPager(binding.vpMain);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.homeFragment:
                            binding.vpMain.setCurrentItem(0);
                            break;
                        case R.id.trendingFragment:
                            binding.vpMain.setCurrentItem(1);
                            break;
                        case R.id.settingFragment:
                            binding.vpMain.setCurrentItem(2);
                            break;
                        case R.id.searchFragment:
                            binding.vpMain.setCurrentItem(3);
                            break;
                    }
                    return false;
                });
        binding.vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        hideKeyboard(MainActivity.this);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    binding.bottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = binding.bottomNavigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        drag();
        if (savedInstanceState != null) {
            itemSave = new Gson().fromJson(savedInstanceState.getString(Constant.SAVE_ITEM), new TypeToken<Items>() {
            }.getType());
            checkShowComment = savedInstanceState.getBoolean(Constant.SAVE_COMMENT);
            checkMaxScreen = savedInstanceState.getBoolean(Constant.SAVE_DRAGVIEW);
            if (checkMaxScreen) {

                new Handler().postDelayed(() -> {
                    if (playTopFragment.isAdded() && playBottomFragment.isAdded()) {
                        showPlayVideo(itemSave);
                        if (checkShowComment) {
                            playBottomFragment.showComment();
                        }
                    }
                }, 200);
            }
            binding.vpMain.setCurrentItem(savedInstanceState.getInt(Constant.SAVE_VIEWPAGE));
        }

    }

    private void disableMenuTooltip() {
        View bottom1 = binding.bottomNavigation.findViewById(R.id.homeFragment);
        View bottom2 = binding.bottomNavigation.findViewById(R.id.trendingFragment);
        View bottom3 = binding.bottomNavigation.findViewById(R.id.settingFragment);
        View bottom4 = binding.bottomNavigation.findViewById(R.id.searchFragment);

        View.OnLongClickListener longClickListener = v -> true;

        bottom1.setOnLongClickListener(longClickListener);
        bottom2.setOnLongClickListener(longClickListener);
        bottom3.setOnLongClickListener(longClickListener);
        bottom4.setOnLongClickListener(longClickListener);
    }


    public void setTextTitle(AppCompatTextView tvTitle, String title, Drawable icon) {
        Shader shader = new LinearGradient(0, 0, 0, tvTitle.getLineHeight(),
                Constant.colorStart, Constant.colorEnd, Shader.TileMode.REPEAT);
        tvTitle.getPaint().setShader(shader);
        tvTitle.setText(title);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        tvTitle.setCompoundDrawablePadding(Utils.dpToPx(12));
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void show_Notification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        NotificationChannel notificationChannel = new NotificationChannel(Constant.CHANNEL_ID, Constant.PRIORITY_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), Constant.REQUEST_CODE_1, intent, 0);
        Notification notification = new Notification.Builder(getApplicationContext(), Constant.CHANNEL_ID).setLargeIcon(Utils.getIcon(imageVideo))
                .setContentText(channelVideo)
                .setContentTitle(titleVideo)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_title_app)
                .build();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(Constant.NOTIFICATION_ID_1, notification);
    }


    public void closedNotification() {
        if (notificationManager != null) {
            notificationManager.cancel(Constant.NOTIFICATION_ID_1);

        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public void StartAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 23);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), AlarmMorning.class);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    @Override
    public void setupData() {
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public String getChannelVideo() {
        return channelVideo;
    }

    public void setupViewPager(ViewPager viewPager) {
        MainViewPageAdapter adapter = new MainViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void showPlayVideo(Items items) {
        itemSave = items;
        binding.dragView.maximize();
        checkMaxScreen = true;
        EventBus.getDefault().post(new MessageEvent(Constant.LOAD_DATA_VIDEO));
        Snippet snippet = items.getSnippet();
        titleVideo = snippet.getTitle();
        channelVideo = snippet.getChannelTitle();
        imageVideo = snippet.getThumbnails().getHigh().getUrl();
        videoId = items.getId();
        viewModel.commentLiveData.setValue(videoId);
        playTopFragment.initYouTubePlayerView(videoId);
        playBottomFragment.initDataVideo(items);
        if (listIdVideo.size() == 0) {
            listIdVideo.add(items);
        }
        if (checkShowComment) {
            EventBus.getDefault().post(new MessageEvent(Constant.REMOVE_COMMENT_FRAGMENT));
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.dragView.setHeightMax(ViewGroup.LayoutParams.MATCH_PARENT, true);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        hideKeyboard(MainActivity.this);

    }

    public void drag() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameTop, playTopFragment).addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameBottom, playBottomFragment).addToBackStack(null).commit();
        if (binding.dragView.isMinimize()) {
            checkMaxScreen = false;
        }

        binding.dragView.setDragListener(new DragView.DragListener() {
            @Override
            public void onExpanded() {

            }

            @Override
            public void onChangeState(@NonNull DragView.State state) {

                if (!checkMaxScreen) {
                    playTopFragment.hideIconVideo();
                } else {
                    playTopFragment.showIconVideo();
                }
            }

            @Override
            public void onChangePercent(float percent) {


            }
        });

    }

    public void exitVideo() {
        playTopFragment.exitBackgroundVideo();
    }

    public void pauseVideo() {
        playTopFragment.pauseVideo();
    }

    public void playVideo() {
        playTopFragment.playVideo();
    }


    public boolean isCheckMaxScreen() {
        return checkMaxScreen;
    }

    public void setCheckMaxScreen(boolean checkMaxScreen) {
        this.checkMaxScreen = checkMaxScreen;
    }

    public void hideNavigation() {
        binding.bottomNavigation.setVisibility(View.INVISIBLE);
    }

    public void showNavigation() {
        binding.bottomNavigation.setVisibility(View.VISIBLE);

    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void exitFullScreen() {
        if (playTopFragment.getHeightBeforeMax() > 0) {
            binding.dragView.setHeightMax(playTopFragment.getHeightBeforeMax(), true);
        }
    }


    @Override
    public void onBackPressed() {
        if (checkMaxScreen) {
            if (checkShowComment) {
                EventBus.getDefault().post(new MessageEvent(Constant.REMOVE_COMMENT_FRAGMENT));
            } else {
                binding.dragView.minimize();
                exitFullScreen();
                checkMaxScreen = false;
            }

        } else {
            if (binding.vpMain.getCurrentItem() != 0) {
                binding.vpMain.setCurrentItem(binding.vpMain.getCurrentItem() - 1, false);
            } else {
                finish();
            }
        }
    }

    private void registerNetworkBroadcastForNougat() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Constant.CONNECTIVITY_CHANGE);
        registerReceiver(networkChange, intentFilter);


    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(networkChange);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        orientation = newConfig.orientation;
        if (checkMaxScreen) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(0);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                exitFullScreen();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Window window = getWindow();
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                );
                window.setStatusBarColor(Color.TRANSPARENT);
                binding.dragView.setHeightMax(ViewGroup.LayoutParams.MATCH_PARENT, true);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkChanges();
    }

    public void showGetNotificationMorning() {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext(), Constant.PREF_SETTING_LANGUAGE);
        if (sharedPreferenceHelper.getBoolean(Constant.ON_NOTIFICATION, false)) {
            StartAlarm();
        } else if (sharedPreferenceHelper.getBoolean(Constant.OFF_NOTIFICATION, false)) {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
            }

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
        closedNotification();
        showGetNotificationMorning();
    }


}

