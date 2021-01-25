package com.edu.aralpan.aralpanmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class UserActivity extends AppCompatActivity {

    Button btnBegin;
    TextView txtName;
    ImageView leftcategory;
    private SoundManager mSoundManager;
    public static MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnBegin = findViewById(R.id.btnBegin);
        txtName = findViewById(R.id.txtName);

        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        leftcategory = findViewById(R.id.leftcategory);
        mSoundManager = SoundManager.getInstance(this);
        leftcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle extras = new Bundle();

                if (txtName.length() == 0){
                    Toast.makeText(UserActivity.this, "You must enter your name to begin", Toast.LENGTH_SHORT).show();
                }else{
                    intent = new Intent(getApplicationContext(), SelectGameActivity.class);
                    extras.putString("Multiple", "Multiple Choice");
                    extras.putString("user", txtName.getText().toString());
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
            }
        });
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

}
