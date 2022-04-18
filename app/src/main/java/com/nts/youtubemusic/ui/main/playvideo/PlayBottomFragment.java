package com.nts.youtubemusic.ui.main.playvideo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.MessageEvent;
import com.nts.youtubemusic.data.model.video.Items;
import com.nts.youtubemusic.data.model.video.Snippet;
import com.nts.youtubemusic.databinding.FragmentPlayBottomBinding;
import com.nts.youtubemusic.ui.adapter.HomeAdapter;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;
import com.nts.youtubemusic.ui.main.MainActivity;
import com.nts.youtubemusic.ui.main.comment.CommentFragment;
import com.nts.youtubemusic.utils.ConvertTime;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PlayBottomFragment extends BaseBindingFragment<FragmentPlayBottomBinding, PlayBottomViewModel> implements View.OnClickListener {
    String videoId;
    private String channelID;
    private String title;
    private HomeAdapter playAdapter;
    private CommentFragment commentFragment;


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
        switch (event.getTypeEvent()) {
            case Constant.NEXT_VIDEO:
                if (playAdapter != null && playAdapter.getItems() != null && playAdapter.getItems().size() > 0) {
                    Items item = playAdapter.getItems().get(0).getItem();
                    ((MainActivity) requireActivity()).listIdVideo.add(item);
                    ((MainActivity) requireActivity()).showPlayVideo(item);
                }
                break;
            case Constant.LOAD_DATA_VIDEO:
                ProgressDialog progressDialog = new ProgressDialog(getContext(),R.style.Custom);
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Handler().postDelayed(() -> {
                    if (isAdded()) {
                        ((MainActivity) requireActivity()).hideKeyboard(requireActivity());
                        binding.nestedScrollView.fling(0);
                        binding.nestedScrollView.smoothScrollTo(0, 0);
                        progressDialog.dismiss();
                    }
                }, 2000);
                break;
        }

    }


    @Override
    protected Class<PlayBottomViewModel> getViewModel() {
        return PlayBottomViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_play_bottom;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        observerData();
        initView();
    }

    private void initView() {
        initAdapter();
        initListener();
    }

    private void initListener() {
        binding.imgAdd.setOnClickListener(this);
        binding.txtCmt.setOnClickListener(this);
        binding.imgCmt.setOnClickListener(this);
        playAdapter.setListen(items -> {
            ((MainActivity) requireActivity()).listIdVideo.add(items);
            ((MainActivity) requireActivity()).showPlayVideo(items);
        });
    }

    private void initAdapter() {
        playAdapter = new HomeAdapter();
        binding.rvListVideo.setAdapter(playAdapter);
    }

    @SuppressLint("SetTextI18n")
    public void observerData() {
        mainViewModel.liveEventInternet.observe(getViewLifecycleOwner(), object -> {
            if (((MessageEvent) object).getTypeEvent() == Constant.CONNECT_INTERNET_HOME) {
                if (((MainActivity) requireActivity()).isCheckMaxScreen()) {
                    initData();
                    binding.rvListVideo.setVisibility(View.VISIBLE);
                }
            } else if (((MessageEvent) object).getTypeEvent() == Constant.DISCONET_INTERNET_HOME) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.disconnect_internet), Toast.LENGTH_LONG).show();
                binding.rvListVideo.setVisibility(View.GONE);
            }
        });
        viewModel.channelMutableLiveData.observe(getViewLifecycleOwner(), channel -> {
            if (channel != null && channel.getItems() != null && channel.getItems().size() > 0) {
                if (channel.getItems().get(0).getSnippet() != null && channel.getItems().get(0).getSnippet().getTitle() != null
                        && channel.getItems().get(0).getSnippet().getPublishedAt() != null && channel.getItems().get(0).getSnippet().getThumbnails() != null
                        && channel.getItems().get(0).getSnippet().getThumbnails().getHigh() != null
                        && channel.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl() != null) {
                    binding.txtChannel.setText(channel.getItems().get(0).getSnippet().getTitle());
                    binding.txtSub.setText(ConvertTime.convertSub(binding.getRoot().getContext(), channel.getItems().get(0)) + " - " +
                            ConvertTime.convertTime(binding.getRoot().getContext(), channel.getItems().get(0).getSnippet().getPublishedAt()));
                    Glide.with(binding.imgChannel).load(channel.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl()).into(binding.imgChannel);

                }

            }
        });

        viewModel.videoTrendingLiveDataVN.observe(getViewLifecycleOwner(), videoChannels -> {
            if (videoChannels != null) {
                for (int i = 0; i < videoChannels.size(); i++) {
                    Items items = videoChannels.get(i).getItem();
                    if (items.getId() != null && items.getId().equals(videoId)) {
                        videoChannels.remove(videoChannels.get(i));
                    }
                }
                playAdapter.setItems(videoChannels);
            } else {
                playAdapter.setItems(null);
                binding.txtCmtError.setVisibility(View.VISIBLE);
            }

        });

        viewModel.commentMutableLiveData.observe(getViewLifecycleOwner(), comment ->
        {
            binding.imgCmt.setVisibility(comment != null ? View.VISIBLE : View.GONE);
            if (comment != null) {
                if (comment.getItems() != null && comment.getItems().size() > 0) {
                    Snippet snippet = comment.getItems().get(0).getSnippet();
                    if (snippet != null && snippet.getTopLevelComment() != null
                            && snippet.getTopLevelComment().getSnippet() != null
                            && snippet.getTopLevelComment().getSnippet().getAuthorProfileImageUrl() != null
                            && snippet.getTopLevelComment().getSnippet().getTextOriginal() != null)
                        Glide.with(binding.imgCmt).load(comment.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl()).into(binding.imgCmt);
                    binding.txtCmt.setText(comment.getItems().get(0).getSnippet().getTopLevelComment().getSnippet().getTextOriginal());
                    binding.imgAdd.setVisibility(View.VISIBLE);
                }
            } else {
                binding.imgAdd.setVisibility(View.GONE);
                binding.txtCmt.setText(null);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void initDataVideo(Items items) {
        if (items != null && items.getSnippet() != null && items.getStatistics() != null && items.getStatistics().getViewCount() != null &&
                items.getSnippet() != null && items.getSnippet().getPublishedAt() != null && items.getSnippet().getChannelId() != null
                && items.getSnippet().getTitle() != null && items.getId() != null) {
            channelID = items.getSnippet().getChannelId();
            title = items.getSnippet().getTitle();
            videoId = items.getId();
            binding.txtCommentView.setText(ConvertTime.convertCommentView(items));
            binding.txtView.setText(ConvertTime.convertViewCount(binding.getRoot().getContext(), items.getStatistics().getViewCount())
                    + " - " + ConvertTime.convertTime(binding.getRoot().getContext(), items.getSnippet().getPublishedAt()));
            binding.txtDislike.setText(ConvertTime.convertDisLikeCount(items));
            binding.txtLike.setText(ConvertTime.convertLikeCount(items));
            initData();
            binding.txtTitle.setText(title);

        }

    }

    public void initData() {
        viewModel.searchVideo(title);
        viewModel.getChannel(channelID);
        viewModel.getComment(videoId);

    }
    public void showComment() {
        commentFragment = new CommentFragment();
        ((MainActivity) requireActivity()).checkShowComment = true;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.play_bottom_video, commentFragment, PlayBottomFragment.class.getSimpleName()).commit();
    }

    @Override
    protected void onPermissionGranted() {

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add:
            case R.id.txt_cmt:
            case R.id.img_cmt:
                showComment();
                break;
        }
    }
}
