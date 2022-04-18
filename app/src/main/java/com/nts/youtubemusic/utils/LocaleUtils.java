package com.nts.youtubemusic.utils;

import static com.nts.youtubemusic.utils.Utils.isAtLeastSdkVersion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.nts.youtubemusic.R;
import com.nts.youtubemusic.common.Constant;
import com.nts.youtubemusic.common.model.ChangeLanguage;
import com.nts.youtubemusic.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class LocaleUtils {

    public static String codeLanguageCurrent = Constant.NULL;

    public static void applyLocale(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        codeLanguageCurrent = preferences.getString(Constant.PREF_SETTING_LANGUAGE, Constant.NULL);
        if (codeLanguageCurrent.equals(Constant.NULL)) {
            codeLanguageCurrent = Locale.getDefault().getLanguage();
        }
        if (TextUtils.isEmpty(codeLanguageCurrent)) {
            codeLanguageCurrent = Constant.LANGUAGE_EN;
        }

        Locale newLocale = new Locale(codeLanguageCurrent);
        updateResource(context, newLocale);
        if (context != context.getApplicationContext()) {
            updateResource(context.getApplicationContext(), newLocale);
        }

    }

    public static void updateResource(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Locale current = getLocaleCompat(resources);
        if (current == locale) {
            return;
        }
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (isAtLeastSdkVersion(Build.VERSION_CODES.N)) {
            configuration.setLocale(locale);
        } else if (isAtLeastSdkVersion(Build.VERSION_CODES.JELLY_BEAN_MR1)) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static Locale getLocaleCompat(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return isAtLeastSdkVersion(Build.VERSION_CODES.N) ? configuration.getLocales().get(0) : configuration.locale;
    }

    public static void applyLocaleAndRestart(Activity activity, String localeString) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        preferences.edit().putString(Constant.PREF_SETTING_LANGUAGE, localeString).apply();
        LocaleUtils.applyLocale(activity);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        ActivityCompat.finishAffinity(activity);
    }


    public static List<ChangeLanguage> getLanguages(Context context){
        List<ChangeLanguage> list = new ArrayList<>();
        list.add(new ChangeLanguage(Constant.LANGUAGE_EN, context.getResources().getString(R.string.text_EN)));
        list.add(new ChangeLanguage(Constant.LANGUAGE_VN, context.getResources().getString(R.string.text_VN)));
        return list;
    }
}
