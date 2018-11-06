package com.example.yelaman.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signIn;
    private TextView signUp;
    private FirebaseAuth mAuth;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String stringEmail = email.getText().toString().trim();
            String stringPassword = password.getText().toString().trim();
            signIn.setEnabled(!stringEmail.isEmpty() && !stringPassword.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private static final String TAG = "TAG" + LogInActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.editTextMail);
        password = findViewById(R.id.editTextPassword);
        signIn = findViewById(R.id.buttonSignIn);
        signUp = findViewById(R.id.textViewSignUp);
        mAuth = FirebaseAuth.getInstance();
        signIn.setEnabled(false);
        password.addTextChangedListener(mTextWatcher);
        email.addTextChangedListener(mTextWatcher);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                signUp.setTextColor(R.color.colorBlack);
                changeActivity();
            }
        });
    }

    private void changeActivity() {
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    private void login() {
        String email = String.valueOf(this.email.getText());
        String password = String.valueOf(this.password.getText());

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(LogInActivity.this, ChatActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(
                                    LogInActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        /*Intent intent = new Intent(this, .class);
        // Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
        startActivity(intent);*/

    }
}
