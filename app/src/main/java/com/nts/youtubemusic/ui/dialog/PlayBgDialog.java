package com.nts.youtubemusic.ui.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.data.local.SharedPreferenceHelper;
import com.nts.youtubemusic.databinding.DialogPlaybackgroundBinding;
import com.nts.youtubemusic.ui.base.BaseBindingDialogFragment;

import java.util.Objects;

public class PlayBgDialog extends BaseBindingDialogFragment<DialogPlaybackgroundBinding> {

    private int type;
    private SharedPreferenceHelper sharedPreferenceHelper;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_playbackground;
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog()).setCancelable(true);
        getDialog().getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        sharedPreferenceHelper = new SharedPreferenceHelper(requireContext(), Constant.PREF_SETTING_LANGUAGE);
        if (type == Constant.TYPE_HOME) {
            initView(requireContext().getString(R.string.text_PlayBackground)
                    , sharedPreferenceHelper.getBoolean(Constant.ON_PLAY_BACKGROUND, false), Constant.ON_PLAY_BACKGROUND, Constant.OFF_PLAY_BACKGROUND);
        } else if (type == Constant.TYPE_TRENDING) {
            initView(requireContext().getString(R.string.txt_notification), sharedPreferenceHelper.getBoolean(Constant.ON_NOTIFICATION, false),
                    Constant.ON_NOTIFICATION, Constant.OFF_NOTIFICATION);
        }
    }


    private void initView(String text, boolean check, String idOn, String idOff) {
        binding.txtTitle.setText(text);
        if (check)
            binding.rbOn.setChecked(true);
        else {
            binding.rbOff.setChecked(true);
        }
        binding.rbOn.setOnCheckedChangeListener((buttonView, isChecked) -> sharedPreferenceHelper.storeBoolean(idOn, isChecked));
        binding.rbOff.setOnCheckedChangeListener((buttonView, isChecked) -> sharedPreferenceHelper.storeBoolean(idOff, isChecked));

    }

}
