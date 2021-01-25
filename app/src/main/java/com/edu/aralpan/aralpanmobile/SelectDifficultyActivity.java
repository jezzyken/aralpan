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
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class SelectDifficultyActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnEasy, btnAverage, btnDifficult;

    ImageView leftcategory;

    String Multiple = "Multiple Choice";
    String TF = "True or False";
    String Guess = "Guess The Image";
    String Find = "Find the Missing Letter";

    private SoundManager mSoundManager;

    public static MediaPlayer mediaPlayer;



    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);

        btnEasy = findViewById(R.id.btnEasy);
        btnAverage = findViewById(R.id.btnAverage);
        btnDifficult = findViewById(R.id.btnDifficult);

        btnEasy.setOnClickListener(this);
        btnAverage.setOnClickListener(this);
        btnDifficult.setOnClickListener(this);

        b = getIntent().getExtras();
        mSoundManager = SoundManager.getInstance(this);


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
                b = getIntent().getExtras();

                if (b.getString("user") == null){
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Intent intent = new Intent(SelectDifficultyActivity.this, UserActivity.class);
                    startActivity(intent);
                }else{
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                    Bundle extras = new Bundle();
                    Intent intent = new Intent(SelectDifficultyActivity.this, SelectGameActivity.class);
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                }


            }
        });

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        Bundle extras = new Bundle();

        switch(v.getId()) {
            case R.id.btnEasy:
                if (Multiple.equals(b.getString("Multiple"))) {
                    intent = new Intent(getApplicationContext(), QuizActivity.class);
                    extras.putString("Easy", "Easy");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (TF.equals(b.getString("TF"))) {
                    intent = new Intent(getApplicationContext(), QuizTFActivity.class);
                    extras.putString("Easy", "Easy");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (Guess.equals(b.getString("Guess"))) {
                    intent = new Intent(getApplicationContext(), GuessActivity.class);
                    extras.putString("Easy", "Easy");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (Find.equals(b.getString("Find"))) {
                    intent = new Intent(getApplicationContext(), FindActivity.class);
                    extras.putString("Easy", "Easy");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                break;

            case R.id.btnAverage:
                if (Multiple.equals(b.getString("Multiple"))) {
                    intent = new Intent(getApplicationContext(), QuizActivity.class);
                    extras.putString("Average", "Average");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (TF.equals(b.getString("TF"))) {
                    intent = new Intent(getApplicationContext(), QuizTFActivity.class);
                    extras.putString("Average", "Average");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (Guess.equals(b.getString("Guess"))) {
                    intent = new Intent(getApplicationContext(), GuessActivity.class);
                    extras.putString("Average", "Average");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (Find.equals(b.getString("Find"))) {
                    intent = new Intent(getApplicationContext(), FindActivity.class);
                    extras.putString("Average", "Average");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                break;
            case R.id.btnDifficult:
                if (Multiple.equals(b.getString("Multiple"))) {
                    intent = new Intent(getApplicationContext(), QuizActivity.class);
                    extras.putString("Difficult", "Difficult");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (TF.equals(b.getString("TF"))) {
                    intent = new Intent(getApplicationContext(), QuizTFActivity.class);
                    extras.putString("Difficult", "Difficult");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (Guess.equals(b.getString("Guess"))) {
                    intent = new Intent(getApplicationContext(), GuessActivity.class);
                    extras.putString("Difficult", "Difficult");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
                if (Find.equals(b.getString("Find"))) {
                    intent = new Intent(getApplicationContext(), FindActivity.class);
                    extras.putString("Difficult", "Difficult");
                    extras.putString("user", b.getString("user"));
                    intent.putExtras(extras);
                    startActivity(intent);
                    mSoundManager.playSound(SoundManager.SOUND_CLICK);
                }
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
            Intent intent = new Intent(SelectDifficultyActivity.this, UserActivity.class);
            startActivity(intent);
        }else{
            mSoundManager.playSound(SoundManager.SOUND_CLICK);
            Bundle extras = new Bundle();
            Intent intent = new Intent(SelectDifficultyActivity.this, SelectGameActivity.class);
            extras.putString("user", b.getString("user"));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

}
