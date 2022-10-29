package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.batech_1.R;

public class Login extends AppCompatActivity {

    Button btn_login, btn_create_acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btn_create_acc = findViewById(R.id.btn_create_acc);

        btn_create_acc.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Signup1.class);
            startActivity(intent);
            finish();
        });

    }
}