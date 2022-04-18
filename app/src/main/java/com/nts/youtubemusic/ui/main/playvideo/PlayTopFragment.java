package com.nts.youtubemusic.ui.main.playvideo;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.data.local.SharedPreferenceHelper;
import com.nts.youtubemusic.data.model.recent.Item;
import com.nts.youtubemusic.databinding.FragmentPlayTopBinding;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;
import com.nts.youtubemusic.ui.custom.DragSourceView;
import com.nts.youtubemusic.ui.main.MainActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class PlayTopFragment extends BaseBindingFragment<FragmentPlayTopBinding, PlayTopViewModel> {
    private YouTubePlayer youTubePlayer;
    private final AbstractYouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
        @Override
        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
            PlayTopFragment.this.youTubePlayer = youTubePlayer;

        }
    };

    private int heightBeforeMax = 0;
    private final YouTubePlayerFullScreenListener fullScreenListener = new YouTubePlayerFullScreenListener() {
        @Override
        public void onYouTubePlayerEnterFullScreen() {
            Timber.e("son2 = " + heightBeforeMax);
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Timber.e("son2 = " + heightBeforeMax);
            ((MainActivity) requireActivity()).binding.dragView.setHeightMax(ViewGroup.LayoutParams.MATCH_PARENT, true);
        }

        @Override
        public void onYouTubePlayerExitFullScreen() {
            ((MainActivity) requireActivity()).binding.dragView.setHeightMax(heightBeforeMax, true);
            Timber.e("son2 = " + heightBeforeMax);
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    };

    public int getHeightBeforeMax() {
        return heightBeforeMax;
    }


    @Override
    protected Class<PlayTopViewModel> getViewModel() {
        return PlayTopViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_play_top;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.youTubePlayerView.addYouTubePlayerListener(listener);
        heightBeforeMax = ((MainActivity) requireActivity()).binding.dragView.getMTempHeight() + 50;
        binding.seekBar.setThumb(requireContext().getDrawable(R.drawable.ic_seekbar));
    }

    public void hideIconVideo() {
        intVisible(false, View.VISIBLE);
    }

    public void showIconVideo() {
        intVisible(true, View.GONE);
    }

    private void intVisible(boolean check, int gone) {
        binding.youTubePlayerView.getPlayerUiController().showPlayPauseButton(check);
        binding.youTubePlayerView.getPlayerUiController().showSeekBar(check);
        binding.youTubePlayerView.getPlayerUiController().showFullscreenButton(check);
        binding.youTubePlayerView.getPlayerUiController().showVideoTitle(check);
        binding.youTubePlayerView.getPlayerUiController().showCustomAction1(check);
        binding.youTubePlayerView.getPlayerUiController().showCustomAction2(check);
        binding.seekBar.setVisibility(gone);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initYouTubePlayerView(String videoId) {
        if (youTubePlayer != null && videoId != null && !videoId.isEmpty()) {
            youTubePlayer.loadVideo(videoId, 0);
            youTubePlayer.addListener(binding.seekBar);
        } else {
            binding.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });
        }
        binding.seekBar.setYoutubePlayerSeekBarListener(time -> youTubePlayer.seekTo(time));
        if (((MainActivity) requireActivity()).listIdVideo.size() >= 2) {
            binding.youTubePlayerView.getPlayerUiController().setCustomAction1(getResources().getDrawable(R.drawable.ic_previos_on), v -> previousVideo());
        } else {
            binding.youTubePlayerView.getPlayerUiController().setCustomAction1(getResources().getDrawable(R.drawable.ic_skip_previous), v -> {
            });
        }


        binding.youTubePlayerView.getPlayerUiController().setCustomAction2(getResources().getDrawable(R.drawable.ic_skip_next), v -> EventBus.getDefault().post(new MessageEvent(Constant.NEXT_VIDEO)));
        binding.youTubePlayerView.getPlayerUiController().showCustomAction1(true);
        binding.youTubePlayerView.getPlayerUiController().showCustomAction2(true);
        binding.youTubePlayerView.getPlayerUiController().showYouTubeButton(false);

        binding.youTubePlayerView.addFullScreenListener(fullScreenListener);

    }


    private void previousVideo() {

        if (((MainActivity) requireActivity()).listIdVideo.size() >= 2) {
            ((MainActivity) requireActivity()).listIdVideo.remove(((MainActivity) requireActivity()).listIdVideo.get(((MainActivity) requireActivity()).listIdVideo.size() - 1));
            ((MainActivity) requireActivity()).showPlayVideo(((MainActivity) requireActivity()).listIdVideo.get(((MainActivity) requireActivity()).listIdVideo.size() - 1));

        }
    }


    public void exitBackgroundVideo() {
        pauseVideo();
        binding.youTubePlayerView.enableBackgroundPlayback(false);
    }

    public void onPlayBackground() {
        binding.youTubePlayerView.enableBackgroundPlayback(true);


    }

    public void offPlayBackground() {
        binding.youTubePlayerView.enableBackgroundPlayback(false);
    }


    public void pauseVideo() {
        if (youTubePlayer != null) {
            youTubePlayer.pause();

        }
    }

    public void playVideo() {
        if (youTubePlayer != null) {
            youTubePlayer.play();
        }
    }


    @Override
    protected void onPermissionGranted() {

    }

    @Override
    public void onPause() {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(requireActivity(), Constant.PREF_SETTING_LANGUAGE);
        if (sharedPreferenceHelper.getBoolean(Constant.ON_PLAY_BACKGROUND, false)) {
            onPlayBackground();
            ((MainActivity) requireActivity()).show_Notification();
            super.onPause();
        } else if (sharedPreferenceHelper.getBoolean(Constant.OFF_PLAY_BACKGROUND, false)) {
            offPlayBackground();
            ((MainActivity) requireActivity()).closedNotification();
            super.onPause();
        } else {
            super.onPause();

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.youTubePlayerView.removeYouTubePlayerListener(listener);
        binding.youTubePlayerView.removeFullScreenListener(fullScreenListener);
        binding.youTubePlayerView.release();
    }


}
