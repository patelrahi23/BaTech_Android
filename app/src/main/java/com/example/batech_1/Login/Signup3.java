package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.batech_1.LoginActivity;
import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.example.batech_1.SharedPrefHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Objects;

public class Signup3 extends AppCompatActivity {
    Button btn_verify, btn_login;
    TextInputLayout et_phone, et_designation;
    CountryCodePicker ccp;
    String phone = null, designation = null;
    UserClass user;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String email = null, password = null, fullName = null, gender = null, dateOfBirth = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup3);

        btn_verify = findViewById(R.id.btn_verify);
        btn_login = findViewById(R.id.btn_login);
        et_phone = findViewById(R.id.et_phone);
        ccp =  findViewById(R.id.ccp);
        et_designation = findViewById(R.id.et_designation);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        user = new UserClass();


        btn_login.setOnClickListener(view->{
            startActivity(new Intent(Signup3.this, Login.class));
            finish();
        });

        btn_verify.setOnClickListener(view -> {

            phone = ccp.getSelectedCountryCode() + Objects.requireNonNull(et_phone.getEditText()).getText().toString();
            designation = Objects.requireNonNull(et_designation.getEditText()).getText().toString();

            if (et_phone.getEditText().getText().length() < 10 && et_phone.getEditText().getText().length() == 0) {
                et_phone.setError("Phone is Required inorder to Confirm");

            } else if (et_designation.getEditText().getText().toString().isEmpty()) {
                et_designation.setError("Designation is required of an organisation");
            } else {
                user.setPh_number(phone);
                user.setOrganisation(designation);
                SharedPrefHelper.setSharedPrefrences(this, "PhoneDesignation", user);

                user = (UserClass) SharedPrefHelper.getSharedPrefrences(this, "NameEmailPassword");
                email = user.getEmail();
                password = user.getPass();
                fullName = user.getFname();
                user = (UserClass) SharedPrefHelper.getSharedPrefrences(this, "GenderDob");
                gender = user.getGender();
                dateOfBirth = user.getDOB();

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    DocumentReference dref = firestore.collection("Users").document(user != null ? user.getUid() : null);
                    HashMap<String, Object> userinfo = new HashMap<>();
                    userinfo.put("FullName", fullName);
                    userinfo.put("Email", email);
                    userinfo.put("Password", password);
                    userinfo.put("Gender", gender);
                    userinfo.put("Phone", phone);
                    userinfo.put("Date Of Birth", dateOfBirth);
                    userinfo.put("Designation", designation);
                    userinfo.put("Client", "1");
                    userinfo.put("Admin", "0");

                    dref.set(userinfo);

                    Toast.makeText(getApplicationContext(), "You are Signed Up! Please Continue to verify OTP.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Signup3.this, OTPVerify.class));
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Please Try Again In Some Time", Toast.LENGTH_LONG).show());


            }

        });
    }
}