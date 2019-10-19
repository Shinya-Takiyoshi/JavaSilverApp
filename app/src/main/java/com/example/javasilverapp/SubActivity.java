package com.example.javasilverapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SubActivity extends AppCompatActivity {
    // クイズアプリの基盤
    // 選択肢、問題文、正解方法を取得する
    // ボタンクリックした回数
    static int clickCnt = 0;
    // 次の問題への切り替えフラグ
    static int no = 1;
    // 出題情報
    static StubQuiz stub = new StubQuiz();
    // 選択されたボタンを格納する情報
    static List<String> answerList = new ArrayList<>();
    // ボタンに引数を渡して問題に応じて正解のアクションを変更したい
    // TODO：共通化するために匿名クラスに引数を渡せるようにしたい。
    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button b = (Button) v;
            // 選択したボタンは非活性にする
            b.setEnabled(false);

            //実際に選択されている情報の取得
            String buttonText = b.getText().toString();
            answerList.add(buttonText);

            // ボタンを押下した回数
            clickCnt++;

            // TODO：実装検討メモ
            // チェックボックスにしてみる
            // 正解のボタンを設けてみる
            // xmlかたあらかじめ正解の情報を取得しておく

            // 指定された選択肢分、ボタンの数が押下された場合
            if (stub.getMaxButton() == clickCnt) {
                // 問題の検証
                resultAnswer();
            }
        }
    };

    // 問題の検証
    private void resultAnswer() {
        int answerFlg = 0;
        // stub.getSelection()から情報取得
        String[] select = stub.getSelection();
        int[] ans = stub.getAnswer();
        // 回答した答えを検証
        for (String ansTxt : answerList) {
            for (String str : select) {
                if (ansTxt == str) {
                    for (int answerNo : ans) {
                        if (ansTxt == select[answerNo]) {
                            //  回答と正解が一致した場合、正解数を加算する
                            answerFlg++;
                        }
                    }
                }
            }
        }

        if (answerFlg == stub.getMaxButton()) {
            Toast.makeText(SubActivity.this, "正解", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SubActivity.this, "不正解", Toast.LENGTH_LONG).show();
        }
        answerList.clear();
        // 次の問題へ切り替え
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickCnt = 0;
        // TODO:問題情報の取得の方法をどうするか
        try {
            // クラスの取得
            Class<?> quizInfo = StubQuizInfo.class;
            // インスタンスの生成
            Object objQuiz = quizInfo.newInstance();
            // メソッド(setStr)の取得
            Method m = quizInfo.getMethod("CreateQuiz" + String.valueOf(no));
            stub = (StubQuiz) m.invoke(objQuiz);

            createProgram();
            no++;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    設問作成メソッド
     */
    private void createProgram() {
        List<String> list = Arrays.asList(stub.getSelection().clone());
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
        textView.setText(stub.getProgram());

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

