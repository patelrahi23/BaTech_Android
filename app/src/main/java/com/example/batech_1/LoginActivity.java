package com.example.batech_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.batech_1.Admin.AdminLogin;

public class LoginActivity extends AppCompatActivity {

    CardView cv_client, cv_admin;
    TextView tv_client, tv_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cv_client = findViewById(R.id.client);
        cv_admin = findViewById(R.id.admin);
        tv_client = findViewById(R.id.tv_client);
        tv_admin = findViewById(R.id.tv_admin);

        cv_client.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, AdminLogin.class);
            startActivity(intent);

        });

        tv_client.setOnClickListener(view ->{
            Intent intent = new Intent(LoginActivity.this, AdminLogin.class);
            startActivity(intent);
        });

        cv_admin.setOnClickListener(view->{
            cv_client.callOnClick();
        });

        tv_admin.setOnClickListener(view->{
            tv_client.callOnClick();
        });
    }
}