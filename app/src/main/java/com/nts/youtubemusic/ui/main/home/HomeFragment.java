package com.nts.youtubemusic.ui.main.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.data.model.recent.Item;
import com.nts.youtubemusic.data.model.table.VideoChannel;
import com.nts.youtubemusic.databinding.FragmentHomeBinding;
import com.nts.youtubemusic.ui.adapter.HomeAdapter;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;
import com.nts.youtubemusic.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import timber.log.Timber;

public class HomeFragment extends BaseBindingFragment<FragmentHomeBinding, HomeViewModel> {

    private HomeAdapter homeAdapter;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            type = savedInstanceState.getInt(Constant.SAVE_TYPE);
        }
        initView();
        observerData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.SAVE_TYPE, type);
    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private void initView() {
        if (type == Constant.TYPE_HOME) {
            ((MainActivity) requireActivity()).setTextTitle(binding.include.txtTitleMain
                    , requireContext().getString(R.string.app_name)
                    , requireContext().getDrawable(R.drawable.ic_title_app));
        } else {
            ((MainActivity) requireActivity()).setTextTitle(binding.include.txtTitleMain
                    , requireContext().getString(R.string.ab_trending)
                    , null);
        }
        homeAdapter = new HomeAdapter();
        homeAdapter.setType(Constant.TYPE_HOME);
        homeAdapter.setListen(items -> {
            if (((MainActivity) requireActivity()).listIdVideo.size() > 0) {
                ((MainActivity) requireActivity()).listIdVideo.clear();
            }
            ((MainActivity) requireActivity()).showPlayVideo(items);
        });
        binding.rvListVideo.setAdapter(homeAdapter);
    }

    public void observerData() {
        mainViewModel.liveEventInternet.observe(getViewLifecycleOwner(), object -> {
            switch (((MessageEvent) object).getTypeEvent()) {
                case Constant.DISCONET_INTERNET_HOME:
                    Toast.makeText(requireContext(), requireContext().getString(R.string.disconnect_internet), Toast.LENGTH_LONG).show();
                    break;
                case Constant.CONNECT_INTERNET_HOME:
                    mainViewModel.searchVideo(Constant.HOME, Constant.MAX_RESULTS);
                    viewModel.getVideoHome(Constant.CODE);
                    break;
                case Constant.CONNECT_SLOW_HOME:
                    Toast.makeText(requireContext(), R.string.check_network, Toast.LENGTH_LONG).show();
                    break;
            }
        });
        if (type == Constant.TYPE_HOME) {
            mainViewModel.videoTrendingLiveDataVN.observe(getViewLifecycleOwner(), this::setDataAdapter);
        } else {
            viewModel.getVideoHome(Constant.CODE);
            viewModel.videoTrendingLiveDataVN.observe(getViewLifecycleOwner(), this::setDataAdapter);
        }
    }

    private void setDataAdapter(List<VideoChannel> videoYoutube) {
        if (homeAdapter != null && videoYoutube != null) {
            homeAdapter.setItems(videoYoutube);
            setVisibilityNone(GONE, VISIBLE);
        } else {
            setVisibilityNone(VISIBLE, GONE);
        }
    }


    private void setVisibilityNone(int v2, int v3) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isAdded()) binding.progress.setVisibility(View.GONE);
            binding.txtError.setVisibility(v2);
            binding.rvListVideo.setVisibility(v3);
        }, 500);

    }


    @Override
    protected void onPermissionGranted() {

    }


}
