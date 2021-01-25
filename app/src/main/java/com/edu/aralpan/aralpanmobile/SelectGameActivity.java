package com.edu.aralpan.aralpanmobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class SelectGameActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView btnMultiple, btnTF, btnGuess, btnFind;
    ImageView leftcategory;

    Bundle b;
    private SoundManager mSoundManager;

    public static MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        btnMultiple = findViewById(R.id.btnEasy);
        btnTF = findViewById(R.id.btnTF);
        btnGuess = findViewById(R.id.btnGuess);
        btnFind = findViewById(R.id.btnFind);

        btnMultiple.setOnClickListener(this);
        btnTF.setOnClickListener(this);
        btnGuess.setOnClickListener(this);
        btnFind.setOnClickListener(this);

        b = getIntent().getExtras();

        leftcategory = findViewById(R.id.leftcategory);
        mSoundManager = SoundManager.getInstance(this);
        leftcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b = getIntent().getExtras();

                if (b.getString("user") == null){
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Intent intent = new Intent(SelectGameActivity.this, UserActivity.class);
                    startActivity(intent);
                }else{
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Bundle extras = new Bundle();
                    Intent intent = new Intent(SelectGameActivity.this, MainActivity.class);
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                }

            }
        });

        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle extras = new Bundle();
        switch(v.getId()) {
            case R.id.btnEasy:
                intent = new Intent(getApplicationContext(), SelectDifficultyActivity.class);
                extras.putString("Multiple", "Multiple Choice");
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                break;
            case R.id.btnTF:
                intent = new Intent(getApplicationContext(), SelectDifficultyActivity.class);
                extras.putString("TF", "True or False");
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                break;
            case R.id.btnGuess:
                intent = new Intent(getApplicationContext(), SelectDifficultyActivity.class);
                extras.putString("Guess", "Guess The Image");
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                break;
            case R.id.btnFind:
                intent = new Intent(getApplicationContext(), SelectDifficultyActivity.class);
                extras.putString("Find", "Find the Missing Letter");
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    public void onBackPressed() {
        b = getIntent().getExtras();

        if (b.getString("user") == null){
            mSoundManager.playSound(SoundManager.SOUND_CLICK);
            Intent intent = new Intent(SelectGameActivity.this, UserActivity.class);
            startActivity(intent);
        }else{
            mSoundManager.playSound(SoundManager.SOUND_CLICK);
            Bundle extras = new Bundle();
            Intent intent = new Intent(SelectGameActivity.this, MainActivity.class);
            extras.putString("user", b.getString("user"));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

}
