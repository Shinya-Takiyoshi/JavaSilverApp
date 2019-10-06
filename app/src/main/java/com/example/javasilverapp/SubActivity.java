package com.example.javasilverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamedisplay);

        Button sendButton = findViewById(R.id.startButton2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data1 = "TEST";
                Intent intent = new Intent(getApplication(), SubSecondActivity.class);
                intent.putExtra("EXTRA_DATA", data1);
                startActivity(intent);
            }
        });
        // SQLiteを使用してDBから試験問題のデータを取得する
        // 一問の回答アクションを実装する
        // 回答アクション: [1]戦う　[2]逃げる
        // 回答後のページ再読み込みで試験問題の再描画
        // [1]戦うを選んだ場合、試験問題に紐づいた選択肢データを取得する。取得したデータの表示順ははランダムとする
        // [2]逃げるを選んだ場合、パス成功率(プレーヤーのステータス項目)の計算をする。問題のパスが成功した場合にのみ逃げることができる。
        // [2]逃げるが失敗した場合、相手のターンになり、攻撃される
        // [1][2]どちらの処理が終了した場合に、相手のターンに移行する
        // 制限5ターン内で戦いを終了しない場合、ゲームオーバーとなる
        // 10問連続正解でクリアとなる。
    }

}