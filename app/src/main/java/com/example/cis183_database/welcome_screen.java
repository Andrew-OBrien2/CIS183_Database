package com.example.cis183_database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcome_screen extends AppCompatActivity
{
    //BOOOOOOOOOOOOOOOOOOOOOOOKMARK HEEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!!!!!!!!
    // Connect the make a post stuff here
    TextView tv_j_ws_userFullName;
    TextView tv_j_ws_numOfPosts;
    DatabaseHelper dbHelper;
    Button btn_tv_j_makePost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);

        tv_j_ws_userFullName = findViewById(R.id.tv_v_ws_userFullName);
        tv_j_ws_numOfPosts = findViewById(R.id.tv_v_ws_numOfPosts);
        btn_tv_j_makePost = findViewById(R.id.btn_v_ws_makePost);

        //make a new instance of the dbHelper.
        dbHelper = new DatabaseHelper(this);

        makePostButtonClickListener();

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

    private void makePostButtonClickListener()
    {
        btn_tv_j_makePost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(welcome_screen.this, MakePost.class));
            }
        });
    }

    private void setRecentPost()
    {
        //BOOOOOOOOOOOOOOOOOOOOOOOKMARK HEEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!!!!!!!!
        String p = dbHelper.getMostRecentPostGivenId(SessionData.getLoggedInUser().getId());
        if(p != null)
        {
            tv_j_recentPosts.setText(p.getPost());
        }
        else
        {
            tv_j_recentPost.setText("You do not have any posts. You should make one!");
        }

    }
}