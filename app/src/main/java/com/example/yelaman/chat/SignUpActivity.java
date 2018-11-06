package com.example.yelaman.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, password, age;
    private Button signUp;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String stringName = name.getText().toString().trim();
            String stringEmail = email.getText().toString().trim();
            String stringPassword = password.getText().toString().trim();
            String stringAge = age.getText().toString().trim();

            signUp.setEnabled(!stringEmail.isEmpty() && !stringName.isEmpty() && !stringPassword.isEmpty() && !stringAge.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    private static final String TAG = "TAG" + SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextRegMail);
        password = findViewById(R.id.editTextRegPassword);
        age = findViewById(R.id.editTextAge);
        signUp = findViewById(R.id.buttonRegister);
        signUp.setEnabled(false);

        name.addTextChangedListener(mTextWatcher);
        email.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
        age.addTextChangedListener(mTextWatcher);



        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        final String email = String.valueOf(this.email.getText());
        final String password = String.valueOf(this.password.getText());
        final String age = String.valueOf(this.age.getText());
        final String name = String.valueOf(this.name.getText());

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(
                                    SignUpActivity.this,
                                    "Authentication OK.",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(
                                    SignUpActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =
                database.getReference("users/" + mUser.getUid());
        myRef.updateChildren(new HashMap<String, Object>() {{
                put("age", age);
                put("name", name);
        }}).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpActivity.this, task.isSuccessful() + "", Toast.LENGTH_SHORT).show();
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Toast.makeText(
                        SignUpActivity.this,
                        dataSnapshot.getKey() + ":" +
                                dataSnapshot.getValue(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
