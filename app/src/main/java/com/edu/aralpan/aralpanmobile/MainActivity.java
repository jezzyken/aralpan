package com.edu.aralpan.aralpanmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class MainActivity extends AppCompatActivity {

    ImageView  btnSetting, btnLeaderBoard, insturction, btnExit;
    private SoundManager mSoundManager;
    Button btnPlay;

    public static MediaPlayer player;
    public static MediaPlayer mediaPlayer;

    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new MediaPlayer();

        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        mSoundManager = SoundManager.getInstance(this);

        gameSount();

        btnPlay = findViewById(R.id.btnPlay);
        btnSetting = findViewById(R.id.btnSetting);
        btnLeaderBoard = findViewById(R.id.btnLeaderBoard);
        insturction = findViewById(R.id.insturction);
        btnExit = findViewById(R.id.btnExit);

        b = getIntent().getExtras();


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (b.getString("user") == null){
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                }else{
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Bundle extras = new Bundle();
                    Intent intent = new Intent(MainActivity.this, SelectGameActivity.class);
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                }


//                Intent intent = new Intent(MainActivity.this, UserActivity.class);
//                mSoundManager.playSound(SoundManager.SOUND_CLICK);
//                startActivity(intent);

            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        insturction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);

            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);

               finish();

            }
        });


    }

    public void gameSount(){
        mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }







}
