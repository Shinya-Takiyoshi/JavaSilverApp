package com.example.javasilverapp;

import android.content.Intent;
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
    //　正解数
    static int answerCnt = 0;

    // ボタン押下時の処理
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
        // 選択肢情報の取得
        String[] select = stub.getSelection();
        // 正解情報の取得
        int[] ans = stub.getAnswer();
        // 回答の検証
        for (String ansTxt : answerList) {
            for (int answerNo : ans) {
                if (ansTxt == select[answerNo]) {
                    //  回答と正解が一致した場合、正解数を加算する
                    answerFlg++;
                }
            }
        }

        // 正解判定を行う
        if (answerFlg == stub.getMaxButton()) {
            Toast.makeText(SubActivity.this, "正解", Toast.LENGTH_LONG).show();
            answerCnt++;
        } else {
            Toast.makeText(SubActivity.this, "不正解", Toast.LENGTH_LONG).show();
        }
        // 一時回答リストの初期化
        answerList.clear();
        // 次の問題へ切り替え
        no++;
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ボタンクリック数の初期化
        clickCnt = 0;

        try {
            // クラスの取得
            Class<?> quizInfo = StubQuizInfo.class;
            // インスタンスの生成
            Object objQuiz = quizInfo.newInstance();
            // スタブ情報の取得
            Method m = quizInfo.getMethod("createQuiz" + String.valueOf(no));
            stub = (StubQuiz) m.invoke(objQuiz);
            // 画面情報の作成
            createLayout();
        } catch (Exception e) {
            e.printStackTrace();
            no = 1;
            int resCnt = answerCnt;
            answerCnt = 0;
            // ゲーム終了画面遷移
            Intent intent = new Intent(getApplication(), GameEndActivity.class);
            intent.putExtra("ANSWER_CNT", resCnt);
            startActivity(intent);
        }

    }

    /*
    設問作成メソッド
     */
    private void createLayout() {
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

