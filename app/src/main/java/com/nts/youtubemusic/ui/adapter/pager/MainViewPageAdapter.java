package com.nts.youtubemusic.ui.adapter.pager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.ui.main.home.HomeFragment;
import com.nts.youtubemusic.ui.main.search.SearchFragment;
import com.nts.youtubemusic.ui.main.setting.SettingFragment;

@SuppressWarnings("ALL")
public class MainViewPageAdapter extends FragmentStatePagerAdapter {
    private HomeFragment homeFragment;
    private HomeFragment trendingFragment;
    private SettingFragment settingFragment;
    private SearchFragment searchFragment;

    public MainViewPageAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) homeFragment = new HomeFragment();
                homeFragment.setType(Constant.TYPE_HOME);
                return homeFragment;
            case 1:
                if (trendingFragment == null) trendingFragment = new HomeFragment();
                trendingFragment.setType(Constant.TYPE_TRENDING);
                return trendingFragment;
            case 2:
                if (settingFragment == null) settingFragment = new SettingFragment();
                return settingFragment;
            case 3:
            default:
                if (searchFragment == null) searchFragment = new SearchFragment();
                return searchFragment;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}


