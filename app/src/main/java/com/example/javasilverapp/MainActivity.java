package com.example.javasilverapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView mStatusTextView;
    TextView mDetailTextView;
    EditText mEmailField;
    EditText mPasswordField;

    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpassword);

        /*Button sendButton = findViewById(R.id.startButton);
        Button fireBase = findViewById(R.id.fireBase);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SubActivity.class);
                startActivity(intent);
            }
        });

        // fireBaseの導通テスト用コード
        fireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fbase = FirebaseApp.initializeApp(MainActivity.this);
                //String msg = "FireBase:" + fbase.getName();
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(), LoginCheckActivity.class);
                startActivity(intent);
            }
        });*/

        //ImageView titleView = findViewById(R.id.titleView);
        //titleView.setImageResource(R.drawable.javasilver);
        try {

            // Authentication -> setApplicationId,setApiKey
            // Cloud Storage -> setStorageBucket
            // Cloud Firestore -> setDatabaseUrl
            FirebaseOptions options = new FirebaseOptions.Builder().
                    setApplicationId(getResources().getString(R.string.google_app_id)).
                    setApiKey(getResources().getString(R.string.google_api_key)).
                    setStorageBucket(getResources().getString(R.string.google_storage_bucket)).
                    setDatabaseUrl(getResources().getString(R.string.firebase_database_url)).build();

            FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), options, "JavaSilverApp");

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance(myApp);

            final Button createAccount = findViewById(R.id.emailCreateAccountButton);
            final Button signIn = findViewById(R.id.emailSignInButton);
            mEmailField = findViewById(R.id.fieldEmail);
            mPasswordField = findViewById(R.id.fieldPassword);

            // Authentication
            createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                }
            });

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        //createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
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

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (mStatusTextView != null) {
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser user) {
        // TODO:一度ログイン失敗した状態のログインは成功する。初期ログインは画面情報がないため、成功しない。
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());

            // アプリのスタート画面
            setContentView(R.layout.activity_main);
            ImageView titleView = findViewById(R.id.titleView);
            titleView.setImageResource(R.drawable.javasilver);
            Button sendButton = findViewById(R.id.startButton);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), SubActivity.class);
                    startActivity(intent);
                }
            });

            // DB取得練習　Cloud Firestore
            Button testButton = findViewById(R.id.testButton);
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseFirestore.getInstance().collection("quizSelection")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                        }
                                    } else {
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }
            });

            // ファイル取得練習　Cloud storage
            Button testStorage = findViewById(R.id.testButton_Storage);


            testStorage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                    final StorageReference fileRef = mStorageRef.child("test画像.png");
                    try{
                        File localFile = File.createTempFile("images", "jpg");
                        fileRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        // Successfully downloaded data to local file
                                        // ...
                                        Log.d(TAG, "ダウンロード成功");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle failed download
                                // ...
                                Log.d(TAG, "ダウンロード失敗");
                            }
                        });
                    }catch(Exception e){
                        Log.d(TAG, "ダウンロード例外");
                    }


                }
            });
        } else {
            mStatusTextView = findViewById(R.id.titleText);
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView = findViewById(R.id.fieldEmail);
            mDetailTextView.setText("test");

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
        }
    }

}
