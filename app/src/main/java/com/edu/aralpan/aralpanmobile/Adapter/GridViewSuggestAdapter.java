package com.edu.aralpan.aralpanmobile.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.aralpan.aralpanmobile.Common.Common;
import com.edu.aralpan.aralpanmobile.Core.SoundManager;
import com.edu.aralpan.aralpanmobile.FindActivity;
import com.edu.aralpan.aralpanmobile.FindModel;
import com.edu.aralpan.aralpanmobile.R;

import java.util.List;
import java.util.Random;

public class GridViewSuggestAdapter extends BaseAdapter {

    private List<String> suggestSource;
    private Context context;
    private FindActivity findActivity;

    private SoundManager mSoundManager;




    Boolean iSanswer = true;

    Button button;

    String answer;

    int countScore = 0;

    boolean isTrue;

    public int score = 0;


    char compare;

    public GridViewSuggestAdapter(List<String> suggestSource, Context context, FindActivity findActivity) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.findActivity = findActivity;


    }

    @Override
    public int getCount() {
        return suggestSource.size();
    }

    @Override
    public Object getItem(int position) {
        return suggestSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {




        if(convertView == null)
        {


          ans();

            if(suggestSource.get(position).equals("x"))
            {

                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(70,70));
                button.setPadding(8,8,8,8);
                button.setBackgroundColor(Color.DKGRAY);


                countScore++;


                if(!iSanswer){
                        findActivity.mSoundManager.playSound(SoundManager.SOUND_WRONG);
                    }

                    findActivity.ratingStar.setRating(3 - countScore);

                    if(countScore > 3){
                        findActivity.mSoundManager.playSound(SoundManager.SOUND_HIGHSCORE);
                        findActivity.timeUp();
                    }

            }


            else
            {

                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(70,70));
                button.setPadding(8,8,8,8);
                button.setBackgroundColor(Color.DKGRAY);
                button.setTextColor(Color.WHITE);
                button.setText(suggestSource.get(position));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //If correct answer contains character user selected

                        if(!String.valueOf(findActivity.answer).contains(suggestSource.get(position)))
                        {


                            //Remove from suggest source
                            findActivity.suggestSource.set(position,"x");
                            findActivity.suggestAdapter = new GridViewSuggestAdapter(findActivity.suggestSource,context,findActivity);
                            findActivity.gridViewSuggest.setAdapter(findActivity.suggestAdapter);
                            findActivity.suggestAdapter.notifyDataSetChanged();




                        }
                        else
                        {
                            compare = suggestSource.get(position).charAt(0); // Get char

                            for(int i =0;i<findActivity.answer.length;i++)
                            {
                                if(compare == findActivity.answer[i])
                                    Common.user_submit_answer[i] = compare;



                            }




                            if(iSanswer){

                                iSanswer = true;

                                findActivity.mSoundManager.playSound(SoundManager.SOUND_CORRECT);

                                //Update UI
                                GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer,context);
                                findActivity.gridViewAnswer.setAdapter(answerAdapter);
                                answerAdapter.notifyDataSetChanged();

                                //Remove from suggest source

                                findActivity.suggestSource.set(position,"");
                                findActivity.suggestAdapter = new GridViewSuggestAdapter(findActivity.suggestSource,context,findActivity);
                                findActivity.gridViewSuggest.setAdapter(findActivity.suggestAdapter);
                                findActivity.suggestAdapter.notifyDataSetChanged();
//                                findActivity.textSample.setText(countScore + "");
                                //findActivity.ratingStar.setRating(score - countScore);



                            }else if(!iSanswer){
                                findActivity.mSoundManager.playSound(SoundManager.SOUND_WRONG);

                            }


                        }


                    }
                });
            }
        }
        else
            button = (Button)convertView;
        return button;

    }

    public void ans() {

        Button button;
        Random r = new Random();
        int i;
        int randomAns = 0;


        if(suggestSource.get(randomAns).equals(""))
        {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(70,70));
            button.setPadding(8,8,8,8);
            button.setBackgroundColor(Color.DKGRAY);
        }

        else{

            r.nextInt(findActivity.answer.length);
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(70,70));
            button.setPadding(8,8,8,8);
            button.setBackgroundColor(Color.DKGRAY);
            button.setTextColor(Color.WHITE);
            button.setText(suggestSource.get(randomAns));

            if (String.valueOf(findActivity.answer).contains(suggestSource.get(randomAns))) {
                char compare = suggestSource.get(randomAns).charAt(0); // Get char

                for (i = 0; i < findActivity.answer.length; i++) {
                    if (compare == findActivity.answer[i])
                        Common.user_submit_answer[i] = compare;
                }

                //Update UI
                GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);

                findActivity.gridViewAnswer.setAdapter(answerAdapter);
                answerAdapter.notifyDataSetChanged();

                findActivity.suggestSource.set(randomAns, "");
                findActivity.suggestAdapter = new GridViewSuggestAdapter(findActivity.suggestSource, context, findActivity);
                findActivity.gridViewSuggest.setAdapter(findActivity.suggestAdapter);
                findActivity.suggestAdapter.notifyDataSetChanged();
            }
        }

    }

}
