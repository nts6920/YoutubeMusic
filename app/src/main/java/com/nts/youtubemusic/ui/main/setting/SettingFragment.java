package com.nts.youtubemusic.ui.main.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.databinding.FragmentSettingBinding;
import com.nts.youtubemusic.ui.base.BaseBindingFragment;
import com.nts.youtubemusic.ui.dialog.LanguageDialog;
import com.nts.youtubemusic.ui.dialog.PlayBgDialog;
import com.nts.youtubemusic.ui.main.MainActivity;
import com.nts.youtubemusic.utils.Utils;

public class SettingFragment extends BaseBindingFragment<FragmentSettingBinding, SettingViewModel> implements View.OnClickListener {


    @Override
    protected Class<SettingViewModel> getViewModel() {
        return SettingViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initView();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initView() {
        ((MainActivity) requireActivity()).setTextTitle(binding.include.txtTitleMain
                , requireContext().getString(R.string.app_name)
                , requireContext().getDrawable(R.drawable.ic_title_app));

        binding.txtLanguage.setCompoundDrawablePadding(Utils.dpToPx(8));
        binding.txPlayBackgroud.setCompoundDrawablePadding(Utils.dpToPx(8));
        binding.txtNotification.setCompoundDrawablePadding(Utils.dpToPx(8));
        binding.txtPolicy.setCompoundDrawablePadding(Utils.dpToPx(8));
        initListen();
    }

    private void initListen() {
        binding.txtLanguage.setOnClickListener(this);
        binding.txPlayBackgroud.setOnClickListener(this);
        binding.txtNotification.setOnClickListener(this);
        binding.txtPolicy.setOnClickListener(this);

    }

    @Override
    protected void onPermissionGranted() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_language:
                LanguageDialog languageDialog = new LanguageDialog();
                languageDialog.show(requireActivity().getSupportFragmentManager(), null);
                break;
            case R.id.tx_play_backgroud:
                showDialogConfirm(Constant.TYPE_HOME);
                break;
            case R.id.txt_notification:
                showDialogConfirm(Constant.TYPE_TRENDING);
                break;
            case R.id.txt_policy:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_YOUTUBE));
                startActivity(browserIntent);
                break;

        }

    }
    private void showDialogConfirm(int type) {
        PlayBgDialog dialogFragment = new PlayBgDialog();
        dialogFragment.setType(type);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), null);

    }

}
