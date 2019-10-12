package com.example.javasilverapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubActivity extends AppCompatActivity {
    // クイズアプリの基盤
    // 選択肢、問題文、正解方法を取得する
    static int flg = 0;
    static int i = 1;
    static int answerFlg = 0;
    // ボタンに引数を渡して問題に応じて正解のアクションを変更したい
    // TODO：共通化するために匿名クラスに引数を渡せるようにしたい。
    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button b = (Button) v;
            String buttonText = b.getText().toString();

            // 3問連続正解パターンの問題
            if (answerFlg == 0) {
                if (buttonText.equals(StubQuiz.QUIZ_1[0])
                        || buttonText.equals(StubQuiz.QUIZ_1[1])
                        || buttonText.equals(StubQuiz.QUIZ_1[4])) {
                    resultAnswer_3(b);
                }
            } else if (answerFlg == 1) {
                if (buttonText.equals(StubQuiz.QUIZ_2[0])
                        || buttonText.equals(StubQuiz.QUIZ_2[2])
                        || buttonText.equals(StubQuiz.QUIZ_2[3])) {
                    resultAnswer_3(b);
                }
            } else {
                if (buttonText.equals(StubQuiz.QUIZ_3[1])) {
                    Toast.makeText(SubActivity.this, "正解", Toast.LENGTH_LONG).show();
                    answerFlg++;
                    finish();
                    startActivity(getIntent());
                }
            }
        }
    };

    private void resultAnswer_3(Button b) {
        b.setEnabled(false);
        flg++;
        // 正解数が3の場合
        if (flg == 3) {
            Toast.makeText(SubActivity.this, "正解", Toast.LENGTH_LONG).show();
            answerFlg++;
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flg = 0;
        try {
            // クラスの取得
            Class<?> quiz = StubQuiz.class;
            // インスタンスの生成
            Object objQuiz = quiz.newInstance();
            Field f = quiz.getDeclaredField("QUIZ_" + String.valueOf(i));
            f.setAccessible(true);
            String[] getQuiz = (String[]) f.get(objQuiz);

            Class<?> program = StubProgramStr.class;
            Object objProgram = program.newInstance();
            Field fp = program.getDeclaredField("program_" + String.valueOf(i));
            fp.setAccessible(true);
            String getProgram = fp.get(objProgram).toString();

            createProgram(getQuiz, getProgram);
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    設問作成メソッド
     */
    private void createProgram(String[] quiz, String program) {
        List<String> list = Arrays.asList(quiz.clone());
        Collections.shuffle(list);

        // リニアレイアウトの設定
        LinearLayout layout = new LinearLayout(this);
        // orientationは垂直方向
        layout.setOrientation(LinearLayout.VERTICAL);

        // Layoutの横・縦幅の指定　android:layout_width　android:layout_height
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
            // ボタンにIDを設定　android:id
            button.setId(i);

            // Layoutの横・縦幅の指定　android:layout_width　android:layout_height
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            // 均等配置
            buttonLayoutParams.weight = 1;

            button.setLayoutParams(buttonLayoutParams);
            // button.setAllCaps(false)　英語の大文字対策　デフォルトはtrueのため、小文字の文字列が大文字になってしまう。
            button.setAllCaps(false);
            layout.addView(button);
            i++;
            Log.d("ボタンのID", String.valueOf(button.getId()));
            button.setOnClickListener(mButtonListener);
        }
    }
}

