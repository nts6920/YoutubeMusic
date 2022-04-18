package com.nts.youtubemusic.ui.main.comment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.databinding.FragmentCommentBinding;
import com.nts.youtubemusic.ui.adapter.CommentAdapter;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;
import com.nts.youtubemusic.ui.main.MainActivity;
import com.nts.youtubemusic.ui.main.playvideo.PlayBottomFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import timber.log.Timber;

public class CommentFragment extends BaseBindingFragment<FragmentCommentBinding, CommentViewModel> {
    private String videoID;
    private CommentAdapter commentAdapter;

    @Override
    protected Class<CommentViewModel> getViewModel() {
        return CommentViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMyDesign(MessageEvent event) {
        if (event.getTypeEvent() == Constant.REMOVE_COMMENT_FRAGMENT) {
            closeFragment();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.VIDEO_ID, videoID);
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            videoID = savedInstanceState.getString(Constant.videoID);
        }
        initView();
        observerData();
    }

    private void initView() {
        commentAdapter = new CommentAdapter();
        binding.rvListComment.setAdapter(commentAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        binding.rvListComment.addItemDecoration(dividerItemDecoration);
        binding.imgClose.setOnClickListener(v ->
                closeFragment());
    }

    private void closeFragment() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment commentFragment = getParentFragmentManager().findFragmentByTag(PlayBottomFragment.class.getSimpleName());
        if (commentFragment != null) {
            transaction.remove(commentFragment).commit();
        }
    }


    private void observerData() {
        mainViewModel.commentLiveData.observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                videoID = s;
                mainViewModel.getComment(videoID);
            }
        });
        mainViewModel.commentMutableLiveData.observe(getViewLifecycleOwner(), comment -> {
            if (comment != null && comment.getItems() != null) {
                commentAdapter.setCommentList(comment.getItems());
            }
        });

    }


    @Override
    protected void onPermissionGranted() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) requireActivity()).checkShowComment = false;
    }
}

