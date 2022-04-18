package com.nts.youtubemusic.ui.main.splash;

import android.os.Bundle;
import android.view.View;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.databinding.FragmentSplashBinding;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;

public class SplashFragment extends BaseBindingFragment<FragmentSplashBinding, SplashViewModel> {
    @Override
    protected Class<SplashViewModel> getViewModel() {
        return SplashViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
    }

    @Override
    protected void onPermissionGranted() {

    }
}