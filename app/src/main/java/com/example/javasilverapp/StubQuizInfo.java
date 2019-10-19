package com.example.javasilverapp;

public class StubQuizInfo {
    static String br = System.getProperty("line.separator");
    // 問題1
    public StubQuiz CreateQuiz1(){
        StubQuiz quiz = new StubQuiz();
        quiz.setProgram("次のうち、クラス宣言に含めることができるもを選びなさい。(3つ選択)");
        quiz.setSelection(new String[]{"メソッド", "フィールド", "インポート宣言", "パッケージ宣言", "コンストラクタ"});
        quiz.setMaxButton(3);
        quiz.setAnswer(new int[]{0,1,4});
        return quiz;
    }

    // 問題2
    public StubQuiz CreateQuiz2(){
        StubQuiz quiz = new StubQuiz();
        quiz.setProgram("パッケージに関する説明として、正しいものを選びなさい(3つ選択)");
        quiz.setSelection(new String[]{"名前空間を提供する", "パッケージ名にはドメイン名を逆にしたものを使用しなければならない", "アクセス制御を提供する", "クラスの分類を可能にする", "パッケージに属さないクラスもある"});
        quiz.setMaxButton(3);
        quiz.setAnswer(new int[]{0,2,3});
        return quiz;
    }

    // 問題3
    public StubQuiz CreateQuiz3(){
        StubQuiz quiz = new StubQuiz();
        quiz.setProgram("以下の中から、パッケージ宣言が正しく記述されているコードを選びなさい(1つ選択)");
        quiz.setSelection(new String[]{"import java.io.*;" + br + "package aaa;" + br + "public class Sample{}",
                "package aaa;" + br + "import java.io.*;" + br + "public class Sample{}",
                "import java.io.*;" + br + "package aaa { " + br + "public class Sample{}" + br + "}",
                "import java.io.*;" + br + "package aaa (" + br + "public class Sample{}" + br + ");"});
        quiz.setMaxButton(1);
        quiz.setAnswer(new int[]{1});
        return quiz;
    }
}
