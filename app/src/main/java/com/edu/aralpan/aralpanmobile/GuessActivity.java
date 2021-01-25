package com.edu.aralpan.aralpanmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;
import com.edu.aralpan.aralpanmobile.model.QuizGuess;

public class GuessActivity extends AppCompatActivity {

    private DbHelper databaseHelper;

    private TextView mQuestion, timeText, txtPoints, txtLevel, txtUser, txtQuizNum, txtCall, coin_count;
    private ImageView mImageView;
    private Button btnSubmit;

    EditText txtAns;
    private SoundManager mSoundManager;





    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;

    String str;


    String getAns;

    int timeValue = 20;
    CountDownTimer countDownTimer;
    int time_future = 22000;
    public int coin = 10;


    Bundle b;

    String Easy = "Easy";
    String Average = "Average";
    String Difficult = "Difficult";

    public int quiz_num;
    public int rignt_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        mQuestion = findViewById(R.id.question);
        btnSubmit = findViewById(R.id.btnMain);
        mImageView = findViewById(R.id.imageView);
        txtAns = findViewById(R.id.txtAns);
        txtUser = findViewById(R.id.txtUser);
        txtCall = findViewById(R.id.txtCall);

        txtLevel = findViewById(R.id.txtLevel);
        timeText = findViewById(R.id.timeText);
        txtPoints = findViewById(R.id.txtPoint);
        txtQuizNum = findViewById(R.id.txtQuizNum);
        coin_count = findViewById(R.id.coin_count);

        mSoundManager = SoundManager.getInstance(this);

        b = getIntent().getExtras();

        txtUser.setText(b.getString("user"));

        databaseHelper = new DbHelper(this);

        updateQuestion();

        countDownTimer = new CountDownTimer(time_future, 1000) {
            public void onTick(long millisUntilFinished) {

                timeText.setText(String.valueOf(timeValue));

                timeValue -= 1;

                if (timeValue == -1) {

                }
            }

            public void onFinish() {
                timeUp();
            }
        }.start();

        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (coin >= 6){
                    coin = coin - 6;
                    coin_count.setText("" + coin);
                    Intent callIntent = new Intent( "android.intent.action.DIAL");
                    startActivity(callIntent);
                }else{
                    Toast.makeText(GuessActivity.this, "Not Enough Coins, Need atleast 6 Coins", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Logic for true button
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String ans = txtAns.getText().toString().trim();

                if (Easy.equals(b.getString("Easy"))){

                    Toast.makeText(GuessActivity.this, "Easy", Toast.LENGTH_SHORT).show();
                    if(!ans.equalsIgnoreCase(mAnswer)) {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);
                        Toast.makeText(GuessActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();

                    }else{
                        coin++;
                        coin_count.setText(String.valueOf(coin));
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);

                        //perform check before you update the question
                        if (mQuestionNumber == QuizGuess.questions.length) {
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
                            Intent i = new Intent(GuessActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            saveGameScore();
                            GuessActivity.this.finish();
                            startActivity(i);
                        } else {
                            mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }

                    }
                }

                if (Average.equals(b.getString("Average"))){

                    Toast.makeText(GuessActivity.this, "Average", Toast.LENGTH_SHORT).show();
                    if(!ans.equalsIgnoreCase(mAnswer)) {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);
                        Toast.makeText(GuessActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();

                    }else{
                        coin++;
                        coin_count.setText(String.valueOf(coin));
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);

                        //perform check before you update the question
                        if (mQuestionNumber == QuizGuess.questionsAverage.length) {
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
                            Intent i = new Intent(GuessActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            saveGameScore();
                            GuessActivity.this.finish();
                            startActivity(i);
                        } else {
                            mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }

                    }
                }

                if (Difficult.equals(b.getString("Difficult"))){

                    Toast.makeText(GuessActivity.this, "Difficult", Toast.LENGTH_SHORT).show();
                    if(!ans.equalsIgnoreCase(mAnswer)) {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);
                        Toast.makeText(GuessActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();

                    }else{
                        coin++;
                        coin_count.setText(String.valueOf(coin));
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);

                        //perform check before you update the question
                        if (mQuestionNumber == QuizGuess.questionsDifficult.length) {
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
                            Intent i = new Intent(GuessActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            saveGameScore();
                            GuessActivity.this.finish();
                            startActivity(i);
                        } else {
                            mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }

                    }
                }

            }


        });
    }

    private void updateQuestion() {




        if(Easy.equals(b.getString("Easy"))){
            txtLevel.setText(Easy);
            timeValue = 20;

            mImageView.setImageResource(QuizGuess.images[mQuestionNumber]);
            mQuestion.setText(QuizGuess.questions[mQuestionNumber]);
            mAnswer = QuizGuess.answers[mQuestionNumber];
            mQuestionNumber++;

            quiz_num++;

            txtQuizNum.setText(quiz_num + "/" + QuizGuess.questions.length);
        }

        if(Average.equals(b.getString("Average"))){
            txtLevel.setText(Average);
            timeValue = 10;
            time_future = 10200;

            mImageView.setImageResource(QuizGuess.imagesAverage[mQuestionNumber]);
            mQuestion.setText(QuizGuess.questionsAverage[mQuestionNumber]);
            mAnswer = QuizGuess.answersAverage[mQuestionNumber];
            mQuestionNumber++;

            quiz_num++;

            txtQuizNum.setText(quiz_num + "/" + QuizGuess.questions.length);
        }

        if(Difficult.equals(b.getString("Difficult"))){
            txtLevel.setText(Difficult);
            timeValue = 5;
            time_future = 5200;

            mImageView.setImageResource(QuizGuess.imagesDifficult[mQuestionNumber]);
            mQuestion.setText(QuizGuess.questionsDifficult[mQuestionNumber]);
            mAnswer = QuizGuess.answersDifficult[mQuestionNumber];
            mQuestionNumber++;

            quiz_num++;

            txtQuizNum.setText(quiz_num + "/" + QuizGuess.questions.length);
        }
    }

    public void timeUp() {
        mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
        Intent intent = new Intent(GuessActivity.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("correctanswer", rignt_answer);
        b.putInt("correctanswer", rignt_answer);
        b.putString("user", txtUser.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        saveGameScore();
        finish();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    private void updateScore(int point) {
//        mScoreView.setText("" + mScore);
    }

    public void clickExit(View view) {
        askToClose();
    }

    @Override
    public void onBackPressed() {
        askToClose();
    }


    private void askToClose (){
        AlertDialog.Builder builder = new AlertDialog.Builder(GuessActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(GuessActivity.this, SelectGameActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveGameScore(){
        databaseHelper.addUserDetail(txtUser.getText().toString(), "mode", txtLevel.getText().toString(), txtPoints.getText().toString());
    }

}
