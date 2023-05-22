package com.flaxeninfosoft.sarwateAcademy.views.userFragments.mycourse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.flaxeninfosoft.sarwateAcademy.views.UserActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tv, tv2, tv3;
    Button btnRestart, btnpdfdownload;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv = (TextView)findViewById(R.id.tvres);
        btnRestart = (Button) findViewById(R.id.btnRestart);
        btnpdfdownload = (Button) findViewById(R.id.btnpdf);

        StringBuffer sb = new StringBuffer();
        sb.append("Correct answers: " + StartQuizFragment.correct + "\n");

        tv.setText(sb);


        StartQuizFragment.correct=0;

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ResultActivity.this, UserActivity.class);
                startActivity(in);
            }
        });
        btnpdfdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

    }
}