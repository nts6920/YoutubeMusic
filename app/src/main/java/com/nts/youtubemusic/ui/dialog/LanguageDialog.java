package com.nts.youtubemusic.ui.dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.databinding.DiaglogLanguageBinding;
import com.nts.youtubemusic.ui.base.BaseBindingDialogFragment;
import com.nts.youtubemusic.ui.main.MainActivity;
import com.nts.youtubemusic.utils.LocaleUtils;

import java.util.Objects;

import timber.log.Timber;

public class LanguageDialog extends BaseBindingDialogFragment<DiaglogLanguageBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.diaglog_language;
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        Objects.requireNonNull(getDialog()).setCancelable(true);
        getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        String codeLanguageCurrent = preferences.getString(Constant.PREF_SETTING_LANGUAGE, Constant.LANGUAGE_VN);
        Timber.e("Language %s", codeLanguageCurrent);
        Timber.e("Language %s", LocaleUtils.codeLanguageCurrent);
        if (LocaleUtils.codeLanguageCurrent.equals(Constant.LANGUAGE_VN)) {
            binding.rbVN.setChecked(true);
        } else {
            binding.rbEN.setChecked(true);
        }
        binding.rbVN.setOnClickListener(v -> {
            dismiss();
            LocaleUtils.applyLocaleAndRestart(requireActivity(), Constant.LANGUAGE_VN);
        });
        binding.rbEN.setOnClickListener(v -> {
            dismiss();
            LocaleUtils.applyLocaleAndRestart(requireActivity(), Constant.LANGUAGE_EN);
        });

    }

}
