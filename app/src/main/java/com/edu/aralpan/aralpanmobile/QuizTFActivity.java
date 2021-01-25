package com.edu.aralpan.aralpanmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;
import com.edu.aralpan.aralpanmobile.model.QuizBook;
import com.edu.aralpan.aralpanmobile.model.QuizGuess;

import java.util.Random;

public class QuizTFActivity extends AppCompatActivity {

    private DbHelper databaseHelper;

    private TextView mQuestion, timeText, txtPoints, txtLevel, txtQuizNum, txtUser, txtCall;
    private ImageView mImageView;
    private Button mTrueButton, mFalseButton;

    QuizBook _tfeasy = new QuizBook();

    TextView coin_count;

    private boolean mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;

    String str;

    int timeValue = 20;
    CountDownTimer countDownTimer;
    int time_future = 22000;
    public int coin = 10;


    Bundle b;

    String Easy = "Easy";
    String Average = "Average";
    String Difficult = "Difficult";

    public int quiz_num = 0;
    public int rignt_answer;

    private SoundManager mSoundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_tf);

        mQuestion = findViewById(R.id.question);
        mTrueButton = findViewById(R.id.btnMain);
        mFalseButton = findViewById(R.id.btnFalse);
        txtLevel = findViewById(R.id.txtLevel);
        timeText = findViewById(R.id.timeText);
        txtQuizNum = findViewById(R.id.txtQuizNum);
        txtUser = findViewById(R.id.txtUser);
        mSoundManager = SoundManager.getInstance(this);

        txtCall = findViewById(R.id.txtCall);

        coin_count  = findViewById(R.id.coin_count);


        txtPoints = findViewById(R.id.txtPoint);


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
                    Toast.makeText(QuizTFActivity.this, "Not Enough Coins, Need atleast 6 Coins", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Logic for true button
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Easy.equals(b.getString("Easy"))){

                    Toast.makeText(QuizTFActivity.this, "Easy", Toast.LENGTH_SHORT).show();

                if(mAnswer == true) {
                    mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                    mScore+=10;
                    rignt_answer++;
                    updateScore(mScore);
                    txtPoints.setText("" + mScore);
                    coin++;
                    coin_count.setText(String.valueOf(coin));

                    //perform check before you update the question
                    if (mQuestionNumber == QuizBook.questions.length) {
                        Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        bundle.putInt("correctanswer", rignt_answer);
                        bundle.putString("user", txtUser.getText().toString());
                        i.putExtras(bundle);
                        QuizTFActivity.this.finish();
                        saveGameScore();
                        startActivity(i);
                        mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                    } else {
                        updateQuestion();
                        countDownTimer.cancel();
                        countDownTimer.start();
                    }
                }
                else {
                    mSoundManager.playSound(SoundManager.SOUND_WRONG);

                    if (mQuestionNumber == QuizBook.questions.length) {
                        Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        bundle.putInt("correctanswer", rignt_answer);
                        bundle.putString("user", txtUser.getText().toString());
                        i.putExtras(bundle);
                        QuizTFActivity.this.finish();
                        saveGameScore();
                        startActivity(i);
                        mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                    } else {
                        updateQuestion();
                        countDownTimer.cancel();
                        countDownTimer.start();
                    }
                }

                }
                if (Average.equals(b.getString("Average"))){
                    Toast.makeText(QuizTFActivity.this, "Average", Toast.LENGTH_SHORT).show();

                    if(mAnswer == true) {
                        mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);
                        coin++;
                        coin_count.setText(String.valueOf(coin));

                        //perform check before you update the question
                        if (mQuestionNumber == QuizBook.questionsAverage.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                    else {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);

                        if (mQuestionNumber == QuizBook.questionsAverage.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                }

                if (Difficult.equals(b.getString("Difficult"))){
                    Toast.makeText(QuizTFActivity.this, "Difficult", Toast.LENGTH_SHORT).show();

                    if(mAnswer == true) {
                        mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);
                        coin++;
                        coin_count.setText(String.valueOf(coin));

                        //perform check before you update the question
                        if (mQuestionNumber == QuizBook.questionsDifficult.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                    else {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);

                        if (mQuestionNumber == QuizBook.questionsDifficult.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                }




            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Easy.equals(b.getString("Easy"))){

                    Toast.makeText(QuizTFActivity.this, "Easy", Toast.LENGTH_SHORT).show();

                    if(mAnswer == false) {
                        mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);
                        coin++;
                        coin_count.setText(String.valueOf(coin));

                        //perform check before you update the question
                        if (mQuestionNumber == QuizBook.questions.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                    else {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);

                        if (mQuestionNumber == QuizBook.questions.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }

                }
                if (Average.equals(b.getString("Average"))){
                    Toast.makeText(QuizTFActivity.this, "Average", Toast.LENGTH_SHORT).show();

                    if(mAnswer == false) {
                        mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);
                        coin++;
                        coin_count.setText(String.valueOf(coin));

                        //perform check before you update the question
                        if (mQuestionNumber == QuizBook.questionsAverage.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                    else {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);

                        if (mQuestionNumber == QuizBook.questionsAverage.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                }

                if (Difficult.equals(b.getString("Difficult"))){
                    Toast.makeText(QuizTFActivity.this, "Difficult", Toast.LENGTH_SHORT).show();

                    if(mAnswer == false) {
                        mSoundManager.playSound(SoundManager.SOUND_CORRECT);
                        mScore+=10;
                        rignt_answer++;
                        updateScore(mScore);
                        txtPoints.setText("" + mScore);
                        coin++;
                        coin_count.setText(String.valueOf(coin));

                        //perform check before you update the question
                        if (mQuestionNumber == QuizBook.questionsDifficult.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
                            updateQuestion();
                            countDownTimer.cancel();
                            countDownTimer.start();
                        }
                    }
                    else {
                        mSoundManager.playSound(SoundManager.SOUND_WRONG);

                        if (mQuestionNumber == QuizBook.questionsDifficult.length) {
                            Intent i = new Intent(QuizTFActivity.this, ResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("finalScore", mScore);
                            bundle.putInt("correctanswer", rignt_answer);
                            bundle.putString("user", txtUser.getText().toString());
                            i.putExtras(bundle);
                            QuizTFActivity.this.finish();
                            saveGameScore();
                            startActivity(i);
                            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

                        } else {
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

        // mImageView.setImageResource(QuizBook.images[mQuestionNumber]);
        //mQuestion.setText(QuizBook.questions[mQuestionNumber]);
        // mAnswer = QuizBook.answers[mQuestionNumber];
        //mQuestionNumber++;


        // countDownTimer.cancel();
        // countDownTimer.start();


        if(Easy.equals(b.getString("Easy"))){
            txtLevel.setText(Easy);
            timeValue = 20;

            Random random = new Random();

            int rand_result = random.nextInt(QuizBook.questions.length);

            mQuestion.setText(QuizBook.questions[rand_result]);
            mAnswer = QuizBook.answers[rand_result];
            mQuestionNumber++;

            quiz_num++;
            txtQuizNum.setText(quiz_num + "/" + QuizBook.questions.length);

        }

        if(Average.equals(b.getString("Average"))){
            txtLevel.setText(Average);
            timeValue = 10;
            time_future = 10200;

            Random random = new Random();

            int rand_result = random.nextInt(QuizBook.questionsAverage.length);

            mQuestion.setText(QuizBook.questionsAverage[rand_result]);
            mAnswer = QuizBook.answersAverage[rand_result];
            mQuestionNumber++;

            quiz_num++;
            txtQuizNum.setText(quiz_num + "/" + QuizBook.questionsAverage.length);
        }

        if(Difficult.equals(b.getString("Difficult"))){
            txtLevel.setText(Difficult);
            timeValue = 5;
            time_future = 5200;

            Random random = new Random();

            int rand_result = random.nextInt(QuizBook.questionsDifficult.length);

            mQuestion.setText(QuizBook.questionsDifficult[rand_result]);
            mAnswer = QuizBook.answersDifficult[rand_result];
            mQuestionNumber++;

            quiz_num++;
            txtQuizNum.setText(quiz_num + "/" + QuizBook.questionsDifficult.length);
        }

    }

    public void timeUp() {
        mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
        Intent intent = new Intent(QuizTFActivity.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("finalScore", mScore);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizTFActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(QuizTFActivity.this, SelectGameActivity.class);
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
