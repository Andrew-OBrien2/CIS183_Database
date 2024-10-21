package com.example.cis183_database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    EditText et_j_fname;
    EditText et_j_lname;
    EditText et_j_email;
    Button btn_j_register;
    Button btn_j_back;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        et_j_fname = findViewById(R.id.et_v_register_fname);
        et_j_lname = findViewById(R.id.et_v_register_lname);
        et_j_email = findViewById(R.id.et_v_register_email);
        btn_j_register = findViewById(R.id.btn_v_register_register);
        btn_j_back = findViewById(R.id.btn_v_register_back);
        db = new DatabaseHelper(this);

        backButtonOnClickListener();
        registerButtonClickListener();
    }

    private void backButtonOnClickListener()
    {
        btn_j_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });
    }

    private void registerButtonClickListener()
    {
        btn_j_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String fname = et_j_fname.getText().toString();
                String lname = et_j_lname.getText().toString();
                String email = et_j_email.getText().toString();

                if(!fname.isEmpty() && !lname.isEmpty() && !email.isEmpty())
                {
                    User u = new User();
                    u.setFname(fname);
                    u.setLname(lname);
                    u.setEmail(email);

                    db.addUserToDB(u);
                }
            }
        });
    }
}