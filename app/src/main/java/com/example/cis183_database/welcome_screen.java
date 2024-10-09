package com.example.cis183_database;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcome_screen extends AppCompatActivity
{
    TextView tv_j_ws_userFullName;
    TextView tv_j_ws_numOfPosts;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);

        tv_j_ws_userFullName = findViewById(R.id.tv_v_ws_userFullName);
        tv_j_ws_numOfPosts = findViewById(R.id.tv_v_ws_numOfPosts);

        //make a new instance of the dbHelper.
        dbHelper = new DatabaseHelper(this);

        Intent cameFrom = getIntent();
        Bundle infoPassedToMe = cameFrom.getExtras();
        if (infoPassedToMe != null)
        {
            int userId = infoPassedToMe.getInt("userID");

            int postCount = dbHelper.getNumberOfPosts(userId);
            tv_j_ws_numOfPosts.setText(String.valueOf(postCount));

            tv_j_ws_userFullName.setText(dbHelper.getFNameForUser(userId) + " " + dbHelper.getLNameForUser(userId));
        }
    }
}