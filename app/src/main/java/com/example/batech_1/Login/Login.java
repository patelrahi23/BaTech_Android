package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.batech_1.R;

public class Login extends AppCompatActivity {

    Button btn_login, btn_create_acc, btn_fgt_pass;
    CheckBox cb_remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btn_create_acc = findViewById(R.id.btn_create_acc);
        btn_login = findViewById(R.id.btn_login);
        btn_fgt_pass = findViewById(R.id.btn_fgt_pass);
        cb_remember_me = findViewById(R.id.cb_remember_me);

        cb_remember_me.setOnClickListener(view->{
            // procedure for shared prefrences to store email and password of the user


        });


        btn_fgt_pass.setOnClickListener(view->{
            // forgot passowrd activity
        });

        btn_login.setOnClickListener(view->{
            //login the user
        });

        btn_create_acc.setOnClickListener(view -> {

            // procedure of creating account for user
            Intent intent = new Intent(Login.this, Signup1.class);
            startActivity(intent);
            finish();
        });

    }
}