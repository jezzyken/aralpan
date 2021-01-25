package com.edu.aralpan.aralpanmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Adapter.GridViewAnswerAdapter;
import com.edu.aralpan.aralpanmobile.Adapter.GridViewSuggestAdapter;
import com.edu.aralpan.aralpanmobile.Common.Common;
import com.edu.aralpan.aralpanmobile.Core.SoundManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FindActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    FindModel _findModel = new FindModel();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;
    public TextView textSample;


    public Button btnSubmit;

    public GridView gridViewAnswer,gridViewSuggest;


    public ImageView imgViewQuestion;

    public TextView txtPoints, question, txtQuizNum, txtLevel, timeText, txtCall, txtUser;

    public RatingBar ratingBar, ratingStar;

    public int sample;


    String find_question;

    int timeValue = 20;
    CountDownTimer countDownTimer;
    TextView coin_count;

    public int coin = 10;

    public int score;
    int quid = 0;

    Bundle b;

    String Easy = "Easy";
    String Average = "Average";
    String Difficult = "Difficult";

    public int quiz_num = 1;

    int time_future = 22000;

    int rignt_answer;

    public SoundManager mSoundManager;


    //for easy
    String[] image_list={

            "aumer",
            "akkad",
            "babylon",
            "satrap",
            "chaldean",
            "tsar",
            "sputnik",
            "nasyonalismo",
            "militarismo",
            "alyansa",

    };

    String[] question_list={
            "Mga unang lungsod-estado ng Mesopotamia",
            "Unang imperyong itinatag sa daigdig",
            "Kabisera ng Imperyong Babylonia",
            "Imperyong itinatag ni NabopoLassar",
            "Imperyong itinatag pagkatapos ng Babylonia",
            "Ttawag sa pinuno ng Russia",
            "Kauna-unahang space satellite sa kasaysayan na inilunsad ng USSR",
            "Pagmamalasakit at Pagmamahal ng mga mamamayan sa sariling bansa",
            "Pagpapalakas ng puwersang military.",
            "TPagbubuong grupo o lupon ng mga makapanyarihang bansa sa Europe",

    };


    //for average
    String[] image_listAverage={

            "ffgdfg",
            "dfgj",
            "dfgdhd",
            "jdd",
            "dydyfg",
            "mnmn",
            "cvcvc",
            "lpjjl",
            "vvv",
            "dfsg",

    };

    String[] question_listAverage={
            "jgfj ghd ",
            "dfg ffssf sdfsdf",
            "fsdf sdfsd fsdfsf",
            "fghfgh fgh ",
            "yuyu yu y",
            "ioo sfs",
            "llg rerr",
            "sdfsdf sdfsdfs ",
            "sdfdsf sdfsd ",
            "sfsf sdfsd ",

    };

    //for Difficult
    String[] image_listDifficult={

            "aumer",
            "akkad",
            "babylon",
            "satrap",
            "chaldean",
            "tsar",
            "sputnik",
            "nasyonalismo",
            "militarismo",
            "alyansa",

    };

    String[] question_listDifficult={
            "Mga unang lungsod-estado ng Mesopotamia",
            "Unang imperyong itinatag sa daigdig",
            "Kabisera ng Imperyong Babylonia",
            "Imperyong itinatag ni NabopoLassar",
            "Imperyong itinatag pagkatapos ng Babylonia",
            "Ttawag sa pinuno ng Russia",
            "Kauna-unahang space satellite sa kasaysayan na inilunsad ng USSR",
            "Pagmamalasakit at Pagmamahal ng mga mamamayan sa sariling bansa",
            "Pagpapalakas ng puwersang military.",
            "TPagbubuong grupo o lupon ng mga makapanyarihang bansa sa Europe",

    };




    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        timeText = findViewById(R.id.timeText);
        question = findViewById(R.id.question);
        txtPoints = findViewById(R.id.txtPoint);
        txtLevel = findViewById(R.id.txtLevel);
        txtQuizNum = findViewById(R.id.txtQuizNum);
        coin_count =  findViewById(R.id.coin_count);
        ratingBar = findViewById(R.id.ratingBar);
        txtCall = findViewById(R.id.txtCall);
        txtUser = findViewById(R.id.txtUser);

        ratingStar = findViewById(R.id.ratingBar3);



        mSoundManager = SoundManager.getInstance(this);


        b = getIntent().getExtras();
        txtUser.setText(b.getString("user"));
        initView();

        ratingStar.setRating(3);



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
                    Toast.makeText(FindActivity.this, "Not Enough Coins, Need 6 Coins", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void initView() {
        gridViewAnswer = (GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView)findViewById(R.id.gridViewSuggest);

        //txtScore = (TextView)findViewById(R.id.txtScore);

        question = findViewById(R.id.question);

        //Add SetupList Here


        if (Easy.equals(b.getString("Easy"))){

            Toast.makeText(this, "Easy", Toast.LENGTH_SHORT).show();
            setupList();
        }

        if (Average.equals(b.getString("Average"))){
            Toast.makeText(this, "Average", Toast.LENGTH_SHORT).show();
            setupList();
        }

        if (Difficult.equals(b.getString("Difficult"))){
            Toast.makeText(this, "Difficult", Toast.LENGTH_SHORT).show();
            setupList();
        }




        btnSubmit = (Button)findViewById(R.id.btnMain);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                for(int i = 0; i< Common.user_submit_answer.length; i++)
                    result+=String.valueOf(Common.user_submit_answer[i]);


                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(),"Finish ! This is "+result,Toast.LENGTH_SHORT).show();
                    //score++;
                    //txtScore.setText(String.valueOf(score));
                    //Reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    //Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource,getApplicationContext(),FindActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                   rignt_answer++;

                    setupList();
                    quid++;
                    countDownTimer.cancel();
                    countDownTimer.start();
                    finishGame();
                    ratingStar.setRating(3);

                    score+=10;

                    txtPoints.setText(score + "");

                    quiz_num++;

                    txtQuizNum.setText(quiz_num + "/10");
                    coin++;
                    coin_count.setText(String.valueOf(coin));



                }
                else
                {
                    Toast.makeText(FindActivity.this, "Incorrect!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {



        if(Easy.equals(b.getString("Easy"))){
            txtLevel.setText(Easy);
            timeValue = 180;
            time_future = 180200;

            //Random logo
            Random random = new Random();

            int rand_result = random.nextInt(image_list.length);

            String imageSelected = image_list[rand_result];

            String QuestionSelected = question_list[rand_result];

            question.setText(QuestionSelected);

            correct_answer = imageSelected;

            correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

            answer = correct_answer.toCharArray();

            Common.user_submit_answer = new char[answer.length];

            //Add Answer character to List
            suggestSource.clear();
            for(char item:answer)
            {
                //Add logo name to list
                suggestSource.add(String.valueOf(item));
            }

            //Random add some character to list
            for(int i = answer.length;i<answer.length*2;i++)
                suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

            //Sort random
            Collections.shuffle(suggestSource);

            //Set for GridView
            answerAdapter = new GridViewAnswerAdapter(setupNullList(),this);
            suggestAdapter = new GridViewSuggestAdapter(suggestSource,this,this);

            answerAdapter.notifyDataSetChanged();
            suggestAdapter.notifyDataSetChanged();

            gridViewSuggest.setAdapter(suggestAdapter);
            gridViewAnswer.setAdapter(answerAdapter);

        }

        if(Average.equals(b.getString("Average"))){
            txtLevel.setText(Average);
            timeValue = 120;
            time_future = 120200;

            //Random logo
            Random random = new Random();

            int rand_result = random.nextInt(image_listAverage.length);

            String imageSelected = image_listAverage[rand_result];

            String QuestionSelected = question_listAverage[rand_result];

            question.setText(QuestionSelected);

            correct_answer = imageSelected;

            correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

            answer = correct_answer.toCharArray();

            Common.user_submit_answer = new char[answer.length];

            //Add Answer character to List
            suggestSource.clear();
            for(char item:answer)
            {
                //Add logo name to list
                suggestSource.add(String.valueOf(item));
            }

            //Random add some character to list
            for(int i = answer.length;i<answer.length*2;i++)
                suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

            //Sort random
            Collections.shuffle(suggestSource);

            //Set for GridView
            answerAdapter = new GridViewAnswerAdapter(setupNullList(),this);
            suggestAdapter = new GridViewSuggestAdapter(suggestSource,this,this);

            answerAdapter.notifyDataSetChanged();
            suggestAdapter.notifyDataSetChanged();

            gridViewSuggest.setAdapter(suggestAdapter);
            gridViewAnswer.setAdapter(answerAdapter);
        }

        if(Difficult.equals(b.getString("Difficult"))){
            txtLevel.setText(Difficult);
            timeValue = 60;
            time_future = 60200;

            //Random logo
            Random random = new Random();

            int rand_result = random.nextInt(image_listDifficult.length);

            String imageSelected = image_listDifficult[rand_result];

            String QuestionSelected = question_listDifficult[rand_result];

            question.setText(QuestionSelected);

            correct_answer = imageSelected;

            correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

            answer = correct_answer.toCharArray();

            Common.user_submit_answer = new char[answer.length];

            //Add Answer character to List
            suggestSource.clear();
            for(char item:answer)
            {
                //Add logo name to list
                suggestSource.add(String.valueOf(item));
            }

            //Random add some character to list
            for(int i = answer.length;i<answer.length*2;i++)
                suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

            //Sort random
            Collections.shuffle(suggestSource);

            //Set for GridView
            answerAdapter = new GridViewAnswerAdapter(setupNullList(),this);
            suggestAdapter = new GridViewSuggestAdapter(suggestSource,this,this);

            answerAdapter.notifyDataSetChanged();
            suggestAdapter.notifyDataSetChanged();

            gridViewSuggest.setAdapter(suggestAdapter);
            gridViewAnswer.setAdapter(answerAdapter);
        }


    }

    public void timeUp() {

        Intent intent = new Intent(FindActivity.this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putInt("finalScore",score);
        b.putInt("correctanswer", rignt_answer);
        b.putString("user", txtUser.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        askToClose();
    }


    private void askToClose (){
        AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(FindActivity.this, SelectGameActivity.class);
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

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for(int i=0;i<answer.length;i++)
            result[i]=' ';
        return result;
    }

    public void finishGame(){
        if (quid >= 10){
            Intent intent = new Intent(FindActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("finalScore",score);
            b.putInt("correctanswer", rignt_answer);
            b.putString("user", txtUser.getText().toString());
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
