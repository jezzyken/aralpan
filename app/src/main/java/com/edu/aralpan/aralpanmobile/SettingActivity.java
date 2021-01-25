package com.edu.aralpan.aralpanmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.GameSettings;
import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class SettingActivity extends AppCompatActivity {

    GameSettings mSettings;
    MediaPlayer mediaPlayer;
    MediaPlayer player;
    CheckBox check_music;
    private Button sound;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Button btnMain;
    Bundle b;

    private SoundManager mSoundManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sound = (Button) findViewById(R.id.play_sound);
        mSettings = GameSettings.getInstance(this);
        CheckBox cbSound = findViewById(R.id.check_sound);
        cbSound.setChecked(mSettings.isSoundEnabled());

        b = getIntent().getExtras();

        Toast.makeText(this,  b.getString("user") + "", Toast.LENGTH_SHORT).show();

        mSoundManager = SoundManager.getInstance(this);
        btnMain = findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Bundle extras = new Bundle();
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (sharedPreferences.getInt("Sound", 0) == 0) {
            sound.setText("Mute Sound");
            player = MediaPlayer.create(this, R.raw.bgmusic);
            player.start();
            player.setLooping(true);
        } else
            sound.setText("Play Sound");
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound.getText().equals("Play Sound")) {
                    editor.putInt("Sound", 0).commit();
                    sound.setText("Mute Sound");
                    player = MediaPlayer.create(SettingActivity.this, R.raw.bgmusic);
                    player.start();
                    player.setLooping(true);
                } else if (sound.getText().equals("Mute Sound")) {
                    editor.putInt("Sound", 1).commit();
                    sound.setText("Play Sound");
                    player.stop();
                }
            }
        });

    }


    public void onSoundCheckClick(View view) {
        boolean enable = !mSettings.isSoundEnabled();
        mSettings.setSoundEnabled(enable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            player.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            player.start();
    }



}
