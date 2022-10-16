package com.example.batech_1.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.batech_1.R;

public class AdminSignup3 extends AppCompatActivity {
    Button btn_verify, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup3);

        btn_verify = findViewById(R.id.btn_verify);

        btn_verify.setOnClickListener(view -> {
            Intent intent = new Intent(AdminSignup3.this,OTPVerify.class);
            startActivity(intent);

        });
    }
}