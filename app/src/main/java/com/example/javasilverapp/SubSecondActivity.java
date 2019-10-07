package com.example.javasilverapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubSecondActivity extends AppCompatActivity {
    // クイズアプリの基盤
    // 選択肢、問題文、正解方法を取得する
    static int flg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (flg == 0) {
            CreateProgram(StubQuiz.QUIZ_1, StubProgramStr.program_1);
        } else {
            CreateProgram(StubQuiz.QUIZ_2, StubProgramStr.program_2);
        }
    }

    /*
    設問作成メソッド
     */
    private void CreateProgram(String[] quiz, String program) {
        List<String> list = Arrays.asList(quiz.clone());
        Collections.shuffle(list);

        // リニアレイアウトの設定
        LinearLayout layout = new LinearLayout(this);
        // orientationは垂直方向
        layout.setOrientation(LinearLayout.VERTICAL);

        // Layoutの横・縦幅の指定
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        setContentView(layout);

        // 問題文設定
        TextView textView = new TextView(this);
        textView.setText(program);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        textLayoutParams.weight = 1;
        textView.setLayoutParams(textLayoutParams);
        layout.addView(textView);
        // 選択肢ボタンの設定
        // 問題数に応じてボタンの数を増減する
        int i = 0;
        while (i < list.size()) {
            Button button = new Button(this);
            button.setText(list.get(i));
            button.setId(i);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.weight = 1;

            button.setLayoutParams(buttonLayoutParams);
            layout.addView(button);
            i++;
            Log.d("ボタンのID", String.valueOf(button.getId()));
            button.setOnClickListener(mButtonListener);
        }
    }

    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button b = (Button) v;
            String buttonText = b.getText().toString();

            // 3問連続正解パターンの問題
            if (buttonText.equals(StubQuiz.QUIZ_1[0])
                    || buttonText.equals(StubQuiz.QUIZ_1[1])
                    || buttonText.equals(StubQuiz.QUIZ_1[4])) {
                b.setEnabled(false);
                flg++;
                // 正解数が3の場合
                if (flg == 3) {
                    Toast.makeText(SubSecondActivity.this, "正解", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        }
    };
}

