package com.example.yelaman.chat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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

        buttonSend = findViewById(R.id.floatingActionButton);
        mRecyclerView = findViewById(R.id.recyclerViewID);
        mMessages = new ArrayList<>();

        final CustomAdapter adapter = new CustomAdapter(mMessages);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase.getInstance().getReference("/messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("****", dataSnapshot.getValue().toString());
                mMessages.add(dataSnapshot.getValue(Message.class));
                adapter.notifyItemInserted(mMessages.size() - 1);
                mRecyclerView.scrollToPosition(mMessages.size() - 1);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText = findViewById(R.id.editTextMessageText);

                Message msg =
                        new Message(
                                messageText.getText().toString(),
                                FirebaseAuth.getInstance().getCurrentUser().getEmail());

                FirebaseDatabase.getInstance()
                        .getReference("/messages")
                        .push()
                        .setValue(msg);

                messageText.setText("");

            }
        });


    }

}
