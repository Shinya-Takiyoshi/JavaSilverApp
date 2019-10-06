package com.example.javasilverapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubSecondActivity extends AppCompatActivity {
    // クイズアプリの基盤
    // 選択肢、問題文、正解方法を取得する
    String[] QUIZ = {"メソッド", "フィールド", "インポート宣言", "パッケージ宣言"};
    String program = "クラス宣言に含めることができるもの";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        ((TextView) findViewById(R.id.program)).setText(program);
        List<String> list = Arrays.asList(QUIZ.clone());
        Collections.shuffle(list);
        // 問題数に応じてボタンの数を増やす

        Button ansButton = findViewById(R.id.answer01);
        ((Button) findViewById(R.id.answer01)).setText(list.get(0));
        ((Button) findViewById(R.id.answer02)).setText(list.get(1));
        ((Button) findViewById(R.id.answer03)).setText(list.get(2));
        ((Button) findViewById(R.id.answer04)).setText(list.get(3));

        ansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.program)).setText("正解");
            }
        });
    }
}

