package com.example.batech_1.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.batech_1.R;

public class AdminSignup1 extends AppCompatActivity {

    Button btn_next, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup1);

        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(view -> {
            Intent intent = new Intent(AdminSignup1.this,AdminSignup2.class);
            startActivity(intent);

        });
    }
}