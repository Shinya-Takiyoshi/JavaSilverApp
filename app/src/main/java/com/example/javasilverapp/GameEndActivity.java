package com.example.javasilverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gameclear);

        Intent intent = getIntent();
        int ansCnt = intent.getIntExtra("EXTRA_DATA", 0);
        TextView textView = findViewById(R.id.answerCount);
        textView.setText(String.valueOf(ansCnt) + "問正解");

        Button sendButton = findViewById(R.id.restartButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
