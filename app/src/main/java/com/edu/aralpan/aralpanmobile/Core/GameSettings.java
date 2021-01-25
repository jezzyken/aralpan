package com.edu.aralpan.aralpanmobile.Core;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class GameSettings {
    private static final String SETTINGS_NAME = "QuizGameSettingsValue";
    private static final String SETTINGS_KEY_SOUND = "SettingSound";

    private SharedPreferences mPref;

    private static GameSettings sInstance;

    public static GameSettings getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GameSettings(context);
        }

        return sInstance;
    }

    private GameSettings(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSoundEnabled() {
        return mPref.getBoolean(SETTINGS_KEY_SOUND, false);
    }

    public void setSoundEnabled(boolean enabled) {
        mPref.edit().putBoolean(SETTINGS_KEY_SOUND, enabled).apply();
    }

}
