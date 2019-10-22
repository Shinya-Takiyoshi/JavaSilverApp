package com.example.javasilverapp;

public class StubQuizInfo {
    static String br = System.getProperty("line.separator");

    // 問題1
    public StubQuiz createQuiz1() {
        StubQuiz quiz = new StubQuiz();
        quiz.setQuestion("次のうち、クラス宣言に含めることができるもを選びなさい。(3つ選択)");
        quiz.setSelection(new String[]{"メソッド", "フィールド", "インポート宣言", "パッケージ宣言", "コンストラクタ"});
        quiz.setMaxButton(3);
        quiz.setAnswer(new int[]{0, 1, 4});
        return quiz;
    }

    // 問題2
    public StubQuiz createQuiz2() {
        StubQuiz quiz = new StubQuiz();
        quiz.setQuestion("パッケージに関する説明として、正しいものを選びなさい(3つ選択)");
        quiz.setSelection(new String[]{"名前空間を提供する", "パッケージ名にはドメイン名を逆にしたものを使用しなければならない", "アクセス制御を提供する", "クラスの分類を可能にする", "パッケージに属さないクラスもある"});
        quiz.setMaxButton(3);
        quiz.setAnswer(new int[]{0, 2, 3});
        return quiz;
    }

    // 問題3
    public StubQuiz createQuiz3() {
        StubQuiz quiz = new StubQuiz();
        quiz.setQuestion("以下の中から、パッケージ宣言が正しく記述されているコードを選びなさい(1つ選択)");
        quiz.setSelection(new String[]{"import java.io.*;" + br + "package aaa;" + br + "public class Sample{}",
                "package aaa;" + br + "import java.io.*;" + br + "public class Sample{}",
                "import java.io.*;" + br + "package aaa { " + br + "public class Sample{}" + br + "}",
                "import java.io.*;" + br + "package aaa (" + br + "public class Sample{}" + br + ");"});
        quiz.setMaxButton(1);
        quiz.setAnswer(new int[]{1});
        return quiz;
    }

    // 問題4
    public StubQuiz createQuiz4() {
        StubQuiz quiz = new StubQuiz();
        quiz.setQuestion("次のうち、インポート宣言をしなくても、自動的にインポートされるものはどれか。正しいものを選びなさい(2つ選択)");
        quiz.setSelection(new String[]{"java.langパッケージに属するクラス",
                "java.langパッケージのうち、StringクラスとSystemクラスの2つだけ",
                "同じパッケージに属するクラス",
                "サブパッケージに属するクラス"
        });
        quiz.setMaxButton(2);
        quiz.setAnswer(new int[]{0, 2});
        return quiz;
    }

    // 問題5
    // 画像ありの問題
    public StubQuiz createQuiz5() {
        StubQuiz quiz = new StubQuiz();
        quiz.setQuestion("次のプログラムをコンパイル、実行したときの結果として、正しいものを選びなさい(1つ選択)");
        quiz.setQuestionImage(R.drawable.question05);
        quiz.setSelection(new String[]{"0が表示される",
                "10が表示される",
                "コンパイルエラーが発生する",
                "実行時に例外がスローされる"
        });
        quiz.setMaxButton(1);
        quiz.setAnswer(new int[]{2});
        return quiz;
    }
}
