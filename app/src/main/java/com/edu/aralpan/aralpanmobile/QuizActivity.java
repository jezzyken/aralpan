package com.edu.aralpan.aralpanmobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;
import com.edu.aralpan.aralpanmobile.model.Question;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;


    private DbHelper databaseHelper;

    private SoundManager mSoundManager;



    private TextView timeText, txtLevel;

    int timeValue = 20;
    CountDownTimer countDownTimer;

    List<Question> questionList;
    int score = 0;
    int quid = 0;
    Question currentQuestion;

    TextView txtQuestion, txtPoints, fifty_fifty, txtQuizNum, txtUser, txtCall;
    Button butNext, btnA, btnB, btnC, btnD;

    //fifty_fifty
    public int coin = 10;
    private int btnposition = 0;
    TextView coin_count;

    Bundle b;

    String Easy = "Easy";
    String Average = "Average";
    String Difficult = "Difficult";

    public int quiz_num = 1;

    int time_future = 22000;

    int rignt_answer;

    String _level = "";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnA = findViewById(R.id.btnMain);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);


        timeText = findViewById(R.id.timeText);
        txtQuestion = findViewById(R.id.question);
        coin_count = findViewById(R.id.coin_count);
        txtPoints = findViewById(R.id.txtPoint);
        txtLevel = findViewById(R.id.txtLevel);
        txtQuizNum = findViewById(R.id.txtQuizNum);
        txtUser = findViewById(R.id.txtUser);
        txtCall = findViewById(R.id.txtCall);

        b = getIntent().getExtras();

        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);//reference to shared preference file
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);


        if (Easy.equals(b.getString("Easy"))){

            DbHelper dbHelper = new DbHelper(this);
            questionList = dbHelper.getAllQuestions();
            Collections.shuffle(questionList);
            currentQuestion = questionList.get(quid);
            databaseHelper = new DbHelper(this);

        }
        if (Average.equals(b.getString("Average"))){

            DbHelper dbHelper = new DbHelper(this);
            questionList = dbHelper.getAllQuestionsAverage();
            Collections.shuffle(questionList);
            currentQuestion = questionList.get(quid);
            databaseHelper = new DbHelper(this);

        }

        if (Difficult.equals(b.getString("Difficult"))){

            DbHelper dbHelper = new DbHelper(this);
            questionList = dbHelper.getAllDifficult();
            Collections.shuffle(questionList);
            currentQuestion = questionList.get(quid);
            databaseHelper = new DbHelper(this);

        }


        mSoundManager = SoundManager.getInstance(this);

        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }




        txtUser.setText(b.getString("user"));




        setQuestionView();

        resetAllValue();


        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (coin >= 6){
                    coin = coin - 6;
                    coin_count.setText("" + coin);
                    Intent callIntent = new Intent( "android.intent.action.DIAL");
                    startActivity(callIntent);
                }else{
                    Toast.makeText(QuizActivity.this, "Not Enough Coins, Need 6 Coins", Toast.LENGTH_SHORT).show();
                }



            }
        });






        countDownTimer = new CountDownTimer(time_future, 1000) {
            public void onTick(long millisUntilFinished) {

                timeText.setText(String.valueOf(timeValue));

                timeValue -= 1;

                if (timeValue == -1) {
                    timeUp();
                }
            }

            public void onFinish() {
                timeUp();
            }
        }.start();



    }

    private void setQuestionView(){
        txtQuestion.setText(currentQuestion.getQuestion());
        btnA.setText(currentQuestion.getOptA());
        btnB.setText(currentQuestion.getOptB());
        btnC.setText(currentQuestion.getOptC());
        btnD.setText(currentQuestion.getOptD());
        quid++;
        quiz_num++;


        if(Easy.equals(b.getString("Easy"))){
            txtLevel.setText(Easy);
            timeValue = 20;

            _level = b.getString("Easy");


        }

        if(Average.equals(b.getString("Average"))){
            txtLevel.setText(Average);
            timeValue = 10;
            time_future = 10200;

            _level = b.getString("Average");
        }

        if(Difficult.equals(b.getString("Difficult"))){
            txtLevel.setText(Difficult);
            timeValue = 5;
            time_future = 5200;

            _level = b.getString("Difficult");
        }


       // timeValue = 20;

        setEnable();



    }



    public void btnAclick(View view){

        if(currentQuestion.getAnswer().equals(btnA.getText())){

            score += 10;
            coin++;
            rignt_answer++;
            Log.d("finalScore", "Your score: "+score);
            txtPoints.setText("" + score);
            coin_count.setText(String.valueOf(coin));
            mSoundManager.playSound(SoundManager.SOUND_CORRECT);

        }else{

            mSoundManager.playSound(SoundManager.SOUND_WRONG);
        }

        if(quid<4){

            txtQuizNum.setText(quiz_num + "/10");
            currentQuestion = questionList.get(quid);
            setQuestionView();
            countDownTimer.cancel();
            countDownTimer.start();

        }else{
            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("finalScore",score);
            b.putInt("correctanswer", rignt_answer);
            b.putString("user", txtUser.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
            saveGameScore();
            finish();
        }

    }

    public void btnBclick(View view){

        if(currentQuestion.getAnswer().equals(btnB.getText())){
            score += 10;
            coin++;
            rignt_answer++;
            Log.d("finalScore", "Your score: "+score);
            txtPoints.setText("" + score);
            coin_count.setText(String.valueOf(coin));

            mSoundManager.playSound(SoundManager.SOUND_CORRECT);


        }else{

            mSoundManager.playSound(SoundManager.SOUND_WRONG);
        }

        if(quid<4){
            txtQuizNum.setText(quiz_num + "/10");
            currentQuestion = questionList.get(quid);
            setQuestionView();
            countDownTimer.cancel();
            countDownTimer.start();


        }else{
            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("finalScore",score);
            b.putInt("correctanswer", rignt_answer);
            b.putString("user", txtUser.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
            saveGameScore();
            finish();
        }

    }

    public void btnCclick(View view){

        if(currentQuestion.getAnswer().equals(btnC.getText())){
            score += 10;
            coin++;
            rignt_answer++;
            Log.d("finalScore", "Your score: "+score);
            coin_count.setText(String.valueOf(coin));


            mSoundManager.playSound(SoundManager.SOUND_CORRECT);

        }else{

            mSoundManager.playSound(SoundManager.SOUND_WRONG);
        }


        if(quid<4){
            txtQuizNum.setText(quiz_num + "/10");
            currentQuestion = questionList.get(quid);
            setQuestionView();
            countDownTimer.cancel(); coin_count.setText(String.valueOf(coin));
            countDownTimer.start();

        }else{

            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("finalScore",score);
            b.putInt("correctanswer", rignt_answer);
            b.putString("user", txtUser.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
            saveGameScore();
            finish();
        }

    }

    public void btnDclick(View view){

        if(currentQuestion.getAnswer().equals(btnD.getText())){
            score += 10;
            coin++;
            rignt_answer++;
            Log.d("finalScore", "Your score: "+score);
            txtPoints.setText("" + score);
            coin_count.setText(String.valueOf(coin));

            mSoundManager.playSound(SoundManager.SOUND_CORRECT);


        }else{

            mSoundManager.playSound(SoundManager.SOUND_WRONG);
        }

        if(quid<4){
            txtQuizNum.setText(quiz_num + "/10");
            currentQuestion = questionList.get(quid);
            setQuestionView();
            countDownTimer.cancel();
            countDownTimer.start();

        }else{
            mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("finalScore",score);
            b.putInt("correctanswer", rignt_answer);
            b.putString("user", txtUser.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
            saveGameScore();
            finish();
        }

    }

    private void resetAllValue(){


        fifty_fifty = findViewById(R.id.fifty_fifty);
        coin_count =  findViewById(R.id.coin_count);

        coin_count.setText(String.valueOf(coin));

        fifty_fifty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (coin >= 4) {

                    btnposition = 0;
                    //fifty_fifty.setImageResource(R.drawable.dec_fifty);
                    // TODO Auto-generated method stub
                    coin = coin - 4;
                    coin_count.setText("" + coin);
                    if (btnA.getText().toString().trim().equalsIgnoreCase(currentQuestion.getAnswer().trim())) {
                        btnposition = 1;
                    }

                    if (btnB.getText().toString().trim().equalsIgnoreCase(currentQuestion.getAnswer().trim())) {
                        btnposition = 2;
                    }

                    if (btnC.getText().toString().trim().equalsIgnoreCase(currentQuestion.getAnswer().trim())) {
                        btnposition = 3;
                    }

                    if (btnD.getText().toString().trim().equalsIgnoreCase(currentQuestion.getAnswer().trim())) {
                        btnposition = 4;
                    }


                    if (btnposition == 1) {
                        btnB.setEnabled(false);
                        btnC.setEnabled(false);

                        btnB.setBackground(getResources().getDrawable(R.drawable.btn_error));
                        btnC.setBackground(getResources().getDrawable(R.drawable.btn_error));

                    } else if (btnposition == 2) {
                        btnA.setEnabled(false);
                        btnD.setEnabled(false);

                        btnA.setBackground(getResources().getDrawable(R.drawable.btn_error));
                        btnD.setBackground(getResources().getDrawable(R.drawable.btn_error));

                    } else if (btnposition == 3) {
                        btnD.setEnabled(false);
                        btnB.setEnabled(false);

                        btnD.setBackground(getResources().getDrawable(R.drawable.btn_error));
                        btnB.setBackground(getResources().getDrawable(R.drawable.btn_error));
                    }

                    else if (btnposition == 4) {
                        btnA.setEnabled(false);
                        btnC.setEnabled(false);


                        btnA.setBackground(getResources().getDrawable(R.drawable.btn_error));
                        btnC.setBackground(getResources().getDrawable(R.drawable.btn_error));

                    }

                } else {
                    Toast.makeText(QuizActivity.this, "Not Enough Coin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setEnable();

    }

    public void setEnable(){

        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);

        btnA.setBackground(getResources().getDrawable(R.drawable.btn_oval));
        btnB.setBackground(getResources().getDrawable(R.drawable.btn_oval));
        btnC.setBackground(getResources().getDrawable(R.drawable.btn_oval));
        btnD.setBackground(getResources().getDrawable(R.drawable.btn_oval));


    }


    public void timeUp() {
        mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("finalScore",score);
        b.putInt("correctanswer", rignt_answer);
        b.putString("user", txtUser.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        saveGameScore();
        finish();


    }

    public void saveGameScore(){
        databaseHelper.addUserDetail(txtUser.getText().toString(), "mode", txtLevel.getText().toString(), txtPoints.getText().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.start();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
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
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    public void onBackPressed() {
        askToClose();
    }


    private void askToClose (){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(QuizActivity.this, SelectGameActivity.class);
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


}
