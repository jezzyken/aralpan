package com.edu.aralpan.aralpanmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.edu.aralpan.aralpanmobile.Core.SoundManager;

public class InstructionActivity extends AppCompatActivity {


    private SoundManager mSoundManager;
    Bundle b;
    Button btnMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        btnMain = findViewById(R.id.btnMain);

        mSoundManager = SoundManager.getInstance(this);

        b = getIntent().getExtras();
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mSoundManager.playSound(SoundManager.SOUND_CLICK);
                Bundle extras = new Bundle();
                Intent intent = new Intent(InstructionActivity.this, MainActivity.class);
                extras.putString("user", b.getString("user"));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
}
