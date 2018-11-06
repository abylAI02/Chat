package com.example.yelaman.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    setTitle("Chat");
                    ChatFragment chatFragment = new ChatFragment();
                    android.support.v4.app.FragmentTransaction chatFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    chatFragmentTransaction.replace(R.id.frame,chatFragment , "ChatFragment");
                    chatFragmentTransaction.commit();
                    return true;
                case R.id.navigation_news:
                    setTitle("News");
                    NewsFragment newsFragment = new NewsFragment();
                    android.support.v4.app.FragmentTransaction newsFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    newsFragmentTransaction.replace(R.id.frame,newsFragment , "ChatFragment");
                    newsFragmentTransaction.commit();
                    return true;
                case R.id.navigation_user:
                    setTitle("User");
                    UserFragment userFragment = new UserFragment();
                    android.support.v4.app.FragmentTransaction userFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    userFragmentTransaction.replace(R.id.frame,userFragment , "ChatFragment");
                    userFragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setTitle("Chat");
        ChatFragment chatFragment = new ChatFragment();
        android.support.v4.app.FragmentTransaction chatFragmentTransaction = getSupportFragmentManager().beginTransaction();
        chatFragmentTransaction.replace(R.id.frame, chatFragment,"ChatFragment");
        chatFragmentTransaction.commit();
    }

}
