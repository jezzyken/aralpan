package com.edu.aralpan.aralpanmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;
import com.edu.aralpan.aralpanmobile.model.UserModel;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<UserModel> userModelArrayList;
    private CustomAdapter customAdapter;
    private DbHelper databaseHelper;

    Button btnMain;
    Bundle b;

    private SoundManager mSoundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        databaseHelper = new DbHelper(this);
        listView = (ListView) findViewById(R.id.lv);

        userModelArrayList = databaseHelper.getAllUsers();

        customAdapter = new CustomAdapter(this,userModelArrayList);
        listView.setAdapter(customAdapter);

        b = getIntent().getExtras();

        btnMain = findViewById(R.id.btnMain);
        mSoundManager = SoundManager.getInstance(this);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(LeaderBoardActivity.this, MainActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(GetAllUsersActivity.this,UpdateDeleteActivity.class);
                //intent.putExtra("user",userModelArrayList.get(position));
                // startActivity(intent);
            }
        });

    }
}
