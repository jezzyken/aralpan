package com.edu.aralpan.aralpanmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class ResultActivity extends AppCompatActivity {

    private DbHelper databaseHelper;

    String user = "User";
    String mode = "True or False";
    String level = "Easy";

    Button btnLeaderBoard, btnMain;

    private SoundManager mSoundManager;


    Bundle b;

    TextView txtScore, txtEarned;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnLeaderBoard = findViewById(R.id.btnLeaderBoard);
        txtScore = findViewById(R.id.coin_count);
        txtEarned = findViewById(R.id.txtEarned);
        btnMain = findViewById(R.id.btnMain);

        mSoundManager = SoundManager.getInstance(this);

        databaseHelper = new DbHelper(this);

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        b = getIntent().getExtras();

        Toast.makeText(this, b.getString("user") + "", Toast.LENGTH_SHORT).show();

        txtScore.setText("You got " + b.getInt("correctanswer") + " out of 10");
        txtEarned.setText("You earned " + b.getInt("finalScore") + " points");

        ratingBar.setRating(b.getInt("correctanswer"));


        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(ResultActivity.this, LeaderBoardActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
    }
}
