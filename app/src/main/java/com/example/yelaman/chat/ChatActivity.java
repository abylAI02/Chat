package com.example.yelaman.chat;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private FloatingActionButton buttonSend;
    private RecyclerView mRecyclerView;
    private EditText messageText;
    private List<Message> mMessages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        buttonSend =  findViewById(R.id.floatingActionButton);
        mRecyclerView = findViewById(R.id.recyclerView);
        CustomAdapter adapter = new CustomAdapter(mMessages);
        mRecyclerView.setAdapter(adapter);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText = findViewById(R.id.editTextMessageText);
                FirebaseDatabase.getInstance()
                        .getReference().push().setValue(new Message(messageText.getText().toString() , FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                messageText.setText(" ");

            }
        });


    }

}
