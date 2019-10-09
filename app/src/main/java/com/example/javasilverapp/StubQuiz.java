package com.example.javasilverapp;

/*
 * Firebaseが基盤になるまでのスタブクラス  選択肢
 * */
public class StubQuiz {
    static String br = System.getProperty("line.separator");
    static final String[] QUIZ_1 = {"メソッド", "フィールド", "インポート宣言", "パッケージ宣言", "コンストラクタ"};

    static final String[] QUIZ_2 = {"名前空間を提供する", "パッケージ名にはドメイン名を逆にしたものを使用しなければならない", "アクセス制御を提供する", "クラスの分類を可能にする", "パッケージに属さないクラスもある"};

    static final String[] QUIZ_3 = {"import java.io.*;" + br + "package aaa;" + br + "public class Sample{}",
            "package aaa;" + br + "import java.io.*;" + br + "public class Sample{}",
            "import java.io.*;" + br + "package aaa { " + br + "public class Sample{}" + br + "}",
            "import java.io.*;" + br + "package aaa (" + br + "public class Sample{}" + br + ");"};

}
