package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.batech_1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Objects;

public class Signup1 extends AppCompatActivity {

    String fname= null, email = null, pass = null, confirm_pass = null;

    TextInputLayout et_fname, et_uname,et_pass, et_con_pass;

    boolean valid = true;

    Button btn_next, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup1);

        et_fname = findViewById(R.id.et_fname);
        et_uname = findViewById(R.id.et_uname);
        et_pass = findViewById(R.id.et_pass);
        et_con_pass = findViewById(R.id.et_con_pass);
        btn_next = findViewById(R.id.btn_next);
        btn_login = findViewById(R.id.signup_login_btn);



        btn_login.setOnClickListener(view->{

            Intent intent = new Intent(Signup1.this,Login.class);
            startActivity(intent);
            finish();
        });

        btn_next.setOnClickListener(view -> {

            fname = Objects.requireNonNull(et_fname.getEditText()).getText().toString();
            email = Objects.requireNonNull(et_uname.getEditText()).getText().toString();
            pass = Objects.requireNonNull(et_pass.getEditText()).getText().toString();
            confirm_pass = Objects.requireNonNull(et_con_pass.getEditText()).getText().toString();

            Toast.makeText(getApplicationContext(), fname, Toast.LENGTH_SHORT).show();

            if(CheckFields(et_fname) && CheckFields(et_con_pass) && CheckFields(et_pass) && CheckFields(et_uname)){
                if(pass.equals(confirm_pass)){
                    Intent intent = new Intent(Signup1.this, Signup2.class);
                    startActivity(intent);
                }else{
                    et_pass.setError("Password Does Not Match");
                    et_con_pass.setError("Password Does Not Match");
                }
            }

        });



    }

    private boolean CheckFields(TextInputLayout et) {
        if(Objects.requireNonNull(et.getEditText()).getText().toString().isEmpty()){

            et.setError("Please Fill Up the Field");
            valid = false;
        }else{
            valid = true;
        }
        return valid;
    }
}