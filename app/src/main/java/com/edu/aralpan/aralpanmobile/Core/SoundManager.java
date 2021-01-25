package com.edu.aralpan.aralpanmobile.Core;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

import com.edu.aralpan.aralpanmobile.R;

public class SoundManager {

    public static final int SOUND_CLICK = 0;
    public static final int SOUND_CORRECT = 1;
    public static final int SOUND_WRONG = 2;
    public static final int SOUND_HIGHSCORE = 3;

    private static SoundManager sInstance = null;
    private GameSettings mGameSettings;

    private SoundPool mSoundPool;
    private SparseIntArray mSoundPoolMap;

    public static SoundManager getInstance(Context context) {
        if (sInstance == null) sInstance = new SoundManager(context);
        return sInstance;
    }

    private SoundManager(Context context) {
        mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new SparseIntArray();

        mSoundPoolMap.put(SOUND_CLICK,
                mSoundPool.load(context, R.raw.qclick, 1));
        mSoundPoolMap.put(SOUND_CORRECT,
                mSoundPool.load(context, R.raw.qright, 1));
        mSoundPoolMap.put(SOUND_WRONG,
                mSoundPool.load(context, R.raw.qwrong, 1));
        mSoundPoolMap.put(SOUND_HIGHSCORE,
                mSoundPool.load(context, R.raw.highscore, 1));


        mGameSettings = GameSettings.getInstance(context);
    }

    public void playSound(int sound) {
        if (mGameSettings.isSoundEnabled()) {
            mSoundPool.play(mSoundPoolMap.get(sound), 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }
}
