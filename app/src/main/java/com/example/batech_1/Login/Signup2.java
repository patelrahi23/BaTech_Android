package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.batech_1.R;

public class Signup2 extends AppCompatActivity {
    Button btn_next, btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup2);

        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(view -> {
            Intent intent = new Intent(Signup2.this, Signup3.class);
            startActivity(intent);

        });
    }
}