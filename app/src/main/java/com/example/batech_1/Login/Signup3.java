package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.example.batech_1.SharedPrefHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.auth.User;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class Signup3 extends AppCompatActivity {
    Button btn_verify, btn_login;
    TextInputLayout et_phone, et_designation;
    CountryCodePicker ccp;
    String phone = null, designation = null;
    UserClass user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup3);

        btn_verify = findViewById(R.id.btn_verify);
        et_phone = findViewById(R.id.et_phone);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        et_designation = findViewById(R.id.et_designation);

        btn_verify.setOnClickListener(view -> {

            phone = ccp.getSelectedCountryCode() + Objects.requireNonNull(et_phone.getEditText()).getText().toString();
            designation = Objects.requireNonNull(et_designation.getEditText()).getText().toString();

            if(et_phone.getEditText().getText().length()<10 && et_phone.getEditText().getText().length() == 0){
                et_phone.setError("Phone is Required inorder to Confirm");

            }else if( et_designation.getEditText().getText().toString().isEmpty()){
                et_designation.setError("Designation is required of an organisation");
            }else{
                user = new UserClass();
                user.setPh_number(phone);
                user.setOrganisation(designation);
                SharedPrefHelper.setSharedPrefrences(this,"PhoneDesignation",user);
                startActivity(new Intent(Signup3.this,OTPVerify.class));

            }

        });
    }
}