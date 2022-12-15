package com.example.batech_1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.batech_1.Dashboards.ClientDashboards.ClientDashBoard;
import com.example.batech_1.Dashboards.MainDashboard;
import com.example.batech_1.ModelClasses.UserClass;
import com.example.batech_1.R;
import com.example.batech_1.SharedPrefHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Button btn_login, btn_create_acc, btn_fgt_pass;
    CheckBox cb_remember_me;
    TextInputLayout et_email, et_pass;
    String email = null, pass = null;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    boolean valid = true;
    UserClass user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btn_create_acc = findViewById(R.id.btn_create_acc);
        btn_login = findViewById(R.id.btn_login);
        btn_fgt_pass = findViewById(R.id.btn_fgt_pass);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        cb_remember_me = findViewById(R.id.cb_remember_me);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = new UserClass();

        btn_fgt_pass.setOnClickListener(view -> {


        });

        btn_login.setOnClickListener(view -> {
            //login the user

            if (CheckFields(et_email) && CheckFields(et_pass)) {
                email = Objects.requireNonNull(et_email.getEditText()).getText().toString();
                pass = Objects.requireNonNull(et_pass.getEditText()).getText().toString();
                user = new UserClass();
                user.setEmail(email);
                user.setPass(pass);

                if (cb_remember_me.isChecked()) {
                    SharedPrefHelper.setSharedPrefrences(this, "EmailPassword", user);
                }

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(authResult ->
                                CheckUser(Objects.requireNonNull(authResult.getUser()).getUid()))
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Please Try Again.", Toast.LENGTH_LONG).show());
            }


        });

        btn_create_acc.setOnClickListener(view -> {
            // procedure of creating account for user
            Intent intent = new Intent(Login.this, Signup1.class);
            startActivity(intent);
            finish();
        });

    }

    private void CheckUser(String uid) {
        user = new UserClass();
        Log.d("UID", "CheckUser: uid = "+uid.toString());
        user.setuid(uid);
        SharedPrefHelper.setSharedPrefrences(this, "FirebaseUID", user.getuid());
        DocumentReference dref = firestore.collection("Users").document(uid);
        dref.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("Checking UID", "On Success " + documentSnapshot.getData());
            if (Objects.equals(documentSnapshot.getString("Admin"), "1")) {
                startActivity(new Intent(Login.this, MainDashboard.class));
                finish();
            } else if (Objects.equals(documentSnapshot.getString("Admin"),  "0")) {
                startActivity(new Intent(Login.this, ClientDashBoard.class));
                finish();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Try Again later", Toast.LENGTH_SHORT).show();
            Log.e("Error AccessLevel", "" + e.getMessage());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSetEmailPass();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkSetEmailPass();

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkSetEmailPass();
    }

    private void checkFirebaseUser() {
        if (firebaseAuth.getCurrentUser() == null) {
            checkSetEmailPass();
        } else {
            CheckUser(firebaseAuth.getCurrentUser().getUid());
            UserClass usc = new UserClass();
            usc.setuid(firebaseAuth.getCurrentUser().getUid());
            SharedPrefHelper.setSharedPrefrences(this, "FireabaseUID", usc);
        }

    }

    public void checkSetEmailPass() {
        View view = getWindow().getDecorView();
        int UIView = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(UIView);
        user = (UserClass) SharedPrefHelper.getSharedPrefrences(this, "EmailPassword");
        if (user == null) {
            Toast.makeText(this, "Please Enter Details to Login", Toast.LENGTH_LONG).show();
        } else if (!user.getEmail().isEmpty() && !user.getPass().isEmpty()) {
            email = user.getEmail();
            pass = user.getPass();
            Objects.requireNonNull(et_email.getEditText()).setText(email);
            Objects.requireNonNull(et_pass.getEditText()).setText(pass);
        }

    }

    private boolean CheckFields(TextInputLayout et) {
        if (Objects.requireNonNull(et.getEditText()).getText().toString().isEmpty()) {

            et.setError("Please Fill Up the Field");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}